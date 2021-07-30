import java.io.IOException;

/**
 * Dummy Appendable class used to test IOException cases in Controller, RenderBoard and
 * RenderMessage tests.
 */
public class IOEAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("testing purposes.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("testing purposes.");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("testing purposes.");
  }
}
