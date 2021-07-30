package cs3500.freecell.model;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;

/**
 * Factory class for creating the two different types of available Freecell games. Currently
 * supports Simple (single-card moves) and Multi (multiple-card moves).
 */
public class FreecellModelCreator {

  /**
   * enumeration that represents the current available modes of Freecell to be played. SingleMove
   * only allows the player to move one card at a time, whereas MultiMove allows for the player to
   * move multiple cards at a time, as long as the move can also be made with a series of single
   * moves.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * returns the FreecellModel corresponding to the given GameType enum (SimpleFreecellModel for
   * SINGLEMOVE and MultiFreecellModel for MULTIMOVE).
   *
   * @param type GameType that corresponds to a FreecellModel
   * @return corresponding FreecellModel (of type ICard in this case)
   * @throws IllegalArgumentException if type is invalid
   */
  public static FreecellModel<ICard> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("must provide a gameType.");
    }
    if (type == GameType.SINGLEMOVE) {
      return new SimpleFreecellModel();
    } else {
      return new MultiFreecellModel();
    }
  }
}
