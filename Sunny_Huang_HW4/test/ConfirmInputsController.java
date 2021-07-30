import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import java.util.List;

/**
 * Class to confirm that the inputs for the FreecellController tests are correct.
 */
public class ConfirmInputsController implements FreecellModel<ICard> {

  final StringBuilder log;

  /**
   * constructs a ConfirmInputsFreecellModel using a StringBuilder.
   *
   * @param log StringBuilder that will be used to log the inputs for the controller
   * @throws IllegalArgumentException if given log is invalid
   */
  public ConfirmInputsController(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("log cannot be null.");
    }
    this.log = log;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    log.append("Deck: ");
    for (ICard card : deck) {
      log.append(card.toString());
    }
    log.append(String
        .format("\nwith %d cascade piles, %d open piles. \nshuffled: %s.", numCascadePiles,
            numOpenPiles, shuffle));
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    log.append(String
        .format("\nsource: %s #%d card #%d \ndestination: %s #%d", source, pileNumber + 1,
            cardIndex + 1,
            destination, destPileNumber + 1));
  }

  @Override
  public List<ICard> getDeck() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCascadePiles() {
    return 0;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    return 0;
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    return null;
  }
}
