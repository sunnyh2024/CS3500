import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuite;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.OpenPile;
import cs3500.freecell.model.hw02.FoundationPile;
import cs3500.freecell.model.hw02.CascadePile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests for the Pile interface and all dependencies: tests to ensure that ICard and all methods
 * inside work correctly.
 */
public class PileTest {

  private IPile<ICard> foundation;
  private IPile<ICard> open;
  private IPile<ICard> cascade;

  private ICard c1;
  private ICard c2;
  private ICard c3;

  @Before
  public void init() {
    foundation = new FoundationPile();
    open = new OpenPile();
    cascade = new CascadePile();

    c1 = new Card(CardValue.ACE, CardSuite.SPADE);
    c2 = new Card(CardValue.FIVE, CardSuite.DIAMOND);
    c3 = new Card(CardValue.QUEEN, CardSuite.HEART);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationAddCardNull() {
    foundation.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenAddCardNull() {
    open.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeAddCardNull() {
    cascade.addCard(null);
  }

  @Test
  public void testFoundationAddCard() {
    foundation.addCard(c1);
    assertEquals(1, foundation.size());
  }

  @Test
  public void testOpenAddCard() {
    open.addCard(c1);
    assertEquals(1, open.size());
  }

  @Test
  public void testCascadeAddCard() {
    cascade.addCard(c1);
    assertEquals(1, cascade.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationRemoveCardNull() {
    foundation.removeCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenRemoveCardNull() {
    open.removeCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeRemoveCardNull() {
    cascade.removeCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationRemoveWrongCard() {
    foundation.addCard(c1);
    foundation.removeCard(c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenRemoveWrongCard() {
    open.addCard(c2);
    open.removeCard(c1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeRemoveWrongCard() {
    cascade.addCard(c2);
    cascade.removeCard(c3);
  }

  @Test
  public void testFoundationRemoveCard() {
    foundation.addCard(c1);
    foundation.removeCard(c1);
    assertEquals(0, foundation.size());
  }

  @Test
  public void testOpenRemoveCard() {
    open.addCard(c2);
    open.removeCard(c2);
    assertEquals(0, open.size());
  }

  @Test
  public void testCascadeRemoveCard() {
    cascade.addCard(c3);
    cascade.removeCard(c3);
    assertEquals(0, cascade.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationIllegal() {
    cascade.addCard(c1);
    foundation.addCard(c3);
    foundation.move(cascade, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenIllegal() {
    foundation.addCard(c1);
    open.addCard(c3);
    open.move(foundation, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeIllegal() {
    open.addCard(c2);
    cascade.addCard(c3);
    cascade.move(open, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationSamePile() {
    foundation.addCard(c3);
    foundation.move(foundation, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenSamePile() {
    open.addCard(c3);
    open.move(open, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSamePile() {
    cascade.addCard(c3);
    cascade.move(cascade, c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationMove() {
    foundation.addCard(c1);
    cascade.addCard(new Card(CardValue.TWO, CardSuite.HEART));
    foundation.move(cascade, c1);
  }

  @Test
  public void testOpenMove() {
    ICard c2 = new Card(CardValue.TWO, CardSuite.SPADE);
    foundation.addCard(c1);
    open.addCard(c2);
    open.move(foundation, c2);
    assertEquals(2, foundation.size());
    assertEquals(0, open.size());
  }

  @Test
  public void testCascadeMove() {
    cascade.addCard(c1);
    cascade.move(open, c1);
    assertEquals(1, open.size());
    assertEquals(0, cascade.size());
  }

  @Test
  public void testFoundationValidMove() {
    ICard c2 = new Card(CardValue.TWO, CardSuite.SPADE);
    foundation.addCard(c1);
    cascade.addCard(c2);
    assertTrue(cascade.validMove(foundation, c2));
  }

  @Test
  public void testFoundationValidMoveBadCard() {
    foundation.addCard(c1);
    cascade.addCard(c2);
    assertFalse(cascade.validMove(foundation, c2));
  }

  @Test
  public void testOpenValidMove() {
    cascade.addCard(c2);
    assertTrue(cascade.validMove(open, c2));
  }

  @Test
  public void testOpenValidMoveFull() {
    open.addCard(c1);
    cascade.addCard(c2);
    assertFalse(cascade.validMove(foundation, c2));
  }

  @Test
  public void testCascadeValidMove() {
    ICard c2 = new Card(CardValue.TWO, CardSuite.HEART);
    open.addCard(c1);
    cascade.addCard(c2);
    assertTrue(open.validMove(cascade, c1));
  }

  @Test
  public void testCascadeValidMoveBadCard() {
    foundation.addCard(c1);
    cascade.addCard(c2);
    assertFalse(foundation.validMove(cascade, c2));
  }

  @Test
  public void testFoundationValidMoveHelp() {
    foundation.addCard(c1);
    assertTrue(foundation.validMoveHelp(new Card(CardValue.TWO, CardSuite.SPADE)));
  }

  @Test
  public void testFoundationValidMoveHelpMt() {
    assertTrue(foundation.validMoveHelp(c1));
  }

  @Test
  public void testFoundationValidMoveHelpWrongSuite() {
    foundation.addCard(c1);
    assertFalse(foundation.validMoveHelp(new Card(CardValue.TWO, CardSuite.HEART)));
  }

  @Test
  public void testFoundationValidMoveHelpWrongValue() {
    foundation.addCard(c1);
    assertFalse(foundation.validMoveHelp(new Card(CardValue.THREE, CardSuite.SPADE)));
  }

  @Test
  public void testOpenValidMoveHelp() {
    assertTrue(open.validMoveHelp(c1));
  }

  @Test
  public void testOpenValidMoveHelpFull() {
    open.addCard(c2);
    assertFalse(open.validMoveHelp(c1));
  }

  @Test
  public void testCascadeValidMoveHelp() {
    assertTrue(cascade.validMoveHelp(c1));
  }

  @Test
  public void testCascadeValidMoveHelp2() {
    cascade.addCard(new Card(CardValue.TWO, CardSuite.DIAMOND));
    assertTrue(cascade.validMoveHelp(c1));
  }

  @Test
  public void testCascadeValidMoveHelpWrongValue() {
    cascade.addCard(new Card(CardValue.TWO, CardSuite.DIAMOND));
    assertFalse(cascade.validMoveHelp(new Card(CardValue.THREE, CardSuite.CLUB)));
  }

  @Test
  public void testCascadeValidMoveHelpWrongSuite() {
    cascade.addCard(new Card(CardValue.TWO, CardSuite.DIAMOND));
    assertFalse(cascade.validMoveHelp(new Card(CardValue.ACE, CardSuite.HEART)));
  }

  @Test
  public void testFoundationSize() {
    assertEquals(0, foundation.size());
    foundation.addCard(c1);
    assertEquals(1, foundation.size());
  }

  @Test
  public void testOpenSize() {
    assertEquals(0, open.size());
    open.addCard(c1);
    assertEquals(1, open.size());
  }

  @Test
  public void testCascadeSize() {
    assertEquals(0, cascade.size());
    cascade.addCard(c1);
    assertEquals(1, cascade.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationGetCardBadIndex() {
    foundation.addCard(c1);
    foundation.addCard(c2);
    foundation.getCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationGetCardBadIndex2() {
    foundation.addCard(c1);
    foundation.addCard(c2);
    foundation.getCard(3);
  }

  @Test
  public void testFoundationGetCard() {
    foundation.addCard(c1);
    foundation.addCard(c2);
    assertEquals(c1, foundation.getCard(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenGetCardBadIndex() {
    open.addCard(c1);
    open.addCard(c2);
    open.getCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenGetCardBadIndex2() {
    open.addCard(c1);
    open.addCard(c2);
    open.getCard(3);
  }

  @Test
  public void testOpenGetCard() {
    open.addCard(c1);
    open.addCard(c2);
    assertEquals(c1, open.getCard(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeGetCardBadIndex() {
    cascade.addCard(c1);
    cascade.addCard(c2);
    cascade.getCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeGetCardBadIndex2() {
    cascade.addCard(c1);
    cascade.addCard(c2);
    cascade.getCard(3);
  }

  @Test
  public void testCascadeGetCard() {
    cascade.addCard(c1);
    cascade.addCard(c2);
    assertEquals(c1, cascade.getCard(0));
  }
}
