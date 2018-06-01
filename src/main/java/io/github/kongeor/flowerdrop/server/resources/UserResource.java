package io.github.kongeor.flowerdrop.server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.core.User;
import io.github.kongeor.flowerdrop.server.core.Watering;
import io.github.kongeor.flowerdrop.server.dao.UserDao;
import io.github.kongeor.flowerdrop.server.dao.WateringDao;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import io.github.kongeor.flowerdrop.server.dto.UserDto;
import io.github.kongeor.flowerdrop.server.dto.WateringDto;
import io.github.kongeor.flowerdrop.server.mapper.FlowerMapper;
import io.github.kongeor.flowerdrop.server.mapper.UserMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;
    private final WateringDao wateringDao;

    @Inject
    public UserResource(UserDao userDao, WateringDao wateringDao) {
        this.userDao = userDao;
        this.wateringDao = wateringDao;
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public UserDto findUser(@PathParam("id") Integer id) {
        User user = userDao.findById(id);
        return UserMapper.INSTANCE.userToDto(user);
    }

    @Path("/{id}/flowers")
    @GET
    @Timed
    @UnitOfWork
    public List<FlowerDto> findUserFlowers(@PathParam("id") Integer id) {
        User user = userDao.findById(id);
        return FlowerMapper.INSTANCE.flowersToDtos(user.getFlowers());
    }

    @Path("/{userId}/flowers/{flowerId}/waterings")
    @POST
    @Timed
    @UnitOfWork
    public WateringDto waterUserFlower(@PathParam("userId") Integer userId,
                                       @PathParam("flowerId") Integer flowerId) {
        User user = userDao.findById(userId);
        Flower flower = user.getFlowers().stream()
                .filter(f -> f.getId().equals(flowerId))
                .findFirst().orElseThrow(() -> new RuntimeException("not found"));
        Watering newWatering = new Watering();
        newWatering.setFlowerId(flower.getId());
        wateringDao.create(newWatering);
        return new WateringDto();
    }
}
