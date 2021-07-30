import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * tests for the FreecellController interface and all dependencies: tests to ensure that ICard and
 * all methods inside work correctly.
 */
public abstract class AFreecellControllerTest {

  FreecellModel<ICard> model;
  Readable read;
  Appendable append;
  FreecellView view;
  FreecellView nonNullView;

  @Before
  public void init() {
    model = createModel();
    read = new StringReader("");
    append = new StringBuilder();
    view = new FreecellTextView(model);
    nonNullView = new FreecellTextView(model, append);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorBadModel() {
    new SimpleFreecellController(null, new StringReader(""), append);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorBadRead() {
    new SimpleFreecellController(model, null, append);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorBadAppend() {
    new SimpleFreecellController(model, new StringReader(""), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameBadDeck() {
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(null, 8, 4, true);
  }

  @Test
  public void playGameBadCascade() {
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 2, 4, true);
    assertEquals("Could not start game.", append.toString());
  }

  @Test
  public void playGameBadOpen() {
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 6, 0, true);
    assertEquals("Could not start game.", append.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void playGameFailedAppendableWrite() {
    Appendable fake = new IOEAppendable();
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, fake);
    controller.playGame(model.getDeck(), 8, 4, true);
  }

  @Test(expected = IllegalStateException.class)
  public void playGameFailedRead() {
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
  }

  @Test
  public void playGameRestarting() {
    Readable read = new StringReader("C1 7 F1 q q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♥\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
    controller.playGame(model.getDeck(), 8, 4, false);
    ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♥\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely."
        + "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameImmediateQuitLowerCase() {
    Readable read = new StringReader("q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameImmediateQuitUpperCase() {
    Readable read = new StringReader("Q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameQuitAfterMove() {
    Readable read = new StringReader("C1 7 F1 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♥\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameQuitAtCardIndex() {
    Readable read = new StringReader("C1 q F1 4");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void playGameBadQuit() {
    Readable read = new StringReader("Cq1 C2 7 F1Q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void playGameBadDoubleQuit() {
    Readable read = new StringReader("C1 7 F1 Qq");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
  }

  @Test
  public void playGameQuitDuringMove() {
    Readable read = new StringReader("C1 7 Q F1");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameBadPileTypeInputs() {
    Readable read = new StringReader("c1 o3 f2 X1 11 !1 ()1 randomWord \" \" q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameBadSource() {
    Readable read = new StringReader("c1 11 FC2 1C ...? q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid source pile. Try again.\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameBadIndex() {
    Readable read = new StringReader("C1 f ? 3r F13 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid card index. Try again.\n"
        + "Invalid card index. Try again.\n"
        + "Invalid card index. Try again.\n"
        + "Invalid card index. Try again.\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameBadDestination() {
    Readable read = new StringReader("C1 7 G1 f2 ? 64 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid destination pile. Try again.\n"
        + "Invalid destination pile. Try again.\n"
        + "Invalid destination pile. Try again.\n"
        + "Invalid destination pile. Try again.\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameEnsureSameInputType() {
    Readable read = new StringReader("x1 C2 badIndex 7 61 F4 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid source pile. Try again.\n"
        + "Invalid card index. Try again.\n"
        + "Invalid destination pile. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4: A♦\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameInvalidMove() {
    Readable read = new StringReader("C1 7 O6 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid move. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameMultipleInvalidMoves() {
    Readable read = new StringReader("C1 7 O6 F2 5 F1 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid move. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid move. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameValidMove() {
    Readable read = new StringReader("C1 7 O4 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♥\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameMultipleValidMoves() {
    Readable read = new StringReader("C1 7 O4 C2 7 F2 O4 1 F1 C5 6 F1 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♥\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1:\n"
        + "F2: A♦\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♥\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♥\n"
        + "F2: A♦\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♥, 2♥\n"
        + "F2: A♦\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameValidMoveNewLine() {
    Readable read = new StringReader("\nC2 \n7 \nF4 q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4: A♦\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameValidInvalidMove() {
    Readable read = new StringReader("C1 7 O4 C1 3 O4 Q");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    String ans = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♥\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid move. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♥\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.";
    assertEquals(ans, append.toString());
  }

  @Test
  public void playGameToCompletion() {
    StringBuilder input = new StringBuilder();
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 1; j < 5; j += 1) {
        input.append(String.format("C%d %d F%d ", j, i, j));
      }
    }
    Readable read = new StringReader(input.toString());
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 4, 4, false);
    assertTrue(append.toString().contains("\nGame over."));
  }

  @Test
  public void playGameToCompletionWithInvalidMoves() {
    StringBuilder input = new StringBuilder();
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 1; j < 5; j += 1) {
        input.append(String.format("C%d %d F%d ", j, i, j));
        input.append("  bad inputs.e ");
      }
    }
    Readable read = new StringReader(input.toString());
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 4, 4, false);
    assertTrue(append.toString().contains("\nGame over."));
  }

  @Test(expected = IllegalStateException.class)
  public void playGameOutOfInput() {
    Readable read = new StringReader("C1 7 O4 ");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void playGameScrambledInput() {
    Readable read = new StringReader("7 O4 C1 ");
    FreecellController<ICard> controller = new SimpleFreecellController(model, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
  }

  @Test
  public void playGameConfirmStartGameInputs() {
    Readable read = new StringReader("q");
    StringBuilder builder = new StringBuilder();
    FreecellModel<ICard> inputModel = new ConfirmInputsController(builder);
    FreecellController<ICard> controller = new SimpleFreecellController(inputModel, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(
        "Deck: K♥K♦K♠K♣Q♥Q♦Q♠Q♣J♥J♦J♠J♣10♥10♦10♠10♣9♥9♦9♠9♣8♥8♦8♠8♣7♥7♦7♠7♣6♥6♦6♠6♣5♥5"
            + "♦5♠5♣4♥4♦4♠4♣3♥3♦3♠3♣2♥2♦2♠2♣A♥A♦A♠A♣\n"
            + "with 8 cascade piles, 4 open piles. \n"
            + "shuffled: false.", builder.toString());
  }

  @Test
  public void playGameConfirmMoveInputs() {
    Readable read = new StringReader("C1 7 F1 C2 7 O3 q");
    StringBuilder builder = new StringBuilder();
    FreecellModel<ICard> inputModel = new ConfirmInputsController(builder);
    FreecellController<ICard> controller = new SimpleFreecellController(inputModel, read, append);
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(
        "Deck: K♥K♦K♠K♣Q♥Q♦Q♠Q♣J♥J♦J♠J♣10♥10♦10♠10♣9♥9♦9♠9♣8♥8♦8♠8♣7♥7♦7♠7♣6♥6♦6♠6♣5♥5"
            + "♦5♠5♣4♥4♦4♠4♣3♥3♦3♠3♣2♥2♦2♠2♣A♥A♦A♠A♣\n"
            + "with 8 cascade piles, 4 open piles. \n"
            + "shuffled: false.\n"
            + "source: CASCADE #1 card #7 \n"
            + "destination: FOUNDATION #1\n"
            + "source: CASCADE #2 card #7 \n"
            + "destination: OPEN #3", builder.toString());
  }

  /**
   * creates a model that the controller will take in to be tested.
   *
   * @return the model the controller will use
   */
  protected abstract FreecellModel<ICard> createModel();

  /**
   * tests for the FreecellController interface and all dependencies: tests to ensure that the
   * controller and all methods inside work correctly using a SimpleFreecellModel as its model.
   */
  public static final class SimpleFreecellControllerTest extends AFreecellControllerTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * tests for the FreecellController interface and all dependencies: tests to ensure that the
   * controller and all methods inside work correctly using a MultiFreecellModel as its model.
   */
  public static final class MultiFreecellControllerTest extends AFreecellControllerTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new MultiFreecellModel();
    }
  }
}
