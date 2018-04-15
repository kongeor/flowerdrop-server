package io.github.kongeor.flowerdrop.server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/flowers")
@Produces(MediaType.APPLICATION_JSON)
public class FlowerResource {

    private final FlowerDao flowerDao;

    public FlowerResource(FlowerDao flowerDao) {
        this.flowerDao = flowerDao;
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public FlowerDto findFlower(@PathParam("id") Integer id) {
        Flower flower = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(flower);
    }

    @POST
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public FlowerDto createFlower(FlowerDto flowerDto) {
        Flower flower = new Flower();
        flower.setName(flowerDto.getName());
        flower.setDescription(flowerDto.getDescription());
        int id = flowerDao.create(flower);
        Flower dbFlower = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(dbFlower);
    }
}
