import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    private static final int ITERATIONS = 50;

    private static void runBenchmarkForInteger(String warmup, int size, String state) {
        runSingleIntegerTest(new BubbleSortPassPerItem<Integer>(), warmup, size, state);
        runSingleIntegerTest(new BubbleSortUntilNoChange<Integer>(), warmup, size, state);
        runSingleIntegerTest(new BubbleSortWhileNeeded<Integer>(), warmup, size, state);
    }

    private static void runSingleIntegerTest(Sorter<Integer> algorithm, String warmup, int size, String state) {
        int warmupRuns = getWarmupRuns(warmup);

        for (int i = 0; i < warmupRuns; i++) {
            Integer[] warmupArray = generateIntegerInput(state, size);
            algorithm.sort(warmupArray);
        }

        long[] executionTimes = new long[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++) {
            Integer[] testArray = generateIntegerInput(state, size);
            long startTime = System.nanoTime();
            algorithm.sort(testArray);
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
        reportStatistics(warmup, "INTEGER", algorithm.getClass().getSimpleName(), executionTimes);
    }

    private static void runBenchmarkForDouble(String warmup, int size, String state) {
        runSingleDoubleTest(new BubbleSortPassPerItem<Double>(), warmup, size, state);
        runSingleDoubleTest(new BubbleSortUntilNoChange<Double>(), warmup, size, state);
        runSingleDoubleTest(new BubbleSortWhileNeeded<Double>(), warmup, size, state);
    }

    private static void runSingleDoubleTest(Sorter<Double> algorithm, String warmup, int size, String state) {
        int warmupRuns = getWarmupRuns(warmup);

        for (int i = 0; i < warmupRuns; i++) {
            Double[] warmupArray = generateDoubleInput(state, size);
            algorithm.sort(warmupArray);
        }

        long[] executionTimes = new long[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++) {
            Double[] testArray = generateDoubleInput(state, size);
            long startTime = System.nanoTime();
            algorithm.sort(testArray);
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
        reportStatistics(warmup, "DOUBLE", algorithm.getClass().getSimpleName(), executionTimes);
    }

    private static void runBenchmarkForFloat(String warmup, int size, String state) {
        runSingleFloatTest(new BubbleSortPassPerItem<Float>(), warmup, size, state);
        runSingleFloatTest(new BubbleSortUntilNoChange<Float>(), warmup, size, state);
        runSingleFloatTest(new BubbleSortWhileNeeded<Float>(), warmup, size, state);
    }

    private static void runSingleFloatTest(Sorter<Float> algorithm, String warmup, int size, String state) {
        int warmupRuns = getWarmupRuns(warmup);

        for (int i = 0; i < warmupRuns; i++) {
            Float[] warmupArray = generateFloatInput(state, size);
            algorithm.sort(warmupArray);
        }

        long[] executionTimes = new long[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++) {
            Float[] testArray = generateFloatInput(state, size);
            long startTime = System.nanoTime();
            algorithm.sort(testArray);
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
        reportStatistics(warmup, "FLOAT", algorithm.getClass().getSimpleName(), executionTimes);
    }

    private static void runBenchmarkForString(String warmup, int size, String state) {
        runSingleStringTest(new BubbleSortPassPerItem<String>(), warmup, size, state);
        runSingleStringTest(new BubbleSortUntilNoChange<String>(), warmup, size, state);
        runSingleStringTest(new BubbleSortWhileNeeded<String>(), warmup, size, state);
    }

    private static void runSingleStringTest(Sorter<String> algorithm, String warmup, int size, String state) {
        int warmupRuns = getWarmupRuns(warmup);

        for (int i = 0; i < warmupRuns; i++) {
            String[] warmupArray = generateStringInput(state, size);
            algorithm.sort(warmupArray);
        }

        long[] executionTimes = new long[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++) {
            String[] testArray = generateStringInput(state, size);
            long startTime = System.nanoTime();
            algorithm.sort(testArray);
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
        reportStatistics(warmup, "STRING", algorithm.getClass().getSimpleName(), executionTimes);
    }

    private static Integer[] generateIntegerInput(String state, int size) {
        final Random RANDOM = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++)
            array[i] = RANDOM.nextInt(100000);
        applySortingState(array, state, "INTEGER");
        return array;
    }

    private static Double[] generateDoubleInput(String state, int size) {
        final Random RANDOM = new Random();
        Double[] array = new Double[size];
        for (int i = 0; i < size; i++)
            array[i] = RANDOM.nextDouble();
        applySortingState(array, state, "DOUBLE");
        return array;
    }

    private static Float[] generateFloatInput(String state, int size) {
        final Random RANDOM = new Random();
        Float[] array = new Float[size];
        for (int i = 0; i < size; i++)
            array[i] = RANDOM.nextFloat();
        applySortingState(array, state, "FLOAT");
        return array;
    }

    private static String[] generateStringInput(String state, int size) {
        final Random RANDOM = new Random();
        String[] array = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = "Str-" + RANDOM.nextInt(100000);
        applySortingState(array, state, "STRING");
        return array;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void applySortingState(T[] array, String state, String typeName) {
        int size = array.length;
        switch (state) {
            case "SORTED ASCENDING":
                Arrays.sort(array);
                break;

            case "SORTED DESCENDING":
                Arrays.sort(array);
                for (int i = 0; i < size / 2; i++) {
                    T temp = array[i];
                    array[i] = array[size - 1 - i];
                    array[size - 1 - i] = temp;
                }
                break;

            case "SORTED UNTIL 90%":
                Arrays.sort(array);
                final Random RANDOM = new Random();
                int startIndex90 = (int) (size * 0.90);
                for (int i = startIndex90; i < size; i++) {
                    if (typeName.equals("INTEGER"))
                        array[i] = (T) (Integer) RANDOM.nextInt(100000);
                    else if (typeName.equals("DOUBLE"))
                        array[i] = (T) (Double) RANDOM.nextDouble();
                    else if (typeName.equals("FLOAT"))
                        array[i] = (T) (Float) RANDOM.nextFloat();
                    else if (typeName.equals("STRING"))
                        array[i] = (T) ("" + (RANDOM.nextInt(100000)));
                }
                break;
            case "RANDOM ORDER":
            default:
                break;
        }
    }

    private static int getWarmupRuns(String warmup) {
        if (warmup.equals("SMALL WARMUP"))
            return 3;
        if (warmup.equals("COMPLETE WARMUP"))
            return 15;
        return 0;
    }

    private static void reportStatistics(String warmup, String dataTypeName, String algorithm, long[] times) {
        long min = 1000000000;
        long max = 0;
        double sum = 0;
        for (long time : times) {
            if (time < min)
                min = time;
            if (time > max)
                max = time;
            sum += time;
        }
        double minMs = min / 1000000.0;
        double maxMs = max / 1000000.0;
        double avgMs = (sum / times.length) / 1000000.0;
        System.out.printf("%-16s | %-10s | %-24s | %-10.4f | %-10.4f | %-10.4f\n", warmup, dataTypeName,
                algorithm, minMs, maxMs,
                avgMs);
    }

    public static void main(String[] args) {

        // Array size values
        int[] arraySizes = { 10, 100, 1000 };

        // Array "sortedness" values
        String[] sortStates = {
                "SORTED ASCENDING",
                "SORTED DESCENDING",
                "RANDOM ORDER",
                "SORTED UNTIL 90%"
        };

        // Warmup values
        String[] warmupAmount = { "NO WARMUP", "SMALL WARMUP", "COMPLETE WARMUP" };

        // Iterate over all independant variables
        for (int size : arraySizes) {
            System.out.println("///////////////////////////////////////////////");
            System.out.println("Array size: " + size);
            System.out.println("///////////////////////////////////////////////");

            for (String state : sortStates) {
                System.out.println("///////////////////////////////////////////////");
                System.out.println("Sorting state " + state);
                System.out.println("///////////////////////////////////////////////");

                System.out.printf("%-16s | %-10s | %-24s | %-10s | %-10s | %-10s\n", "Warmup Strategy", "Data type",
                        "Algorithm", "Min (ms)",
                        "Max (ms)", "Average (ms)");

                for (String warmup : warmupAmount) {
                    runBenchmarkForInteger(warmup, size, state);
                    runBenchmarkForDouble(warmup, size, state);
                    runBenchmarkForFloat(warmup, size, state);
                    runBenchmarkForString(warmup, size, state);
                }
            }
        }
    }
}