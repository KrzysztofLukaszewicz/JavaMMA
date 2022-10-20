package framework.factories;

import framework.general.SolutionOperator;

import java.util.function.Function;

/**
 * Used to create parameterized solution operators
 * Basically currying
 * @param <T> Type of the parameter
 **/
@FunctionalInterface
public interface SolutionOperatorFactory<T> extends Function<T, SolutionOperator> {

  @Override
  SolutionOperator apply(T obj);
}
