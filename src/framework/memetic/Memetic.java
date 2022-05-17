package framework.memetic;

import framework.general.EvaluationFunction;
import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.genetic.CrossoverOperator;
import framework.genetic.Genetic;
import framework.genetic.ParentSelectionFunction;
import framework.genetic.ReplacementStrategy;
import framework.utils.GetRandom;

import java.util.ArrayList;

/**
 * Implementation of the Memetic algorithm
 * */
public class Memetic extends Genetic {

  SolutionOperator hillclimbingOperator;

  public Memetic(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      GetRandom getRandom,
      SolutionOperator mutationSolutionOperator,
      CrossoverOperator crossoverOperator,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy,
      SolutionOperator hillclimbingOperator) {
    super(
        evaluationFunction,
        popSize,
        generationCount,
        geneCount,
        useHistory,
        getRandom,
        mutationSolutionOperator,
        crossoverOperator,
        psFunc,
        replacementStrategy);
    hillclimbingOperator = hillclimbingOperator;
  }

  public Memetic(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      ArrayList bounds,
      SolutionOperator mutationSolutionOperator,
      CrossoverOperator crossoverOperator,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy,
      SolutionOperator hillclimbingOperator) {
    super(
        evaluationFunction,
        popSize,
        generationCount,
        geneCount,
        useHistory,
        bounds,
        mutationSolutionOperator,
        crossoverOperator,
        psFunc,
        replacementStrategy);
    hillclimbingOperator = hillclimbingOperator;
  }

  @Override
  protected void compose() {
    nextGeneration =
        (pop) -> {
          ArrayList<Solution> nextPop = new ArrayList<>();

          for (int i = 0; i < pop.size() / 2; i++) {
            // Pick parents
            Solution p1 = psFunc.apply(pop);
            Solution p2 = psFunc.apply(pop);

            // Apply crossover
            Solution[] children = crossoverOperator.apply(p1, p2);
            Solution c1 = children[0];
            Solution c2 = children[1];

            // Mutate
            c1 = mutationSolutionOperator.apply(c1);
            c2 = mutationSolutionOperator.apply(c2);

            // Climb
            c1 = hillclimbingOperator.apply(c1);
            c2 = hillclimbingOperator.apply(c2);

            // Add to next pop
            nextPop.add(c1);
            nextPop.add(c2);
          }

          ArrayList<Solution> ret = replacementStrategy.apply(currentPopulation, nextPop);

          return ret;
        };
  }
}
