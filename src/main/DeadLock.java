package main;

public class DeadLock {

    public static void main(String[] args) {
        Object x = new Object();
        Object y = new Object();

        new Thread(() -> {
            synchronized (x) {
                System.out.println(Thread.currentThread().getName() + " inside first Object.");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error in sleep");
                }

                System.out.println(Thread.currentThread().getName() + " try to get in first Object.");
                synchronized (y) {
                    System.out.println(Thread.currentThread().getName() + " inside second Object.");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (y) {
                System.out.println(Thread.currentThread().getName() + " inside second Object.");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error in sleep");
                }

                System.out.println(Thread.currentThread().getName() + " try to get in second Object.");
                synchronized (x) {
                    System.out.println(Thread.currentThread().getName() + " inside first Object.");
                }
            }
        }).start();
    }
}
