package main;

public class DeadLock {

    public static void main(String[] args) {
        DeadLock dl = new DeadLock();
        Object x = new Object();
        Object y = new Object();

        dl.synchronizedFlow(x, y);
        dl.synchronizedFlow(y, x);
    }

    private void synchronizedFlow(Object x, Object y) {
        new Thread(() -> {
            synchronized (y) {
                System.out.println(Thread.currentThread().getName() + " inside Object.");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error in sleep");
                }

                System.out.println(Thread.currentThread().getName() + " try to get enemy Object.");

                synchronized (x) {
                    System.out.println(Thread.currentThread().getName() + " inside enemy Object.");
                }
            }
        }).start();

    }
}
