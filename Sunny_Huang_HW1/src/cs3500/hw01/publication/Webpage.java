package cs3500.hw01.publication;

/**
 * Represents bibliographic information for Webpages.
 */
public class Webpage implements Publication {

  private final String title;
  private final String url;
  private final String retrieved;

  /**
   * Constructs a Webpage.
   *
   * @param title     the title of the Webpage
   * @param url       the url of the Webpage
   * @param retrieved the date at which the Webpage was retrieved
   */
  public Webpage(String title, String url, String retrieved) {
    if (title == null || url == null || retrieved == null) {
      throw new IllegalArgumentException("missing fields.");
    }

    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  @Override
  public String citeApa() {
    return String.format("%s. Retrieved %s, from %s.", title, retrieved, url);
  }

  @Override
  public String citeMla() {
    return String.format("\"%s.\" Web. %s <%s>.", title, retrieved, url);
  }
}
