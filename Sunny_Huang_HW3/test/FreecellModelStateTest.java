import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * tests for the FreecellModelState interface and all dependencies: tests to ensure that ICard and
 * all methods inside work correctly.
 */
public class FreecellModelStateTest {

  private FreecellModel<ICard> game1;

  @Before
  public void init() {
    game1 = new SimpleFreecellModel();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileNotStarted() {
    game1.getNumCardsInFoundationPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileBadIndex() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getNumCardsInFoundationPile(4);
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    assertEquals(0, game1.getNumCardsInFoundationPile(2));
    game1.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(1, game1.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileNotStarted() {
    game1.getNumCardsInCascadePile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileBadIndex() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getNumCardsInCascadePile(9);
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    assertEquals(7, game1.getNumCardsInCascadePile(2));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileNotStarted() {
    game1.getNumCardsInOpenPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileBadIndex() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getNumCardsInOpenPile(4);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    assertEquals(0, game1.getNumCardsInOpenPile(2));
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1, game1.getNumCascadePiles());
  }

  @Test
  public void testGetNumCascadePilesStarted() {
    game1.startGame(game1.getDeck(), 6, 4, false);
    assertEquals(6, game1.getNumCascadePiles());
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, game1.getNumOpenPiles());
  }

  @Test
  public void testGetNumOpenPilesStarted() {
    game1.startGame(game1.getDeck(), 6, 3, false);
    assertEquals(3, game1.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtNotStarted() {
    game1.getFoundationCardAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardBadPile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getFoundationCardAt(4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardBadCard() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getFoundationCardAt(3, 3);
  }

  @Test
  public void testGetFoundationCard() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
    assertEquals("A♥", game1.getFoundationCardAt(1, 0).toString());
    game1.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 1);
    assertEquals("A♥", game1.getFoundationCardAt(1, 0).toString());
    assertEquals("2♥", game1.getFoundationCardAt(1, 1).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtNotStarted() {
    game1.getCascadeCardAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardBadPile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getCascadeCardAt(8, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardBadCard() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getCascadeCardAt(3, 10);
  }

  @Test
  public void testGetCascadeCard() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    assertEquals("A♥", game1.getCascadeCardAt(0, 6).toString());
    game1.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
    assertEquals("3♥", game1.getCascadeCardAt(0, 5).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtNotStarted() {
    game1.getOpenCardAt(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardBadPile() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.getOpenCardAt(4);
  }

  @Test
  public void testGetOpenCard() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 3);
    assertEquals("A♥", game1.getOpenCardAt(3).toString());
  }

  @Test
  public void testGetOpenCardNull() {
    game1.startGame(game1.getDeck(), 8, 4, false);
    game1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 3);
    assertNull(game1.getOpenCardAt(2));
  }
}

