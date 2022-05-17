package framework.genetic;

import framework.general.EvaluationFunction;
import framework.general.Solution;
import framework.general.SolutionMemory;
import framework.general.SolutionOperator;
import framework.utils.GetRandom;

import java.util.ArrayList;

/**
 * Implementation of genetic memory
 * */
public class Genetic extends SolutionMemory {

  protected SolutionOperator mutationSolutionOperator;
  protected CrossoverOperator crossoverOperator;
  protected ParentSelectionFunction psFunc;
  protected ReplacementStrategy replacementStrategy;

  public Genetic(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      GetRandom getRandom,
      SolutionOperator mutationSolutionOperator,
      CrossoverOperator crossoverOperator,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy) {
    super(evaluationFunction, popSize, generationCount, geneCount, useHistory, getRandom);
    this.mutationSolutionOperator = mutationSolutionOperator;
    this.psFunc = psFunc;
    this.crossoverOperator = crossoverOperator;
    this.replacementStrategy = replacementStrategy;
  }

  public Genetic(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      ArrayList bounds,
      SolutionOperator mutationSolutionOperator,
      CrossoverOperator crossoverOperator,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy) {
    super(evaluationFunction, popSize, generationCount, geneCount, useHistory, bounds);
    this.mutationSolutionOperator = mutationSolutionOperator;
    this.psFunc = psFunc;
    this.crossoverOperator = crossoverOperator;
    this.replacementStrategy = replacementStrategy;
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
            // Add to next pop
            nextPop.add(c1);
            nextPop.add(c2);
          }

          ArrayList<Solution> ret = replacementStrategy.apply(currentPopulation, nextPop);

          return ret;
        };
  }
}
