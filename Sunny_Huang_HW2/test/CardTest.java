import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.CardSuite;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests for the card interface and all dependencies: tests to ensure that ICard and all methods
 * inside work correctly.
 */
public class CardTest {

  private ICard c1;
  private ICard c2;
  private ICard c3;
  private ICard c4;
  private ICard c5;
  private ICard c6;

  @Before
  public void init() {
    c1 = new Card(CardValue.ACE, CardSuite.SPADE);
    c2 = new Card(CardValue.ACE, CardSuite.SPADE);
    c3 = new Card(CardValue.FIVE, CardSuite.HEART);
    c4 = new Card(CardValue.TEN, CardSuite.CLUB);
    c5 = new Card(CardValue.KING, CardSuite.DIAMOND);
    c6 = new Card(CardValue.JACK, CardSuite.SPADE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardConstructorAllNull() {
    new Card(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardConstructorNullValue() {
    new Card(null, CardSuite.SPADE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardConstructorNullSuite() {
    new Card(CardValue.ACE, null);
  }

  @Test
  public void testCardValueToStringLetter() {
    assertEquals("A", CardValue.ACE.toString());
  }

  @Test
  public void testCardValueToStringLetter2() {
    assertEquals("K", CardValue.KING.toString());
  }

  @Test
  public void testCardValueToStringNumber() {
    assertEquals("5", CardValue.FIVE.toString());
  }

  @Test
  public void testCardValueToStringDoubleDigit() {
    assertEquals("10", CardValue.TEN.toString());
  }

  @Test
  public void testCardSuiteToStringHeart() {
    assertEquals("♥", CardSuite.HEART.toString());
  }

  @Test
  public void testCardSuiteToStringSpade() {
    assertEquals("♠", CardSuite.SPADE.toString());
  }

  @Test
  public void testCardSuiteToStringDiamond() {
    assertEquals("♦", CardSuite.DIAMOND.toString());
  }

  @Test
  public void testCardSuiteToStringClub() {
    assertEquals("♣", CardSuite.CLUB.toString());
  }

  @Test
  public void testToString() {
    assertEquals("A♠", c1.toString());
  }

  @Test
  public void testToString2() {
    assertEquals("10♣", c4.toString());
  }

  @Test
  public void testGetValueNum() {
    assertEquals("10", c4.getValue());
  }

  @Test
  public void testGetValueChar() {
    assertEquals("K", c5.getValue());
  }

  @Test
  public void testGetSuiteHeart() {
    assertEquals("♥", c3.getSuite());
  }

  @Test
  public void testGetSuiteClub() {
    assertEquals("♣", c4.getSuite());
  }

  @Test
  public void testGetSuiteDiamond() {
    assertEquals("♦", c5.getSuite());
  }

  @Test
  public void testGetSuiteSpade() {
    assertEquals("♠", c6.getSuite());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValueOneGreaterException() {
    c1.isValueOneGreater(null);
  }

  @Test
  public void testisValueOneGreaterTrue() {
    assertTrue(c4.isValueOneGreater(c6));
  }

  @Test
  public void testIsValueOneGreaterKing() {
    assertFalse(c6.isValueOneGreater(new Card(CardValue.KING, CardSuite.SPADE)));
  }

  @Test
  public void testIsValueOneGreaterKingToAce() {
    assertFalse(c6.isValueOneGreater(c1));
  }

  @Test
  public void testisValueOneGreaterFalse() {
    assertFalse(c1.isValueOneGreater(c6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsSameSuiteException() {
    c1.isSameSuite(null);
  }

  @Test
  public void testIsSameSuiteTrue() {
    assertTrue(c2.isSameSuite(c6));
  }

  @Test
  public void testIsSameSuiteFalse() {
    assertFalse(c3.isSameSuite(c6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsOppositeColorException() {
    c1.isOppositeColor(null);
  }

  @Test
  public void testIsOppositeColorTrue() {
    assertTrue(c5.isOppositeColor(c6));
  }

  @Test
  public void testIsOppositeColorTrue2() {
    assertTrue(c3.isOppositeColor(c4));
  }

  @Test
  public void testIsOppositeColorFalse() {
    assertFalse(c2.isOppositeColor(c6));
  }

  @Test
  public void testIsOppositeColorFalse2() {
    assertFalse(c3.isOppositeColor(c5));
  }

  @Test
  public void testSameCardTrue() {
    assertTrue(c1.sameCard(c2));
  }

  @Test
  public void testSameCardFalse() {
    assertFalse(c1.sameCard(c4));
  }

  @Test
  public void testSameCardNull() {
    assertFalse(c1.sameCard(null));
  }
}
