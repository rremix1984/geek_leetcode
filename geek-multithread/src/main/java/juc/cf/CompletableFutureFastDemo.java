package juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @auther zzyy
 * @create 2022-01-17 18:44
 */
public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            out.println("A come in");
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            out.println("B come in");
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> {
            return f + " is winer";
        });

        out.println(Thread.currentThread().getName() + "\t" + "-----: " + result.join());
    }
}
