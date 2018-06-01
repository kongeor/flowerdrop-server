package io.github.kongeor.flowerdrop.server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;
import io.github.kongeor.flowerdrop.server.service.FlowerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/flowers")
@Produces(MediaType.APPLICATION_JSON)
public class FlowerResource {

    private final FlowerService flowerService;

    @Inject
    public FlowerResource(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @Path("/{id}")
    @GET
    @Timed
    public FlowerDto findFlower(@PathParam("id") Integer id) {
        Flower flower = flowerService.findById(id);
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
        Flower dbFlower = flowerService.create(flower);
        return FlowerMapper.INSTANCE.flowerToDto(dbFlower);
    }
}
