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

package com.iluwatar.mutex;

import lombok.extern.slf4j.Slf4j;

/**
 * Thief is a class which continually tries to acquire a jar and take a bean from it. When the jar
 * is empty the thief stops.
 */
@Slf4j
public class Thief extends Thread {


  /**
   * The name of the thief.
   */
  private final String name;

  /**
   * The jar.
   */
  private final Jar jar;

  public Thief(String name, Jar jar) {
    this.name = name;
    this.jar = jar;
  }

  /**
   * In the run method the thief repeatedly tries to take a bean until none are left.
   */
  @Override
  public void run() {
    var beans = 0;

    while (jar.takeBean()) {
      beans = beans + 1;
      log.info("{} took a bean.", name);
    }

    log.info("{} took {} beans.", name, beans);
  }

}
