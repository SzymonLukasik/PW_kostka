import concurrentcube.Cube;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConcurrencyTests {

    @Test
    public void testOneAxisConcurrentRotation() throws InterruptedException {
        for (int size = 2; size <= 10; size++) {
            CyclicBarrier barrier= new CyclicBarrier(size);
            var counter = new Object() { int value = 0; };
            Cube cube = new Cube(
                size,
                (x, y) -> {
                    try {
                        barrier.await();
                        ++counter.value;
                    } catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
                },
                (x, y) -> { ++counter.value; },
                () -> {},
                () -> {}
            );

            int size1 = size;
            ArrayList<Thread> threads = IntStream.range(0, size * 4).mapToObj(index -> new Thread(() -> {
                try { cube.rotate(0, index % size1); }
                catch (InterruptedException ignore) {}}))
                .collect(Collectors.toCollection(ArrayList::new));
            threads.forEach(Thread::start);
            threads.forEach(thread -> {try { thread.join(); } catch (InterruptedException ignore) {}});

            System.out.println("SIZE: " + size + " - COUNTER: " + counter.value);
            int size_sqr = size * size;
            String solved = IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(i).repeat(size_sqr)).collect(Collectors.joining());
            Assert.assertEquals(solved.toString(), cube.show());
        }
    }
}

