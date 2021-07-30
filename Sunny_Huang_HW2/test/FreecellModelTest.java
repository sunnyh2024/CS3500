import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuite;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests for the FreecellModel interface and all dependencies: tests to ensure that ICard and all
 * methods inside work correctly.
 */
public class FreecellModelTest {

  private SimpleFreecellModel game1;
  private SimpleFreecellModel randGame;
  private List<ICard> deck;
  private List<ICard> badDeck;
  private List<ICard> dupDeck;

  private String noShuffle;

  @Before
  public void init() {
    ICard c1 = new Card(CardValue.ACE, CardSuite.SPADE);
    ICard c2 = new Card(CardValue.TWO, CardSuite.SPADE);
    ICard c3 = new Card(CardValue.FIVE, CardSuite.HEART);
    ICard c4 = new Card(CardValue.TEN, CardSuite.CLUB);

    game1 = new SimpleFreecellModel();
    randGame = new SimpleFreecellModel();
    deck = game1.getDeck();
    badDeck = new ArrayList<>(List.of(c1, c2, c3, c4));
    dupDeck = new ArrayList<>(List.of(c1, c2, c3, c4, c1, c4));

    noShuffle = "F1: \nF2: \nF3: \nF4: \nO1: \nO2: \nO3: \nO4: \n"
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
    assertFalse(game1.isGameOver());
  }

  @Test
  public void testIsGameOverStarted() {
    game1.startGame(deck, 8, 3, false);
    assertFalse(game1.isGameOver());
  }

  @Test
  public void testIsGameOverAlmostTrue() {
    game1.startGame(deck, 4, 1, false);
    for (int i = 13; i > 1; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        game1.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertFalse(game1.isGameOver());
  }

  @Test
  public void testIsGameOverTrue() {
    game1.startGame(deck, 4, 1, false);
    for (int i = 13; i > 0; i -= 1) {
      for (int j = 0; j < 4; j += 1) {
        game1.move(PileType.CASCADE, j, i - 1, PileType.FOUNDATION, j);
      }
    }
    assertTrue(game1.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadCascade() {
    game1.startGame(deck, 2, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadOpen() {
    game1.startGame(deck, 6, -1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadDeck() {
    game1.startGame(badDeck, 6, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDupedDeck() {
    game1.startGame(dupDeck, 6, 3, false);
  }

  @Test
  public void testStartGameNoShuffle() {
    game1.startGame(deck, 8, 4, false);
    assertEquals(noShuffle, new FreecellTextView(game1).toString());
  }

  @Test
  public void testStartGameShuffled() {
    randGame.startGame(deck, 8, 4, true);
    assertNotEquals(noShuffle, new FreecellTextView(randGame).toString());
  }

  @Test
  public void testStartGameShuffledSmallPiles() {
    randGame.startGame(deck, 52, 4, true);
    assertNotEquals(noShuffle, new FreecellTextView(randGame).toString());
  }

  @Test
  public void testStartGameRestarted() {
    game1.startGame(deck, 8, 4, false);
    game1.startGame(deck, 4, 1, true);
    assertEquals(4, game1.getNumCascadePiles());
    assertEquals(1, game1.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNotStarted() {
    game1.move(PileType.FOUNDATION, 2, 3, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadPileNumber() {
    game1.startGame(deck, 8, 4, false);
    game1.move(PileType.FOUNDATION, 5, 0, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadCardIndex() {
    game1.startGame(deck, 8, 4, false);
    game1.move(PileType.CASCADE, 5, 13, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBadCardIndex2() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 3, 10, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCascade() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidOpen() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidFoundation() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    game1.move(PileType.CASCADE, 2, 11, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveCascadeToFoundation() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 0);
    assertEquals("A♠", game1.getFoundationCardAt(0, 0).toString());
  }

  @Test
  public void testMoveCascadeToCascade() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    game1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals("A♠", game1.getCascadeCardAt(0, 12).toString());
  }

  @Test
  public void testMoveCascadeToOpen() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    assertEquals("A♠", game1.getOpenCardAt(0).toString());
  }

  @Test
  public void testMoveOpenToFoundation() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    game1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals("A♠", game1.getFoundationCardAt(0, 0).toString());
  }

  @Test
  public void testMoveOpenToCascade() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 1);
    game1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 2);
    assertEquals("A♥", game1.getCascadeCardAt(2, 12).toString());
  }

  @Test
  public void testMoveOpenToOpen() {
    game1.startGame(deck, 4, 4, false);
    game1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    game1.move(PileType.OPEN, 0, 0, PileType.OPEN, 3);
    assertEquals("A♠", game1.getOpenCardAt(3).toString());
  }
}