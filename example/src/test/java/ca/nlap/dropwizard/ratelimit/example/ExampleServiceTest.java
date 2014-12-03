package ca.nlap.dropwizard.ratelimit.example;

import ca.nlap.dropwizard.ratelimit.example.resources.ExampleResource;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {

    private final ExampleServiceConfiguration configuration = mock(ExampleServiceConfiguration.class);
    private final JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
    private final Environment environment = mock(Environment.class);
    private final ExampleService service = new ExampleService();

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        when(environment.jersey()).thenReturn(jerseyEnvironment);
    }

    @Test
    public void hasCorrectServiceName() throws Exception {
        ExampleService service = new ExampleService();
        assertEquals(service.getName(), "dropwizard-ratelimit-example");
    }

    @Test
    public void registersExampleResource() throws Exception {
        service.run(configuration, environment);
        verify(jerseyEnvironment).register(ExampleResource.class);
    }
}