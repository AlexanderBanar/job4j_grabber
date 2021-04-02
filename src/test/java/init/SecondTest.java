package init;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SecondTest {
    @Test
    public void whenReceivingOneInt() {
        int two = new Second().justGivingTwoInt();
        assertThat(two, is(2));
    }
}