package org.example.transfersv6;

public class Utils {

    private static final boolean VIRTUAL_THREADS_ENABLED = true;

    public static void freeThread() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeOnThread(Runnable runnable) {
        if (VIRTUAL_THREADS_ENABLED) {
            Thread.ofVirtual().start(runnable);
        } else {
            new Thread(runnable).start();
        }
    }
}
