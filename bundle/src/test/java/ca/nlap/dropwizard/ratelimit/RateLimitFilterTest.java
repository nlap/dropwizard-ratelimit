package ca.nlap.dropwizard.ratelimit;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Request;

import javax.ws.rs.NameBinding;

import static org.junit.Assert.*;

public class RateLimitFilterTest {

    @Test
    public void annotationRateLimitedExistsOnClass() {
        // RateLimitFilter will always run if class itself isn't annotated with @RateLimited
        assertTrue((describe(RateLimitFilter.class).getAnnotation(RateLimited.class) != null));
    }

    private Description describe(Class<?> testClass) {
        return Request.aClass(testClass).getRunner().getDescription();
    }

}