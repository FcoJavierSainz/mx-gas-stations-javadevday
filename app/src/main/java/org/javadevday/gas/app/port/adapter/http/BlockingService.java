package org.javadevday.gas.app.port.adapter.http;

import org.springframework.stereotype.Service;

@Service
public class BlockingService {

  public String blockingCall() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "OK";
  }
}
