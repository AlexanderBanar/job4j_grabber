package init;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FirstTest {
    @Test
    public void whenReceivingOneInt() {
        int one = new First().justGivingOneInt();
        assertThat(one, is(1));
    }

}