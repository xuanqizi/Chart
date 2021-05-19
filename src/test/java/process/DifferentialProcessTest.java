package process;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class DifferentialProcessTest {

    private Process process;

    @Before
    public void setUp() {
        process = new DifferentialProcess();
    }

    @Test
    public void process() {
        List<Short> list = new ArrayList<>();
        for (short i = 0; i < 5; i++) {
            list.add(i);
        }
        List<Integer> res = process.process(list);
        int[] resultArray = res.stream().mapToInt(Integer::valueOf).toArray();
        assertArrayEquals(new int[]{1, 1, 1, 1}, resultArray);
    }



    @After
    public void tearDown() throws Exception {
        process = null;
    }
}