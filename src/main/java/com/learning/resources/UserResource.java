package com.learning.resources;

import com.learning.dto.ResponseError;
import com.learning.dto.UserRequest;
import com.learning.model.User;
import com.learning.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;

    private final Validator validator;

    public UserResource(UserRepository userRepository, Validator validator) {
        this.repository = userRepository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(UserRequest userRequest) {

        Optional<ResponseError> valid = isValid(userRequest);

        if (valid.isEmpty()) {

            User user = new User();
            user.setCpf(userRequest.getCpf());
            user.setEmail(userRequest.getEmail());
            user.setName(userRequest.getNome());

            //user.persist();
            repository.persist(user);

            return Response.ok(userRequest).build();
        } else {
            return valid.get().withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
    }

    private Optional<ResponseError> isValid(UserRequest userRequest) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()) {
//            ConstraintViolation<UserRequest> violation = violations.stream().findAny().get();
//            String errorMessage = violation.getMessage();
//            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
//            return Optional.of(response);
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return Optional.of(responseError);
        }
        return Optional.empty();
    }

    @GET
    public Response listAllUsers() {
        //PanacheQuery<User> users = User.findAll();
        PanacheQuery<User> users = repository.findAll();
        return Response.ok(users.list()).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        //User user = User.findById(id);
        User user = repository.findById(id);

        if (user != null) {
            //user.delete();
            repository.delete(user);

            return Response.accepted().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserRequest request) {

        //User user = User.findById(id);
        User user = repository.findById(id);


        if (user != null) {
            user.setCpf(request.getCpf());
            user.setEmail(request.getEmail());
            user.setName(request.getNome());
            return Response.accepted().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
