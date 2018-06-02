package io.github.kongeor.flowerdrop.server.service;

import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;

import javax.inject.Inject;

public class FlowerService {

    private final FlowerDao flowerDao;

    @Inject
    public FlowerService(FlowerDao flowerDao) {
        this.flowerDao = flowerDao;
    }

    @UnitOfWork(transactional = false)
    public FlowerDto findById(int id) {
        Flower flower  = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(flower);
    }

    public Flower create(Flower flower) {
        int id = flowerDao.create(flower);
        return flowerDao.findById(id);
    }
}
