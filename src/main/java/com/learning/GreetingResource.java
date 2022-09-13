package com.learning;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("{num1}/{num2}/{op}")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculate(@PathParam("num1") String num1,
                            @PathParam("num2") String num2,
                            @PathParam("op") String op) {

        switch (op) {
            case "add": return Integer.parseInt(num1) + Integer.parseInt(num2) + "";
            case "sub": return Integer.parseInt(num1) - Integer.parseInt(num2) + "";
        }

        return null;
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculate(@QueryParam("msg") String msg,
                            @PathParam("name") String name) {

        return msg + " " + name;
    }


}