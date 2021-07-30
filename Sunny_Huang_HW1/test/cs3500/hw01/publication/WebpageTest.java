package cs3500.hw01.publication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Webpage: unit tests to ensure that Webpages can be cited correctly and otherwise
 * behave correctly.
 */
public class WebpageTest {

  Publication cs3500 = new Webpage("CS3500: Object-Oriented Design",
      "https://www.ccs.neu.edu/course/cs3500/",
      "August 11, 2014");
  Publication mt = new Webpage("", "", "");

  @Test
  public void testCiteApa() {
    assertEquals(
        "CS3500: Object-Oriented Design. Retrieved August 11, 2014,"
            + " from https://www.ccs.neu.edu/course/cs3500/.", cs3500.citeApa());
  }

  @Test
  public void testCiteApaMt() {
    assertEquals(". Retrieved , from .", mt.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals(
        "\"CS3500: Object-Oriented Design.\""
            + " Web. August 11, 2014 <https://www.ccs.neu.edu/course/cs3500/>.", cs3500.citeMla());
  }

  @Test
  public void testCiteMlaMt() {
    assertEquals("\".\" Web.  <>.", mt.citeMla());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingTitle() {
    new Webpage(null, "https://www.ccs.neu.edu/course/cs3500/",
        "August 11, 2014");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingUrl() {
    new Webpage("CS3500: Object-Oriented Design", null,
        "August 11, 2014");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingRetrieved() {
    new Webpage("CS3500: Object-Oriented Design",
        "https://www.ccs.neu.edu/course/cs3500/", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingTitleAndUrl() {
    new Webpage(null,
        null, "August 11, 2014");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingTitleAndRetrieved() {
    new Webpage(null,
        "https://www.ccs.neu.edu/course/cs3500/", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingUrlAndRetrieved() {
    new Webpage("CS3500: Object-Oriented Design",
        null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingAll() {
    new Webpage(null, null, null);
  }
}