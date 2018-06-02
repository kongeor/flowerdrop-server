package io.github.kongeor.flowerdrop.server.dao;

import io.dropwizard.hibernate.AbstractDAO;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.core.FlowerLog;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class FlowerLogDao extends AbstractDAO<FlowerLog> {

    @Inject
    public FlowerLogDao(@Named SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public FlowerLog findById(Integer id) {
        return get(id);
    }

    public int create(FlowerLog log) {
        return persist(log).getId();
    }
}
