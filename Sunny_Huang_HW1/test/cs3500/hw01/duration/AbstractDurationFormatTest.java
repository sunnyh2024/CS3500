package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the format method of {@link Duration}s. Add your tests to this class to assure that
 * your format method works properly
 */
public abstract class AbstractDurationFormatTest {

  @Test
  public void formatExample1() {
    assertEquals("4 hours, 0 minutes, and 9 seconds",
        hms(4, 0, 9)
            .format("%h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void formatExample2() {
    assertEquals("4:05:17",
        hms(4, 5, 17).format("%h:%M:%S"));
  }

  // ADD MORE TESTS HERE. 
  // THE ABOVE TEST NAMES ARE MERE PLACEHOLDERS. NAME YOUR TESTS MEANINGFULLY. 
  // IF YOU NAME YOUR TESTS SIMILAR TO ABOVE, YOU WILL LOSE POINTS!
  // Your tests must only use hms(...) and sec(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new HmsDuration(...)"

  @Test
  public void formatT() {
    assertEquals("10000", hms(2, 46, 40).format("%t"));
  }

  @Test
  public void formatPercent() {
    assertEquals("%", hms(0, 50, 12).format("%%"));
  }

  @Test
  public void formatMt() {
    assertEquals("", sec(1000).format(""));
  }

  @Test
  public void formatUpper() {
    assertEquals("04:05:07",
        hms(4, 5, 7).format("%H:%M:%S"));
  }

  @Test
  public void formatUpper2() {
    assertEquals("14:55:27",
        hms(14, 55, 27).format("%H:%M:%S"));
  }

  @Test
  public void formatLower() {
    assertEquals("4:5:7",
        hms(4, 5, 7).format("%h:%m:%s"));
  }

  @Test
  public void formatLower2() {
    assertEquals("14:35:57",
        hms(14, 35, 57).format("%h:%m:%s"));
  }

  @Test
  public void formatMixed() {
    assertEquals("02:46:40",
        sec(10000).format("%H:%m:%s"));
  }

  @Test
  public void formatZero() {
    assertEquals("000000",
        sec(0).format("%H%M%S"));
  }

  @Test
  public void formatNoPercent() {
    assertEquals("Hello world!", sec(10000).format("Hello world!"));
  }

  @Test
  public void formatWrongSymbol() {
    assertEquals("$H:$M:$S", hms(4, 45, 2).format("$H:$M:$S"));
  }

  @Test
  public void formatDoubleLetter() {
    assertEquals("04h", hms(4, 45, 2).format("%Hh"));
  }

  @Test
  public void formatDoublePercent() {
    assertEquals("Overlapped %m", hms(4, 45, 2).format("Overlapped %%m"));
  }

  @Test
  public void formatRepeat() {
    assertEquals("minutes 3 3 again", hms(4, 3, 2).format("minutes %m %m again"));
  }

  @Test
  public void formatRepeat2() {
    assertEquals("Error: 404 not found", hms(4, 3, 2).format("Error: %h%H not found"));
  }

  @Test
  public void formatAll() {
    assertEquals("it is (4:5:7, 04:05:07, 14707s, %) currently",
        hms(4, 5, 7).format("it is (%h:%m:%s, %H:%M:%S, %ts, %%) currently"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatValidInvalid() {
    hms(12, 3, 4).format("%H %u");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatTriplePercent() {
    hms(12, 3, 4).format("%%%");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatSinglePercent() {
    hms(12, 3, 4).format("%");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatFlipped() {
    sec(3661).format("s%");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatBeginningPercent() {
    hms(12, 3, 4).format("% Hello World");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatMiddlePercent() {
    hms(12, 3, 4).format("Hello % World");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatMiddlePercent2() {
    hms(12, 3, 4).format("%h % %m");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatEndingPercent() {
    hms(12, 3, 4).format("Hello World %");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatUnrecognizedChar() {
    sec(3661).format("malformed %Template");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatNumbers() {
    sec(3661).format("malformed %007");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatNull() {
    sec(3661).format(null);
  }



  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */

  /**
   * Constructs an instance of the class under test representing the duration given in hours,
   * minutes, and seconds.
   *
   * @param hours   the hours in the duration
   * @param minutes the minutes in the duration
   * @param seconds the seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hms(int hours, int minutes, int seconds);

  /**
   * Constructs an instance of the class under test representing the duration given in seconds.
   *
   * @param inSeconds the total seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration sec(long inSeconds);

  /**
   * Concrete class for testing HmsDuration implementation of Duration.
   */
  public static final class HmsDurationTest extends AbstractDurationFormatTest {

    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new HmsDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new HmsDuration(inSeconds);
    }
  }

  /**
   * Concrete class for testing CompactDuration implementation of Duration.
   */
  public static final class CompactDurationTest extends AbstractDurationFormatTest {

    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new CompactDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new CompactDuration(inSeconds);
    }
  }
}
