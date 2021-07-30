import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuite;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw04.MultiFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * tests for the MultiFreecellModel class and all dependencies: tests to ensure that the model and
 * all methods inside work correctly.
 */
public class MultiFreecellModelTest {

  private FreecellModel<ICard> multiModel;
  private FreecellView view;

  @Before
  public void init() {
    multiModel = new MultiFreecellModel();
    view = new FreecellTextView(multiModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNotEnoughFreePiles() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    multiModel.move(PileType.CASCADE, 1, 11, PileType.OPEN, 2);
    multiModel.move(PileType.CASCADE, 1, 10, PileType.OPEN, 3);
    multiModel.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("F1: A♣, 2♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2: A♦\n"
        + "O3: 2♦\n"
        + "O4: 3♦\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveDestTopCardBadValue() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveDestTopCardBadColor() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("F1: A♦, 2♦\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToNonCascadePileOpen() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToNonCascadePileFoundation() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCardsNotFollowingCascadeRules() {
    multiModel.startGame(multiModel.getDeck(), 13, 4, false);
    multiModel.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: 4♣\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, 10♦, 7♠\n"
        + "C2: K♦, 10♠, 7♣, 3♥\n"
        + "C3: K♠, 10♣, 6♥, 3♦\n"
        + "C4: K♣, 9♥, 6♦, 3♠\n"
        + "C5: Q♥, 9♦, 6♠, 3♣\n"
        + "C6: Q♦, 9♠, 6♣, 2♥\n"
        + "C7: Q♠, 9♣, 5♥, 2♦\n"
        + "C8: Q♣, 8♥, 5♦, 2♠\n"
        + "C9: J♥, 8♦, 5♠, 2♣\n"
        + "C10: J♦, 8♠, 5♣, A♥\n"
        + "C11: J♠, 8♣, 4♥, A♦\n"
        + "C12: J♣, 7♥, 4♦, A♠\n"
        + "C13: 10♥, 7♦, 4♠, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 2, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCardsNotDecreasingByOne() {
    ArrayList<ICard> deck = new ArrayList<>();
    for (CardSuite suite : CardSuite.values()) {
      for (CardValue val : CardValue.values()) {
        deck.add(new Card(val, suite));
      }
    }
    multiModel.startGame(deck, 13, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, K♦, K♠, K♣\n"
        + "C2: Q♥, Q♦, Q♠, Q♣\n"
        + "C3: J♥, J♦, J♠, J♣\n"
        + "C4: 10♥, 10♦, 10♠, 10♣\n"
        + "C5: 9♥, 9♦, 9♠, 9♣\n"
        + "C6: 8♥, 8♦, 8♠, 8♣\n"
        + "C7: 7♥, 7♦, 7♠, 7♣\n"
        + "C8: 6♥, 6♦, 6♠, 6♣\n"
        + "C9: 5♥, 5♦, 5♠, 5♣\n"
        + "C10: 4♥, 4♦, 4♠, 4♣\n"
        + "C11: 3♥, 3♦, 3♠, 3♣\n"
        + "C12: 2♥, 2♦, 2♠, 2♣\n"
        + "C13: A♥, A♦, A♠, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 12, 0, PileType.CASCADE, 11);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCardsNotAlternatingColors() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    multiModel.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 3);
    multiModel.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 3);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4: A♦, 2♦\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", view.toString());
    multiModel.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 1);
  }

  @Test
  public void moveValidMoveFreeOpenPileOnly() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    multiModel.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 0);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    multiModel.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 3);
    assertEquals("F1: A♣, 2♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♥, A♠", view.toString());
  }

  @Test
  public void moveValidMoveFreeCascadePileOnly() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    for (int i = 13; i > 0; i -= 1) {
      multiModel.move(PileType.CASCADE, 0, i - 1, PileType.FOUNDATION, 0);
    }
    multiModel.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 3, 12, PileType.CASCADE, 1);
    multiModel.move(PileType.CASCADE, 3, 11, PileType.OPEN, 3);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.OPEN, 1);
    multiModel.move(PileType.CASCADE, 2, 11, PileType.OPEN, 2);
    multiModel.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♦\n"
        + "O2: A♠\n"
        + "O3: 2♠\n"
        + "O4: 2♣\n"
        + "C1:\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♦, A♣", view.toString());
  }

  @Test
  public void moveValidMoveFreeOpenAndCascadePiles() {
    multiModel.startGame(multiModel.getDeck(), 4, 4, false);
    for (int i = 12; i > 1; i -= 1) {
      multiModel.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
    }
    multiModel.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    multiModel.move(PileType.CASCADE, 0, 0, PileType.OPEN, 1);
    multiModel.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 1);
    multiModel.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 1);
    multiModel.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 2);
    multiModel.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 1);
    multiModel.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
    multiModel.move(PileType.CASCADE, 1, 10, PileType.OPEN, 2);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F2: A♣, 2♣\n"
        + "F3: A♦\n"
        + "F4:\n"
        + "O1: Q♥\n"
        + "O2: K♥\n"
        + "O3: 3♦\n"
        + "O4:\n"
        + "C1:\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♦, A♠", view.toString());
    multiModel.move(PileType.CASCADE, 3, 10, PileType.CASCADE, 1);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F2: A♣, 2♣\n"
        + "F3: A♦\n"
        + "F4:\n"
        + "O1: Q♥\n"
        + "O2: K♥\n"
        + "O3: 3♦\n"
        + "O4:\n"
        + "C1:\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♣, 2♦, A♠\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣", view.toString());
  }
}
