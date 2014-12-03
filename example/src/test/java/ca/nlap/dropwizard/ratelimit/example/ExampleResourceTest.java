package ca.nlap.dropwizard.ratelimit.example;

import io.dropwizard.testing.junit.ResourceTestRule;
import ca.nlap.dropwizard.ratelimit.example.resources.ExampleResource;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ExampleResource())
            .build();

    @Test
    public void returnsHello() throws Exception {
        final String response = resources.client().target("/")
                .request().get(String.class);
        assertEquals(response, "Hello");
    }
}