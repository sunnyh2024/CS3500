import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * tests for the FreecellView interface and all dependencies: tests to ensure that the view and all
 * methods inside work correctly.
 */
public abstract class AFreecellViewTest {

  private FreecellModel<ICard> model;
  private FreecellView textView;
  private String noShuffle;
  private String noShuffle4Pile;
  private String gameEnd;
  private String openPiles;
  private List<ICard> deck;
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private Appendable real;
  private FreecellView fakeView;
  private FreecellView realView;
  private FreecellView realNoNull;

  @Before
  public void init() {
    model = createModel();
    textView = new FreecellTextView(model);
    noShuffle = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\nC2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\nC4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\nC6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\nC8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣";
    noShuffle4Pile = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣";
    gameEnd = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F3: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nC1:\nC2:\nC3:\nC4:";
    openPiles = "F1:\nF2:\nF3:\nF4:\nO1: A♥\nO2:\nO3:\nO4: 2♥\n"
        + "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n"
        + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣";
    deck = model.getDeck();
    System.setOut(new PrintStream(out));
    Appendable fake = new IOEAppendable();
    real = new StringBuilder();
    fakeView = new FreecellTextView(model, fake);
    realView = new FreecellTextView(model);
    realNoNull = new FreecellTextView(model, real);
  }

  @Test
  public void testToStringNoShuffle() {
    model.startGame(deck, 8, 4, false);
    assertEquals(noShuffle, textView.toString());
  }

  @Test
  public void testToStringWithShuffle() {
    model.startGame(deck, 8, 4, true);
    assertNotEquals(noShuffle, textView.toString());
  }

  @Test
  public void testToStringVaryPiles() {
    model.startGame(deck, 4, 4, false);
    assertEquals(noShuffle4Pile, textView.toString());
  }

  @Test
  public void testToStringMt() {
    assertEquals("", textView.toString());
  }

  @Test
  public void testToStringGameOver() {
    model.startGame(deck, 4, 1, false);
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        model.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertEquals(gameEnd, textView.toString());
  }

  @Test
  public void testToStringOpenPiles() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 3);
    assertEquals(openPiles, textView.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewConstructorBadModel() {
    new FreecellTextView(null, real);
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewConstructorBadAp() {
    new FreecellTextView(new SimpleFreecellModel(), null);
  }

  @Test(expected = IOException.class)
  public void renderBoardException() throws IOException {
    fakeView.renderBoard();
  }

  @Test(expected = IOException.class)
  public void renderMessageException() throws IOException {
    fakeView.renderMessage("hello world.");
  }

  @Test
  public void renderMessageNoException() throws IOException {
    realView.renderMessage("test");
    assertEquals("test", out.toString());
  }

  @Test
  public void renderMessageNonNullAp() throws IOException {
    real.append("test");
    realNoNull.renderMessage("ing purpose.");
    assertEquals("testing purpose.", real.toString());
  }

  @Test
  public void renderMessageMtAp() throws IOException {
    realNoNull.renderMessage("Hello world.");
    assertEquals("Hello world.", real.toString());
  }

  @Test
  public void renderBoardNoException() throws IOException {
    realView.renderBoard();
    assertEquals("", out.toString());
  }

  @Test
  public void renderBoardNonNullAp() throws IOException {
    real.append("test");
    realNoNull.renderBoard();
    assertEquals("test", real.toString());
  }

  @Test
  public void renderBoardMtAp() throws IOException {
    realNoNull.renderBoard();
    assertEquals("", real.toString());
  }

  /**
   * returns the model that the view will take in to be tested.
   *
   * @return that will be used by view
   */
  protected abstract FreecellModel<ICard> createModel();

  /**
   * tests for the FreecellView interface and all dependencies: tests to ensure that the view and
   * all methods inside work correctly using a SimpleFreecellModel as its model.
   */
  public static final class SimpleFreecellViewTest extends AFreecellViewTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * tests for the FreecellView interface and all dependencies: tests to ensure that the view and
   * all methods inside work correctly using a MultiFreecellModel as its model.
   */
  public static final class MultiFreecellViewTest extends AFreecellViewTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new MultiFreecellModel();
    }
  }
}
