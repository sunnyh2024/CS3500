import java.io.IOException;

/**
 * Represents a fake Appendable in which it's sole purpose is to only throw IOExceptions for
 * testing.
 */
public class FakeAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("");
  }
}

