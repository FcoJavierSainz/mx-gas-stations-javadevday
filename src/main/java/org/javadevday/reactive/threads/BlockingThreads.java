package org.javadevday.reactive.threads;

public class BlockingThreads {

  public static void main(String[] args) {
    for (int i = 0; i < 2_000; i++) {
      new Thread(() -> {
        try {
          new Object();
          new Object();
          new Object();
          new Object();
          Thread.sleep(5_000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }

}
