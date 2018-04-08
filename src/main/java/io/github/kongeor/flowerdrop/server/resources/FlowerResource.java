package io.github.kongeor.flowerdrop.server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dao.FlowerDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public FlowerDto findUser(@PathParam("id") Integer id) {
        Flower flower = flowerDao.findById(id);
        return FlowerMapper.INSTANCE.flowerToDto(flower);
    }
}
