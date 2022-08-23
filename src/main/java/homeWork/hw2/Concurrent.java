package homeWork.hw2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Concurrent {

    private static List<String> sqrtList = new ArrayList<>(1000000);
    private static final int numberTread = 10; // количество потоков

    public static void main(String[] args) throws IOException, InterruptedException {
        { for (int i = 0; i < 1000000; i++) sqrtList.add("null");}

        long startTime = System.currentTimeMillis();
        BufferedWriter writerToFile = new BufferedWriter(new FileWriter("sqrtList.csv"));
        ArrayList<Thread> threads = new ArrayList<>();

        for (int threadNumber = 0; threadNumber < numberTread; ++threadNumber) {
            int incrementWorkRange = (1_000_000 * threadNumber) / numberTread;

            Thread thread = new Thread(() -> {

                for (int i = 1; i <= 100_000; ++i) {

                    double number = incrementWorkRange + i;
                    sqrtList.set((int) number-1, number + " - " + Math.sqrt(number) + "\n");
                }

            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        writerToFile.write(String.valueOf(sqrtList));
        writerToFile.flush();

        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - startTime) + " ms.");


    }
}
