package framework.multimeme;

import framework.general.EvaluationFunction;
import framework.general.Solution;
import framework.general.SolutionMemory;
import framework.general.SolutionOperator;
import framework.genetic.CrossoverOperator;
import framework.genetic.ParentSelectionFunction;
import framework.genetic.ReplacementStrategy;
import framework.utils.GetRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Multimeme algorithm
 * */
public class MultiMeme extends SolutionMemory {

  private final Map<Solution, Memeplex> memePlexes =
      Collections.synchronizedMap(new HashMap<Solution, Memeplex>());
  private final ParentSelectionFunction psFunc;
  private final ReplacementStrategy replacementStrategy;
  private final MemeplexInheritenceFunction memeplexInheritenceFunction;
  private final MemeplexMutationOperator memeplexMutationOperator;

  public MultiMeme(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      GetRandom getRandom,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy,
      ArrayList<CrossoverOperator> crossoverOperators,
      ArrayList<SolutionOperator> mutationOperators,
      ArrayList<SolutionOperator> hillclimbOperators,
      MemeplexInheritenceFunction memeplexInheritenceFunction,
      MemeplexMutationOperator memeplexMutationOperator,
      ArrayList<SolutionOperator> rnrOperators) {
    super(evaluationFunction, popSize, generationCount, geneCount, useHistory, getRandom);
    this.psFunc = psFunc;
    this.replacementStrategy = replacementStrategy;
    this.memeplexInheritenceFunction = memeplexInheritenceFunction;
    this.memeplexMutationOperator = memeplexMutationOperator;
    initMemes(crossoverOperators, mutationOperators, hillclimbOperators, rnrOperators);
  }

  public MultiMeme(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      ArrayList bounds,
      ParentSelectionFunction psFunc,
      ReplacementStrategy replacementStrategy,
      ArrayList<CrossoverOperator> crossoverOperators,
      ArrayList<SolutionOperator> mutationOperators,
      ArrayList<SolutionOperator> hillclimbOperators,
      MemeplexInheritenceFunction memeplexInheritenceFunction,
      MemeplexMutationOperator memeplexMutationOperator,
      ArrayList<SolutionOperator> rnrOperators) {
    super(evaluationFunction, popSize, generationCount, geneCount, useHistory, bounds);
    this.psFunc = psFunc;
    this.replacementStrategy = replacementStrategy;
    this.memeplexInheritenceFunction = memeplexInheritenceFunction;
    this.memeplexMutationOperator = memeplexMutationOperator;
    initMemes(crossoverOperators, mutationOperators, hillclimbOperators, rnrOperators);
  }

  private void initMemes(
      ArrayList<CrossoverOperator> crossoverOperators,
      ArrayList<SolutionOperator> mutationOperators,
      ArrayList<SolutionOperator> hillclimbOperators,
      ArrayList<SolutionOperator> rnrOperators) {
    for (int i = 0; i < getPopSize(); i++) {
      Memeplex memeplex =
          new Memeplex(crossoverOperators, mutationOperators, rnrOperators, hillclimbOperators);
      memeplex.init();
      memePlexes.put((Solution) this.currentPopulation.get(i), memeplex);
    }
    int xd = 0;
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

            // Get their memetic material
            Memeplex p1_memes;
            Memeplex p2_memes;

            p1_memes = memePlexes.get(p1);
            p2_memes = memePlexes.get(p2);

            // Apply the better crossover
            Solution[] children;

            if (p1_memes.getCrossoverOperatorDelta() >= p2_memes.getCrossoverOperatorDelta()) {
              children = p1_memes.applyCrossoverOperator(p1, p2);
            } else {
              children = p2_memes.applyCrossoverOperator(p1, p2);
            }

            // Extract kids
            Solution c1 = children[0];
            Solution c2 = children[1];

            // Mutate
            c1 = p1_memes.applyOperators(c1);
            c2 = p2_memes.applyOperators(c2);

            // Add to next pop
            nextPop.add(c1);
            nextPop.add(c2);

            // Generate memeplexes
            Memeplex c1_memes = memeplexInheritenceFunction.apply(p1_memes, p2_memes);
            Memeplex c2_memes = memeplexInheritenceFunction.apply(p1_memes, p2_memes);

            // Mutate memeplexes
            c1_memes = memeplexMutationOperator.apply(c1_memes);
            c2_memes = memeplexMutationOperator.apply(c2_memes);

            // Add to memeplex map
            memePlexes.put(c1, c1_memes);
            memePlexes.put(c2, c2_memes);
          }

          ArrayList<Solution> ret = replacementStrategy.apply(currentPopulation, nextPop);

          return ret;
        };
  }

  public void printBestMeme() {
    Memeplex memeplex = memePlexes.get(this.getBestSolution());
    System.out.println(memeplex);
  }
}
