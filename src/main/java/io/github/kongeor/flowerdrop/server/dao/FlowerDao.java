package io.github.kongeor.flowerdrop.server.dao;

import io.dropwizard.hibernate.AbstractDAO;
import io.github.kongeor.flowerdrop.server.core.Flower;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class FlowerDao extends AbstractDAO<Flower> {

    @Inject
    public FlowerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Flower findById(Integer id) {
        return get(id);
    }

    public int create(Flower flower) {
        return persist(flower).getId();
    }
}
