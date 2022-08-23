package lesson.Thread;

public class Worker extends Thread{
    @Override
    public void run(){
        System.out.println("Hello, World!");
    }
}

class WorkerTask implements Runnable{
    @Override
    public void run(){
        System.out.println("Hello, World!");
        System.out.println(Thread.currentThread().getName()); //узнаем имя потока
        Thread.currentThread().setName("worker-1"); //изменяем имя потока
    }
}