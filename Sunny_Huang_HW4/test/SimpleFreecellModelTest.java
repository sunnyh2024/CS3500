import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import org.junit.Test;

/**
 * tests for the MultiFreecellModel class and all dependencies: tests to ensure that the model and
 * all methods inside work correctly.
 */

public class SimpleFreecellModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleCardInvalidMove() {
    FreecellModel<ICard> model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 7);
  }
}
