package com.learning.resources;

import com.learning.dto.UserRequest;
import com.learning.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public Response createUser(UserRequest userRequest) {

        User user = new User();
        user.setCpf(userRequest.getCpf());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getNome());
        user.persist();

        return Response.ok(userRequest).build();
    }

    @GET
    public Response listAllUsers() {
        PanacheQuery<User> users = User.findAll();
        return Response.ok(users.list()).build();
    }

}
