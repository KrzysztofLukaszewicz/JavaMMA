package impl.hillclimb;

import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;
import impl.mutation.BitFlip;

import java.util.Random;

public class RandomMutation implements SolutionOperatorFactory<Double> {

  private final BitFlip bitFlip = new BitFlip();
  private final Random rand = new Random();

  @Override
  public SolutionOperator apply(Double depthOfSearch) {
    return (solution -> {
      Solution ret = solution;
      double bestValue = ret.getSolutionValue();

      for (int i = 0; i < depthOfSearch; i++) {

        int j = rand.nextInt(solution.size());
        ret = bitFlip.apply(j).apply(ret);

        double tempValue = ret.getSolutionValue();

        if (tempValue > bestValue) {

          bestValue = tempValue;

        } else {
          ret = bitFlip.apply(j).apply(ret);
        }
      }

      return ret;
    });
  }
}
