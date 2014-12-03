package ca.nlap.dropwizard.ratelimit;

import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.Description;

import javax.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static org.junit.Assert.assertTrue;

public class RateLimitedTest {

    @Test
    public void annotationNameBindingExistsOnClass() {
        assertTrue((describe(RateLimited.class).getAnnotation(NameBinding.class) != null));
    }

    @Test
    public void annotationRetentionIsRuntime() {
        assertTrue((describe(RateLimited.class).getAnnotation(Retention.class).value().toString() == "RUNTIME"));
    }

    @Test
    public void annotationTargetsAreTypeAndMethod() {
        assertTrue((describe(RateLimited.class).getAnnotation(Target.class).value()[0].toString() == "TYPE"));
        assertTrue((describe(RateLimited.class).getAnnotation(Target.class).value()[1].toString() == "METHOD"));
    }

    private Description describe(Class<?> testClass) {
        return Request.aClass(testClass).getRunner().getDescription();
    }
}
