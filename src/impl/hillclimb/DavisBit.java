package impl.hillclimb;

import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;
import impl.mutation.BitFlip;

import java.util.stream.IntStream;

public class DavisBit implements SolutionOperatorFactory<Double> {

  private final BitFlip bitFlip = new BitFlip();

  @Override
  public SolutionOperator apply(Double depthOfSearch) {
    return (sol) -> {
      Solution ret = sol;
      for (int i = 0; i < depthOfSearch; i++) {

        double bestValue = ret.getSolutionValue();
        Integer[] indexes = IntStream.range(0, ret.size()).boxed().toArray(Integer[]::new);
        indexes = Methods.arrayShuffle(indexes);

        for (int k = 0; k < ret.size(); k++) {

          ret = bitFlip.apply(indexes[k]).apply(ret);
          double tempValue = ret.getSolutionValue();

          if (tempValue > bestValue) {
            bestValue = tempValue;
          } else {
            ret = bitFlip.apply(indexes[k]).apply(ret);
          }
        }
      }
      return ret;
    };
  }
}
