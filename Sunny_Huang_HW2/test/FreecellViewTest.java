import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * tests for the FreecellView interface and all dependencies: tests to ensure that ICard and all
 * methods inside work correctly.
 */
public class FreecellViewTest {

  private SimpleFreecellModel game1;
  private FreecellTextView textView;
  private String noShuffle;
  private String noShuffle4Pile;
  private String gameEnd;
  private String openPiles;
  private List<ICard> deck;

  @Before
  public void init() {
    game1 = new SimpleFreecellModel();
    textView = new FreecellTextView(game1);
    noShuffle = "F1: \nF2: \nF3: \nF4: \nO1: \nO2: \nO3: \nO4: \n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\nC2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\nC4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\nC6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\nC8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣";
    noShuffle4Pile = "F1: \nF2: \nF3: \nF4: \nO1: \nO2: \nO3: \nO4: \n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣";
    gameEnd = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F3: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1: \nC1: \nC2: \nC3: \nC4:";
    openPiles = "F1: \nF2: \nF3: \nF4: \nO1: A♥\nO2: \nO3: \nO4: 2♥\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣";
    deck = game1.getDeck();
  }

  @Test
  public void testToStringNoShuffle() {
    game1.startGame(deck, 8, 4, false);
    assertEquals(noShuffle, textView.toString());
  }

  @Test
  public void testToStringWithShuffle() {
    game1.startGame(deck, 8, 4, true);
    assertNotEquals(noShuffle, textView.toString());
  }

  @Test
  public void testToStringVaryPiles() {
    game1.startGame(deck, 4, 4, false);
    assertEquals(noShuffle4Pile, textView.toString());
  }

  @Test
  public void testToStringMt() {
    assertEquals("", textView.toString());
  }

  @Test
  public void testToStringGameOver() {
    game1.startGame(deck, 4, 1, false);
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        game1.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertEquals(gameEnd, textView.toString());
  }

  @Test
  public void testToStringOpenPiles() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 3);
    assertEquals(openPiles, textView.toString());
  }
}
