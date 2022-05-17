package impl.hillclimb;

import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;
import impl.mutation.BitFlip;

public class NextAscent implements SolutionOperatorFactory<Double> {

  private final BitFlip bitFlip = new BitFlip();

  @Override
  public SolutionOperator apply(Double depthOfSearch) {
    return (sol) -> {
      Solution ret = sol;
      for (int i = 0; i < Methods.iomDosMap(depthOfSearch); i++) {

        double bestValue = ret.getSolutionValue();

        for (int k = 0; k < ret.size(); k++) {

          ret = bitFlip.apply(k).apply(ret);
          double tempValue = ret.getSolutionValue();

          if (tempValue > bestValue) {
            bestValue = tempValue;
          } else {
            ret = bitFlip.apply(k).apply(ret);
          }
        }
      }
      return ret;
    };
  }
}
