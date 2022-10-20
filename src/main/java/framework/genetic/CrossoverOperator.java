package framework.genetic;

import framework.general.Solution;

import java.util.function.BiFunction;

/**
 * Interface for Crossover operators
 * */
@FunctionalInterface
public interface CrossoverOperator extends BiFunction<Solution, Solution, Solution[]> {

  @Override
  Solution[] apply(Solution sol, Solution sol2);
}
