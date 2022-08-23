package lesson.Thread;

public class WorkerMain {
    public static void main(String[] args) {
        new Worker().start();                   //worker extends Thread
        new Thread(new WorkerTask(), "worker").start();   //WorkerTask implements Runnable
        new Thread(()->{                        //implements Runnable
            System.out.println("Hello, World!");
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(5000);
                System.out.println("after sleep");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread helper = new Thread(()->{
            try {
                Thread.sleep(9999999);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        helper.setDaemon(true); //демон-поток
        helper.start();

        System.out.println("end");
    }
}
