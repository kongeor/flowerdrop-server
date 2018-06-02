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
        addLogEntry("Fetching flower with id = " + id);
        Flower flower = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(flower);
    }

    @UnitOfWork("log")
    public void addLogEntry(String text) {
        FlowerLog log = new FlowerLog();
        log.setDescription(text);
        flowerLogDao.create(log);
    }

    @UnitOfWork
    public FlowerDto create(FlowerDto flowerDto) {

        addLogEntry("Creating flower : " + flowerDto);

        Flower flower = new Flower();
        flower.setName(flowerDto.getName());
        flower.setDescription(flowerDto.getDescription());
        int id = flowerDao.create(flower);
        Flower byId = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(byId);
    }
}
