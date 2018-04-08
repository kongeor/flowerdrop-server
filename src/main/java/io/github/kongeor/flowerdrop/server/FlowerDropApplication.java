package io.github.kongeor.flowerdrop.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.core.User;
import io.github.kongeor.flowerdrop.server.core.Watering;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dao.UserDao;
import io.github.kongeor.flowerdrop.server.dao.WateringDao;
import io.github.kongeor.flowerdrop.server.dto.UserDto;
import io.github.kongeor.flowerdrop.server.resources.FlowerResource;
import io.github.kongeor.flowerdrop.server.resources.UserResource;

public class FlowerDropApplication extends Application<FlowerDropConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FlowerDropApplication().run(args);
    }

    @Override
    public String getName() {
        return "FlowerDrop";
    }

    private final HibernateBundle<FlowerDropConfiguration> hibernateBundle =
            new HibernateBundle<FlowerDropConfiguration>(User.class, Flower.class,
                    Watering.class) {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(FlowerDropConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
    private final FlywayBundle<FlowerDropConfiguration> flywayBundle =
            new FlywayBundle<FlowerDropConfiguration>() {

            @Override
            public PooledDataSourceFactory getDataSourceFactory(FlowerDropConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };

    @Override
    public void initialize(final Bootstrap<FlowerDropConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(flywayBundle);
    }

    @Override
    public void run(final FlowerDropConfiguration configuration,
                    final Environment environment) {
        UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        FlowerDao flowerDao = new FlowerDao(hibernateBundle.getSessionFactory());
        WateringDao wateringDao = new WateringDao(hibernateBundle.getSessionFactory());
        environment.jersey().register(new UserResource(userDao, wateringDao));
        environment.jersey().register(new FlowerResource(flowerDao));

        ObjectMapper objectMapper = environment.getObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}
