package io.github.kongeor.flowerdrop.server.dao;

import io.dropwizard.hibernate.AbstractDAO;
import io.github.kongeor.flowerdrop.server.core.User;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserDao extends AbstractDAO<User> {

    @Inject
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findById(Integer id) {
        return get(id);
    }

    public long saveOrUpdate(User user) {
        return persist(user).getId();
    }
}
