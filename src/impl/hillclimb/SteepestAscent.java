package impl.hillclimb;

import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;
import impl.mutation.BitFlip;

public class SteepestAscent implements SolutionOperatorFactory<Double> {

  private final BitFlip bitFlip = new BitFlip();

  @Override
  public SolutionOperator apply(Double depthOfSearch) {
    return (sol) -> {
      Solution ret = sol;
      for (int i = 0; i < depthOfSearch; i++) {

        double bestValue = ret.getSolutionValue();
        boolean improved = false;
        int bestIndex = 0;

        for (int k = 0; k < ret.size(); k++) {
          ret = bitFlip.apply(k).apply(ret);
          double tempValue = ret.getSolutionValue();

          if (tempValue > bestValue) {
            bestIndex = k;
            bestValue = tempValue;
            improved = true;
          }

          ret = bitFlip.apply(k).apply(ret);
        }

        if (improved) {
          ret = bitFlip.apply(bestIndex).apply(ret);
        }
      }
      return ret;
    };
  }
}
