package io.github.kongeor.flowerdrop.server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.github.kongeor.flowerdrop.server.core.User;
import io.github.kongeor.flowerdrop.server.dao.UserDao;
import io.github.kongeor.flowerdrop.server.dto.UserDto;
import io.github.kongeor.flowerdrop.server.mapper.UserMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public UserDto findUser(@PathParam("id") Integer id) {
        User user = userDao.findById(id);
        return UserMapper.INSTANCE.userToDto(user);
    }
}
