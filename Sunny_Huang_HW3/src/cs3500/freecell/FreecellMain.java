package cs3500.freecell;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.io.InputStreamReader;

/**
 * main class that plays the Freecell game.
 */
public class FreecellMain {

  /**
   * Runs the playGame method in the SimpleFreecellController class.
   *
   * @param args input for the game
   */
  public static void main(String[] args) {
    FreecellModel<ICard> model = new SimpleFreecellModel();
    FreecellController<ICard> controller = new SimpleFreecellController(model,
        new InputStreamReader(System.in), System.out);
    controller.playGame(model.getDeck(), 8, 4, false);
  }
}
