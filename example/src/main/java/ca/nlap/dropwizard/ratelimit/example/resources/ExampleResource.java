package ca.nlap.dropwizard.ratelimit.example.resources;

import ca.nlap.dropwizard.ratelimit.RateLimited;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class ExampleResource {
	
	@GET
	@RateLimited
	public Response getResource() {
		return new Response.ok().build();
	}

}
