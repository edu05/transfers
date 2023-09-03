package org.example.transfersv6;

public class Utils {

    public static final String TRANSFERS_TOPIC = "transfers2";
    private static final boolean VIRTUAL_THREADS_ENABLED = true;

    public static void freeThread() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void freeThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeOnThread(Actor actor) {
        executeOnThread(actor.id, actor);
    }

    public static void executeOnThread(String name, Runnable runnable) {
        if (VIRTUAL_THREADS_ENABLED) {
            Thread.ofVirtual().name(name).start(runnable);
        } else {
            new Thread(runnable, name).start();
        }
    }

    public static void executeOnNormalThread(String name, Runnable runnable) {
        new Thread(runnable, name).start();
    }
}
