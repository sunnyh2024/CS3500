import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * tests for the FreecellModelCreator class in the model package. Tests to ensure that all parts of
 * the create method inside works correctly.
 */
public class FreecellModelCreatorTest {

  @Test(expected = IllegalArgumentException.class)
  public void createNullInput() {
    FreecellModelCreator.create(null);
  }

  @Test
  public void createSingleMove() {
    assertEquals(SimpleFreecellModel.class,
        FreecellModelCreator.create(GameType.SINGLEMOVE).getClass());
  }

  @Test
  public void createMultiMove() {
    assertEquals(MultiFreecellModel.class,
        FreecellModelCreator.create(GameType.MULTIMOVE).getClass());
  }
}
