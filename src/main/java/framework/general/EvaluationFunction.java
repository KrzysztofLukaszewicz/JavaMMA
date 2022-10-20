package framework.general;

import java.util.function.ToDoubleFunction;

/**
 *  Functional interface for the evaluation function
 * */
@FunctionalInterface
public interface EvaluationFunction extends ToDoubleFunction<Solution> {

  @Override
  double applyAsDouble(Solution data);

}
