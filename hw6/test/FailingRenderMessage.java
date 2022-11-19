import java.io.IOException;

/**
 * A failing appendable for exception testing.
 */
public class FailingRenderMessage implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Failed CharSequence append");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Failed CharSequence append");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Failed CharSequence append");
  }
}
