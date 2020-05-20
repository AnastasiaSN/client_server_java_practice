import ua.edu.ukma.fido.entity.Counter;

import org.junit.Test;
import static org.junit.Assert.*;

public class RaceConditionTest {

    Integer thread_count = 12;
    Integer iteration_count = 100_000;
    Integer expected_result = thread_count * iteration_count;

    @Test
    public void incrementRaceCondition() throws InterruptedException {
        Thread threads[] = new Thread[thread_count];

        Counter c = new Counter();
        Runnable r = () -> {
            for (int i = 0; i < iteration_count; i++) {
                c.incrementRaceCondition();
            }
        };

        for (int j = 0; j < thread_count; j++) {
            threads[j] = new Thread(r);
            threads[j].start();
        }

        for (int j = 0; j < thread_count; j++) {
            threads[j].join();
        }

        assertEquals(expected_result, c.getValue());
    }

    @Test
    public void incrementNormal() throws InterruptedException {
        Integer thread_count = 12;
        Thread threads[] = new Thread[thread_count];

        Counter c = new Counter();
        Runnable r = () -> {
            for (int i = 0; i < iteration_count; i++) {
                c.increment();
            }
        };

        for (int j = 0; j < thread_count; j++) {
            threads[j] = new Thread(r);
            threads[j].start();
        }

        for (int j = 0; j < thread_count; j++) {
            threads[j].join();
        }

        assertEquals(expected_result, c.getValue());
    }
}
