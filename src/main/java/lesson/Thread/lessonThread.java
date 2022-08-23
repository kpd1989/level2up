package lesson.Thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class lessonThread { // многопоточность
    private static Object lock = new Object();
    //private static long counter = 0;
    private  static AtomicLong counter = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int threadNumber = 0; threadNumber<10;++threadNumber) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 100_000; ++i) {
                    synchronized (lock) {
                        counter.incrementAndGet();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
            for(Thread thread : threads){
                thread.join();
            }
            long end = System.currentTimeMillis();
            System.out.println("counter " + counter);
        System.out.println("Took "+ (end-startTime));

       // Thread.sleep(300);
        //System.out.println("main");

    }
}

class Data {
    private int count;

    public synchronized void increment(){
        count++;
    }

    public int getCount() {
        synchronized (this){
            return count;
        }
    }
}

class Lists {
    private static List<String> names = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        for (int threadNumber=0; threadNumber<10;threadNumber++) {
            new Thread(() -> {
                for (int i = 0; i < 10000; ++i) {
                    //synchronized (names) {
                        names.add("name -" + i);
                  //  }
                }
            }).start();
        }
        Thread.sleep(150);
        System.out.println(names);
    }
}
class MyArrayList { // пример работы ArrayList
    private  Object[] items = new Object[10];
    private int count = 0;

    public void add (String name){
        if(count==items.length){
            items = Arrays.copyOf(items, count*2);
        }
        items[count] = name;
        count++;
    }
}

class Queue {
    private List <Event> events = Collections.synchronizedList(new ArrayList<>());
    private ArrayBlockingQueue<Event> eventsQueue = new ArrayBlockingQueue<>(100);


    void run () throws InterruptedException {
        while (true){
            Event event = eventsQueue.take(); //ожидание появления элемента в очереди
            System.out.println(event);
        }
    }

    enum Event {
        CREATED,
        DELETED,
        MODIFIED
    }
}