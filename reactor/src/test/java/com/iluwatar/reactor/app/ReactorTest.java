/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.reactor.app;

import com.iluwatar.reactor.framework.SameThreadDispatcher;
import com.iluwatar.reactor.framework.ThreadPoolDispatcher;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class tests the Distributed Logging service by starting a Reactor and then sending it
 * concurrent logging requests using multiple clients.
 */
@Slf4j
public class ReactorTest {


  /**
   * Test the application using pooled thread dispatcher.
   *
   * @throws IOException          if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingThreadPoolDispatcher() throws IOException, InterruptedException {
    log.info("testAppUsingThreadPoolDispatcher start");
    var app = new App(new ThreadPoolDispatcher(2));
    app.start();

    assertNotNull(app);

    var client = new AppClient();
    client.start();

    assertNotNull(client);

    // allow clients to send requests. Artificial delay.
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("sleep interrupted", e);
    }

    client.stop();

    app.stop();
    log.info("testAppUsingThreadPoolDispatcher stop");
  }

  /**
   * Test the application using same thread dispatcher.
   *
   * @throws IOException          if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingSameThreadDispatcher() throws IOException, InterruptedException {
    log.info("testAppUsingSameThreadDispatcher start");
    var app = new App(new SameThreadDispatcher());
    app.start();

    assertNotNull(app);

    var client = new AppClient();
    client.start();

    assertNotNull(client);

    // allow clients to send requests. Artificial delay.
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("sleep interrupted", e);
    }

    client.stop();

    app.stop();
    log.info("testAppUsingSameThreadDispatcher stop");
  }
}
