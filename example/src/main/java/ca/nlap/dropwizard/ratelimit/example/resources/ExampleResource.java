package ca.nlap.dropwizard.ratelimit.example.resources;

import ca.nlap.dropwizard.ratelimit.RateLimited;
import javax.ws.rs.*;

@Path("/")
public class ExampleResource {
	
	@GET
	@RateLimited
	public String getResource() {
		return "Hello";
	}

}
