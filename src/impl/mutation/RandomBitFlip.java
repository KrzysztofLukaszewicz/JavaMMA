package impl.mutation;

import framework.general.EvaluationFunction;
import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;

public class RandomBitFlip implements SolutionOperatorFactory<Double> {

  /**
   * @param prob Probability of mutation
   * @return
   */
  @Override
  public SolutionOperator apply(Double prob) {
    return solution -> {
      EvaluationFunction evalFunction = solution.getEvaluationFunction();
      Solution ret = new Solution(evalFunction);

      for (int i = 0; i < solution.size(); i++) {
        if (Math.random() < prob) {
          Integer x = (Integer) solution.get(i);
          if (x == 1) {
            x = 0;
          } else {
            x = 1;
          }
          ret.add(x);
        } else {
          ret.add(solution.get(i));
        }
      }
      return ret;
    };
  }
}
