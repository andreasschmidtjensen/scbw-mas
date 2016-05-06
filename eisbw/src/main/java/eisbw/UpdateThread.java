package eisbw;

import jnibwapi.JNIBWAPI;

public class UpdateThread extends Thread {

  private Game game;
  private JNIBWAPI bwapi;

  public UpdateThread(Game game, JNIBWAPI bwapi) {
    this.bwapi = bwapi;
    this.game = game;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(100);
      } catch (Exception ex) {
        continue;
      }
      game.update(bwapi);
    }
  }
}
