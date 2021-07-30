import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import view.ITextView;
import view.SimpleTextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the SimpleTextView class and all dependencies: tests to ensure that SimpleTextView and
 * all methods inside work correctly.
 */
public class SimpleTextViewTest {

  ITextView view;
  StringBuilder out;

  @Before
  public void init() {
    this.out = new StringBuilder();
    this.view = new SimpleTextView(this.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullAppendable() {
    new SimpleTextView(null);
  }

  // Test renderMessage to make sure that it appends the given message to the Appendable
  @Test
  public void testRenderMessageWithAppendable() {
    try {
      this.view.renderMessage("Fake message");
    } catch (IOException ioe) {
      // do nothing
    }
    assertEquals("Fake message", this.out.toString());
  }

  // Test renderMessage with a Fake Appendable to make sure the
  // method properly throws an IOException
  @Test(expected = IllegalStateException.class)
  public void testRenderMessageWithFakeAppendable() {
    ITextView fakeView = new SimpleTextView(new FakeAppendable());
    try {
      fakeView.renderMessage("Fake message");
    } catch (IOException ioe) {
      throw new IllegalStateException("");
    }
  }

  // Test renderMessage with no Appendable (Uses system.out)
  @Test
  public void testRenderMessageWithNoAppendable() {
    ITextView viewNoAp = new SimpleTextView();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    try {
      viewNoAp.renderMessage("Fake Message");
    } catch (IOException ioe) {
      // do nothing
    }
    String expectedString = "Fake Message\r\n";
    assertEquals(expectedString, outContent.toString());
    System.setOut(System.out);
  }
}
