package com.tsoft.demo.benchmark;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@State(Scope.Benchmark)
@Threads(1)
public class JmhUsageTest {

    @Param({"10","100","1000"})
    public int iterations;

    @Setup(Level.Invocation)
    public void setupInvocation() throws Exception {
        // executed before each invocation of the benchmark
    }

    @Setup(Level.Iteration)
    public void setupIteration() throws Exception {
        // executed before each invocation of the iteration
    }

   /* @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(warmups = 1, value = 1)
    @Warmup(batchSize = -1, iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(batchSize = -1, iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void test() throws Exception {
        int sum = 0; // compare with Integer sum = 0
        for (int i = 0; i < 1_000_000; i ++) {
            sum += i;
        }
        System.out.println(sum);
    } */

    @Test
    public void benchmark() throws Exception {
        String[] argv = {};
        org.openjdk.jmh.Main.main(argv);
    }

    //@Test
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(warmups = 1, value = 1)
    @Warmup(batchSize = -1, iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(batchSize = -1, iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void test() {
        AtomicInteger count = new AtomicInteger();
        Stream<String> stream = Arrays.stream(new String[] {"AAAA", "ABBB", "BBBB"});
        stream.filter(
            symbol -> symbol.charAt(0) != 'A' &&
                symbol.charAt(1) != 'A' &&
                symbol.charAt(2) != 'A' &&
                symbol.charAt(3) != 'A').
            forEach(symbol -> { count.incrementAndGet(); });
    }
}
