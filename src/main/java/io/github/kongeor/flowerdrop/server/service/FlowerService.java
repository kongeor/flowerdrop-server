package io.github.kongeor.flowerdrop.server.service;

import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;

import javax.inject.Inject;

public class FlowerService {

    private final FlowerDao flowerDao;

    @Inject
    public FlowerService(FlowerDao flowerDao) {
        this.flowerDao = flowerDao;
    }

    @UnitOfWork(transactional = false)
    public Flower findById(int id) {
        return flowerDao.findById(id);
    }

    public Flower create(Flower flower) {
        int id = flowerDao.create(flower);
        return findById(id);
    }
}
