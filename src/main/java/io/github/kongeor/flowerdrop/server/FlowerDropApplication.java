package io.github.kongeor.flowerdrop.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.core.FlowerLog;
import io.github.kongeor.flowerdrop.server.core.User;
import io.github.kongeor.flowerdrop.server.core.Watering;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dao.UserDao;
import io.github.kongeor.flowerdrop.server.dao.WateringDao;
import io.github.kongeor.flowerdrop.server.dto.UserDto;
import io.github.kongeor.flowerdrop.server.resources.FlowerResource;
import io.github.kongeor.flowerdrop.server.resources.UserResource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

import javax.inject.Named;
import java.lang.reflect.InvocationTargetException;

public class FlowerDropApplication extends Application<FlowerDropConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FlowerDropApplication().run(args);
    }

    @Override
    public String getName() {
        return "FlowerDrop";
    }

    private final HbnBundle hibernateBundle = new HbnBundle("hibernate", User.class, Flower.class,
                    Watering.class);

    private final HbnBundle logBundle = new HbnBundle("log", FlowerLog.class);

    private final FlywayBundle<FlowerDropConfiguration> flywayBundle =
            new FlywayBundle<FlowerDropConfiguration>() {

            @Override
            public PooledDataSourceFactory getDataSourceFactory(FlowerDropConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };

    @Override
    public void initialize(final Bootstrap<FlowerDropConfiguration> bootstrap) {

        // needs to come first
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(logBundle);

        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new HbnModule(hibernateBundle, logBundle))
                .build());

        bootstrap.addBundle(flywayBundle);
    }

    private static class HbnBundle extends HibernateBundle<FlowerDropConfiguration> {

        private final String name;

        protected HbnBundle(String name, Class<?> entity, Class<?> ...entities) {
            super(entity, entities);
            this.name = name;
        }

        @Override
        public PooledDataSourceFactory getDataSourceFactory(FlowerDropConfiguration configuration) {
            if (name.equals("hibernate")) {
                return configuration.getDataSourceFactory();
            } else {
                return configuration.getLogDatabase();
            }
        }

        @Override
        public String name() {
            return name;
        }
    }

    private static class HbnModule extends AbstractModule {

        private final HbnBundle hbnBundle;
        private final HbnBundle logBundle;

        public HbnModule(HbnBundle hbnBundle, HbnBundle logBundle) {
            this.hbnBundle = hbnBundle;
            this.logBundle = logBundle;
        }

        @Override
        protected void configure() {
            bind(SessionFactory.class).toInstance(hbnBundle.getSessionFactory());
            bind(SessionFactory.class).annotatedWith(Named.class).toInstance(logBundle.getSessionFactory());

            bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class),
                    new UnitOfWorkInterceptor(hbnBundle, logBundle));
        }
    }

    /**
     * Same approach as in {@link io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory}
     */
    private static class UnitOfWorkInterceptor implements MethodInterceptor {


        private final ImmutableMap<String, SessionFactory> sessionFactories;

        public UnitOfWorkInterceptor(HbnBundle... bundles) {
            final ImmutableMap.Builder<String, SessionFactory> sessionFactoriesBuilder = ImmutableMap.builder();
            for (HbnBundle bundle : bundles) {
                sessionFactoriesBuilder.put(bundle.name(), bundle.getSessionFactory());
            }
            sessionFactories = sessionFactoriesBuilder.build();
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            final UnitOfWork unitOfWork = methodInvocation.getMethod().getAnnotation(UnitOfWork.class);
            final UnitOfWorkAspect unitOfWorkAspect = new UnitOfWorkAspect(sessionFactories);
            try {
                unitOfWorkAspect.beforeStart(unitOfWork);
                Object result = methodInvocation.proceed();
                unitOfWorkAspect.afterEnd();
                return result;
            } catch (InvocationTargetException e) {
                unitOfWorkAspect.onError();
                throw e.getCause();
            } catch (Exception e) {
                unitOfWorkAspect.onError();
                throw e;
            } finally {
                unitOfWorkAspect.onFinish();
            }
        }
    }

    @Override
    public void run(final FlowerDropConfiguration configuration,
                    final Environment environment) {
//        UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
//        FlowerDao flowerDao = new FlowerDao(hibernateBundle.getSessionFactory());
//        WateringDao wateringDao = new WateringDao(hibernateBundle.getSessionFactory());
//        environment.jersey().register(new UserResource(userDao, wateringDao));
//        environment.jersey().register(new FlowerResource(flowerDao));

        ObjectMapper objectMapper = environment.getObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}
