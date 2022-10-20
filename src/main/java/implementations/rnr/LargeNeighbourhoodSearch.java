package implementations.rnr;

import implementations.hillclimb.NextAscent;
import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.factories.SolutionOperatorFactory;
import framework.utils.Methods;

public class LargeNeighbourhoodSearch implements SolutionOperatorFactory<Double> {

  private final NextAscent nextAscent = new NextAscent();

  @Override
  public SolutionOperator apply(Double dos) {
    return solution -> {
      double bestValue = solution.getSolutionValue();
      Solution ret = solution.clone();

      for (int i = 0; i < Methods.iomDosMap(dos); i++) {
        ret = recreate(ruin(ret));
        double currentValue = ret.getSolutionValue();

        if (currentValue >= bestValue) {
          return ret;
        }
      }
      return solution;
    };
  }

  private Solution ruin(Solution sol) {
    if (Math.random() < 0.5) {
      for (int i = 0; i < sol.size() / 2; i++) {
        sol.set(i, 0);
      }
    } else {
      for (int i = sol.size() / 2; i < sol.size(); i++) {
        sol.set(i, 0);
      }
    }
    return sol;
  }

  private Solution recreate(Solution sol) {
    return nextAscent.apply(Methods.iomDosMap(0.0)).apply(sol);
  }
}
