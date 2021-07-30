import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuite;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * tests to ensure that the methods in the FreecellModel interface work with its current
 * implementations (SimpleFreecellModel and MultiFreecellModel).
 */
public abstract class AFreecellModelTest {

  protected FreecellModel<ICard> model;
  private List<ICard> deck;
  private List<ICard> badDeck;
  private List<ICard> dupDeck;
  private String noShuffle;

  @Before
  public void init() {
    this.model = createModel();

    ICard c1 = new Card(CardValue.ACE, CardSuite.SPADE);
    ICard c2 = new Card(CardValue.TWO, CardSuite.SPADE);
    ICard c3 = new Card(CardValue.FIVE, CardSuite.HEART);
    ICard c4 = new Card(CardValue.TEN, CardSuite.CLUB);

    deck = model.getDeck();
    badDeck = new ArrayList<>(List.of(c1, c2, c3, c4));
    dupDeck = new ArrayList<>(List.of(c1, c2, c3, c4, c1, c4));

    noShuffle = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
        + "C1: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\nC2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\nC4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\nC6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\nC8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣";
  }

  @Test
  public void testGetDeck() {
    // spot checking points in the ArrayList
    assertEquals("A♣", deck.get(51).toString());
    assertEquals("K♥", deck.get(0).toString());
    assertEquals("7♦", deck.get(25).toString());
  }

  @Test
  public void testIsGameOverFalse() {
    assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverStarted() {
    model.startGame(deck, 8, 3, false);
    assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverAlmostTrue() {
    model.startGame(deck, 4, 1, false);
    for (int i = 13; i > 1; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        model.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverTrue() {
    model.startGame(deck, 4, 1, false);
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        model.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertTrue(model.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadCascade() {
    model.startGame(deck, 2, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadOpen() {
    model.startGame(deck, 6, -1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadDeck() {
    model.startGame(badDeck, 6, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDupedDeck() {
    model.startGame(dupDeck, 6, 3, false);
  }

  @Test
  public void testStartGameNoShuffle() {
    model.startGame(deck, 8, 4, false);
    assertEquals(noShuffle, new FreecellTextView(model).toString());
  }

  @Test
  public void testStartGameShuffled() {
    model.startGame(deck, 8, 4, true);
    assertNotEquals(noShuffle, new FreecellTextView(model).toString());
  }

  @Test
  public void testStartGameShuffledSmallPiles() {
    model.startGame(deck, 52, 4, true);
    assertNotEquals(noShuffle, new FreecellTextView(model).toString());
  }

  @Test
  public void testStartGameRestarted() {
    model.startGame(deck, 8, 4, false);
    model.startGame(deck, 4, 1, true);
    assertEquals(4, model.getNumCascadePiles());
    assertEquals(1, model.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNotStarted() {
    model.move(PileType.FOUNDATION, 2, 3, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadPileNumber() {
    model.startGame(deck, 8, 4, false);
    model.move(PileType.FOUNDATION, 5, 0, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadCardIndex() {
    model.startGame(deck, 8, 4, false);
    model.move(PileType.CASCADE, 5, 13, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadCardIndex2() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 3, 10, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCascade() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidOpen() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidFoundation() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 2, 11, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveCascadeToFoundation() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 0);
    assertEquals("A♠", model.getFoundationCardAt(0, 0).toString());
  }

  @Test
  public void testMoveCascadeToCascade() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("A♠", model.getCascadeCardAt(0, 12).toString());
  }

  @Test
  public void testMoveCascadeToOpen() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    assertEquals("A♠", model.getOpenCardAt(0).toString());
  }

  @Test
  public void testMoveOpenToFoundation() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals("A♠", model.getFoundationCardAt(0, 0).toString());
  }

  @Test
  public void testMoveOpenToCascade() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 1);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 2);
    assertEquals("A♥", model.getCascadeCardAt(2, 12).toString());
  }

  @Test
  public void testMoveOpenToOpen() {
    model.startGame(deck, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 3);
    assertEquals("A♠", model.getOpenCardAt(3).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileNotStarted() {
    model.getNumCardsInFoundationPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileBadIndex() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getNumCardsInFoundationPile(4);
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(0, model.getNumCardsInFoundationPile(2));
    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(1, model.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileNotStarted() {
    model.getNumCardsInCascadePile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileBadIndex() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getNumCardsInCascadePile(9);
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(7, model.getNumCardsInCascadePile(2));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileNotStarted() {
    model.getNumCardsInOpenPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileBadIndex() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getNumCardsInOpenPile(4);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(0, model.getNumCardsInOpenPile(2));
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1, model.getNumCascadePiles());
  }

  @Test
  public void testGetNumCascadePilesStarted() {
    model.startGame(model.getDeck(), 6, 4, false);
    assertEquals(6, model.getNumCascadePiles());
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, model.getNumOpenPiles());
  }

  @Test
  public void testGetNumOpenPilesStarted() {
    model.startGame(model.getDeck(), 6, 3, false);
    assertEquals(3, model.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtNotStarted() {
    model.getFoundationCardAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardBadPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getFoundationCardAt(4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardBadCard() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getFoundationCardAt(3, 3);
  }

  @Test
  public void testGetFoundationCard() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
    assertEquals("A♥", model.getFoundationCardAt(1, 0).toString());
    model.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 1);
    assertEquals("A♥", model.getFoundationCardAt(1, 0).toString());
    assertEquals("2♥", model.getFoundationCardAt(1, 1).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtNotStarted() {
    model.getCascadeCardAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardBadPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getCascadeCardAt(8, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardBadCard() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getCascadeCardAt(3, 10);
  }

  @Test
  public void testGetCascadeCard() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals("A♥", model.getCascadeCardAt(0, 6).toString());
    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
    assertEquals("3♥", model.getCascadeCardAt(0, 5).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtNotStarted() {
    model.getOpenCardAt(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardBadPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.getOpenCardAt(4);
  }

  @Test
  public void testGetOpenCard() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 3);
    assertEquals("A♥", model.getOpenCardAt(3).toString());
  }

  @Test
  public void testGetOpenCardNull() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 3);
    assertNull(model.getOpenCardAt(2));
  }

  /**
   * creates the model that will be tested (either SimpleFreecellModel or MultiFreecellModel).
   *
   * @return FreecellModel
   */
  protected abstract FreecellModel<ICard> createModel();

  /**
   * tests for the SimpleFreecellModel class and all dependencies: tests to ensure that the model
   * and all methods inside work correctly.
   */
  public static final class SimpleFreecellTest extends AFreecellModelTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * tests for the MultiFreecellModel class and all dependencies: tests to ensure that the model and
   * all methods inside work correctly.
   */
  public static final class MultiFreecellTest extends AFreecellModelTest {

    @Override
    protected FreecellModel<ICard> createModel() {
      return new MultiFreecellModel();
    }
  }
}
