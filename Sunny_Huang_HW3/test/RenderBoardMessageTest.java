import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * tests for the RenderBoard and RenderMessage methods in the FreecellView interface and
 * FreecellTextView.
 */
public class RenderBoardMessageTest {

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private Appendable real;
  private FreecellView fakeView;
  private FreecellView realView;
  private FreecellView realNoNull;

  @Before
  public void init() {
    System.setOut(new PrintStream(out));
    Appendable fake = new IOEAppendable();
    real = new StringBuilder();
    fakeView = new FreecellTextView(new SimpleFreecellModel(), fake);
    realView = new FreecellTextView(new SimpleFreecellModel());
    realNoNull = new FreecellTextView(new SimpleFreecellModel(), real);
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
}
