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

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        User user = User.findById(id);

        if (user != null) {
            user.delete();
            return Response.accepted().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserRequest request) {

        User user = User.findById(id);

        if (user != null) {
            user.setCpf(request.getCpf());
            user.setEmail(request.getEmail());
            user.setName(request.getNome());
            user.persist();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
