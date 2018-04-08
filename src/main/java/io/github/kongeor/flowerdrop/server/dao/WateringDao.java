package io.github.kongeor.flowerdrop.server.dao;

import io.dropwizard.hibernate.AbstractDAO;
import io.github.kongeor.flowerdrop.server.core.Watering;
import org.hibernate.SessionFactory;

public class WateringDao extends AbstractDAO<Watering> {

    public WateringDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Watering findById(Integer id) {
        return get(id);
    }

    public long create(Watering flower) {
        return persist(flower).getId();
    }
}
