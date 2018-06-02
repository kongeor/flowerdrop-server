package io.github.kongeor.flowerdrop.server.service;

import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.core.FlowerLog;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dao.FlowerLogDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;

import javax.inject.Inject;

public class FlowerService {

    private final FlowerDao flowerDao;
    private final FlowerLogDao flowerLogDao;

    @Inject
    public FlowerService(FlowerDao flowerDao, FlowerLogDao flowerLogDao) {
        this.flowerDao = flowerDao;
        this.flowerLogDao = flowerLogDao;
    }

//    public FlowerDto findById(int id) {
//        addLogEntry(id);
//        return findByIdImpl(id);
//    }

    @UnitOfWork(transactional = false)
    public FlowerDto findById(int id) {
        addLogEntry(id);
        Flower flower = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(flower);
    }

    @UnitOfWork("log")
    public void addLogEntry(int flowerId) {
        FlowerLog log = new FlowerLog();
        log.setDescription("Fetching flower with id = " + flowerId);
        flowerLogDao.create(log);
    }

    public Flower create(Flower flower) {
        int id = flowerDao.create(flower);
        return flowerDao.findById(id);
    }
}
