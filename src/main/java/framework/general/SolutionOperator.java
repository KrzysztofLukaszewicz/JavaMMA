package framework.general;

import java.util.function.UnaryOperator;

/**
 * Basic solution operator,
 * Gets a solution returns new one.
 * */
@FunctionalInterface
public interface SolutionOperator extends UnaryOperator<Solution> {

  @Override
  Solution apply(Solution solution);
}
