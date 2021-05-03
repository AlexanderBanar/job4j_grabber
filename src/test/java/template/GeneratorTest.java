package template;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@Ignore
public class GeneratorTest {
    @Test(expected = MissingKeyException.class)
    public void notProduceWithMissingKeys() {
        String template = "I am ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("${name}", "Ivan Ivanov");
        Generator generator = new LocalGenerator();
        generator.produce(template, args);
    }

    @Test(expected = ExtraKeyException.class)
    public void notProduceWithExtraKeys() {
        String template = "I am ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("${name}", "Ivan Ivanov");
        args.put("${subject}", "they");
        args.put("${object}", "him");
        Generator generator = new LocalGenerator();
        generator.produce(template, args);
    }

    @Test
    public void produce() {
        String template = "I am ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("${name}", "Ivan Ivanov");
        args.put("${subject}", "they");
        Generator generator = new LocalGenerator();
        assertThat(generator.produce(template, args),
                is("I am Ivan Ivanov, Who are they?"));
    }
}