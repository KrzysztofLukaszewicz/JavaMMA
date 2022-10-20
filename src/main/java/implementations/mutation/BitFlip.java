package implementations.mutation;

import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;

public class BitFlip implements SolutionOperatorFactory<Integer> {

  @Override
  public SolutionOperator apply(Integer index) {
    return (sol) -> {
      int val = (int) sol.get(index);
      if (val == 1) {
        sol.set(index, 0);
      } else {
        sol.set(index, 1);
      }
      return sol;
    };
  }
}
