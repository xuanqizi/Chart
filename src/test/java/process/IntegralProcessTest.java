package process;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class IntegralProcessTest {
    private Process process;

    @Before
    public void setUp() throws Exception {
        process = new IntegralProcess();
    }

    @Test
    public void testProcess1() {
        List<Short> input = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            input.add((short) 1);
        }
        List<Integer> output = process.process(input);
        int[] outputArray = output.stream().mapToInt(Integer::valueOf).toArray();
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, outputArray);
    }

    @Test
    public void testProcess2() {
        List<Short> input = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            input.add((short) 1);
        }
        List<Integer> output = process.process(input);
        int[] outputArray = output.stream().mapToInt(Integer::valueOf).toArray();
        int[] exceptedRes = new int[200];
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += input.get(i);
            exceptedRes[i] = sum;
        }
        for (int i = 0; i < 100; i++) {
            sum += input.get(100 + i);
            sum -= input.get(i);
            exceptedRes[i + 100] = sum;
        }
        assertArrayEquals(exceptedRes, outputArray);
    }

    @After
    public void tearDown() throws Exception {
        process = null;
    }
}