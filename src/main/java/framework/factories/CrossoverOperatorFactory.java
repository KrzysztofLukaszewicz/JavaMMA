package framework.factories;

import framework.genetic.CrossoverOperator;

import java.util.function.Function;

/**
 * Used for creating parameterized crossover operators
 * @param <T> Type of parameter
 * */
@FunctionalInterface
public interface CrossoverOperatorFactory<T> extends Function<T, CrossoverOperator> {

  @Override
  CrossoverOperator apply(T obj);
}
