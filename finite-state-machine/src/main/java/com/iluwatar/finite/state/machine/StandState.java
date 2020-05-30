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

package com.iluwatar.finite.state.machine;

/**.
 * A standing state for the game character.
 */
public class StandState implements State {
  /**.
   * This method is a handler for event.
   * @param context is context data.
   * @param event is an event.
   */
  @Override
  public void handle(Context context, Event event) {
    switch (event) {
      case MOVING_ON_OFF:
        context.setState(new WalkState());
        start_walking();
        break;
      case MOVING_RUN:
        context.setState(new RunState());
        start_running();
        break;
      default:
        break;
    }
  }

  /**.
   * A method which simulates Game character begin to walk.
   */
  private void start_walking() {
    System.out.println("Stop standing! Game character is walking now!");
  }

  /**.
   * A method which simulates Game character begin to run.
   */
  private void start_running() {
    System.out.println("Stop standing! Game character is running now!");
  }
}
