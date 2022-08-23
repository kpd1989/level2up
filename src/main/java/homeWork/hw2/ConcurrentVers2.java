package homeWork.hw2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ConcurrentVers2 {
    private static String [] sqrtList = new String[1000000];
    private static final int numberTread = 10; // количество потоков
    private static Object lock = new Object();

    public static void main(String[] args) throws IOException, InterruptedException {

        long startTime = System.currentTimeMillis();
        BufferedWriter writerToFile = new BufferedWriter(new FileWriter("sqrtList.csv"));
        ArrayList<Thread> threads = new ArrayList<>();


        for (int threadNumber = 0; threadNumber < numberTread; ++threadNumber) {
            int incrementWorkRange = (1_000_000 * threadNumber) / numberTread;

            Thread thread = new Thread(() -> {

                    for (int i = 1; i <= 100_000; ++i) {
                          int number = incrementWorkRange + i;
                            String sqrtNumber = number + " - " + Math.sqrt(number) + "\n";

                            synchronized (lock) {
                            sqrtList[(number - 1)] = sqrtNumber;
                        }
                }

            });
            threads.add(thread);
            thread.start();

        }

        for (Thread thread: threads) {
            thread.join();
        }


       writerToFile.write(Arrays.toString(sqrtList));



        //for (String thread: sqrtList) {
          //  writerToFile.write(thread);
            writerToFile.flush();
        //}
        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - startTime) + " ms.");

    }
}
