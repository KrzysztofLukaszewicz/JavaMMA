package framework.general;

import framework.utils.GetRandom;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.UnaryOperator;

import static framework.utils.Methods.getRandomChoice;

/**
 * This is the main.java.framework.base implementation of all algorithms (Genetic, memetic, multimeme)
 * It holds all commonly shared information
 * @param <T> Type of object to use inside solutions
 * */
public abstract class SolutionMemory<T extends Number> implements Runnable {

  private final ArrayList<Double> bestPerGeneration = new ArrayList<Double>();
  private final ArrayList<Double> worstPerGeneration = new ArrayList<Double>();
  private final int popSize;
  private final int generationCount;
  private final int geneCount;
  private final boolean useHistory;
  private final Random rand;
  protected ArrayList<Solution> currentPopulation;
  protected NextGeneration nextGeneration;
  private ArrayList<ArrayList<Solution>> populationHistory;
  private Solution bestSolution = null;
  private double bestSolutionValue = 0;
  private EvaluationFunction evaluationFunction;
  private ArrayList<T> bounds;
  private GetRandom<T> getRandom;

  /**
   * Unbounded constructor
   * @param evaluationFunction Objective function
   * @param popSize Size of the population
   * @param generationCount How many generations should it run for
   * @param geneCount Amount of genes in each population
   * @param useHistory Should it save values for previous generations
   * @param getRandom How to get random of type T
   */
  public SolutionMemory(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      GetRandom getRandom) {
    this.setEvaluationFunction(evaluationFunction);
    this.popSize = popSize;
    this.generationCount = generationCount;
    this.rand = new Random();
    this.geneCount = geneCount;
    this.useHistory = useHistory;
    this.getRandom = getRandom;
    init();
  }

  /**
   * Constructor with bounded values in solution (0, 1)
   * @param evaluationFunction Objective function
   * @param popSize Size of the population
   * @param generationCount How many generations should it run for
   * @param geneCount Amount of genes in each population
   * @param useHistory Should it save values for previous generations
   * @param bounds Values to use
   */
  public SolutionMemory(
      EvaluationFunction evaluationFunction,
      int popSize,
      int generationCount,
      int geneCount,
      boolean useHistory,
      ArrayList<T> bounds) {
    this.setEvaluationFunction(evaluationFunction);
    this.popSize = popSize;
    this.generationCount = generationCount;
    this.bounds = bounds;
    this.rand = new Random();
    this.geneCount = geneCount;
    this.useHistory = useHistory;

    init();
  }

  /**
   * Runs the thing, uses NextGeneration function
   * */
  @Override
  public void run() {
    compose();

    if (useHistory) {
      populationHistory.add(currentPopulation);
    }

    for (Solution sol : currentPopulation) {
      if (sol.getSolutionValue() > bestSolutionValue) {
        this.setBestSolutionValue(sol.getSolutionValue());
        this.setBestSolution(sol.clone());
      }
    }

    for (int i = 0; i < generationCount + 1; i++) {

      double worst = Double.MAX_VALUE;
      double best = Double.MIN_VALUE;

      for (Solution sol : currentPopulation) {

        if (sol.getSolutionValue() < worst) {
          worst = sol.getSolutionValue();
        }

        if (sol.getSolutionValue() > best) {
          best = sol.getSolutionValue();
        }

        if (sol.getSolutionValue() > bestSolutionValue) {
          this.setBestSolutionValue(sol.getSolutionValue());
          this.setBestSolution(sol.clone());
        }
      }

      worstPerGeneration.add(worst);
      bestPerGeneration.add(best);

      if (useHistory) {
        populationHistory.add(currentPopulation);
      }

      if (i < generationCount) {
        currentPopulation = nextGeneration.apply(currentPopulation);
      }
    }

    System.out.println(bestSolutionValue);
    System.out.println(bestSolution);
  }

  /**
   * Implemented in subclasses
   * Creates the NextGeneration function
   * */
  protected abstract void compose();

  @SuppressWarnings("unchecked")
  private void init() {

    populationHistory = new ArrayList<>();
    currentPopulation = new ArrayList<>();

    for (int i = 0; i < getPopSize(); ++i) {

      Solution<T> solution = new Solution<T>(getEvaluationFunction());

      for (int k = 0; k < getGeneCount(); k++) {
        T num = null;

        if (getBounds() == null) {

          num = getRandom.apply(rand);

        } else {
          num = getRandomChoice(getBounds());
        }
        solution.add(num);
      }

      currentPopulation.add(solution);
    }
  }

  public void printPopulationHistory() {
    int i = 0;
    for (ArrayList<Solution> pop : populationHistory) {
      System.out.println("Generation: " + i);
      for (Solution sol : pop) {
        System.out.print(sol + " - " + sol.getSolutionValue() + "       ");
      }
      System.out.println(" ");
      i++;
    }
  }

  public ArrayList<Solution> getCurrentPopulation() {
    return currentPopulation;
  }

  public void setCurrentPopulation(ArrayList<Solution> currentPopulation) {
    this.currentPopulation = currentPopulation;
  }

  public ArrayList<ArrayList<Solution>> getPopulationHistory() {
    return populationHistory;
  }

  public Solution getBestSolution() {
    return bestSolution;
  }

  public void setBestSolution(Solution bestSolution) {
    this.bestSolution = bestSolution;
  }

  public double getBestSolutionValue() {
    return bestSolutionValue;
  }

  public void setBestSolutionValue(double bestSolutionValue) {
    this.bestSolutionValue = bestSolutionValue;
  }

  public int getPopSize() {
    return popSize;
  }

  public int getGenerationCount() {
    return generationCount;
  }

  public int getGeneCount() {
    return geneCount;
  }

  public EvaluationFunction getEvaluationFunction() {
    return evaluationFunction;
  }

  public void setEvaluationFunction(EvaluationFunction evaluationFunction) {
    this.evaluationFunction = evaluationFunction;
  }

  public ArrayList<T> getBounds() {
    return bounds;
  }

  public ArrayList<Double> getBestPerGeneration() {
    return bestPerGeneration;
  }

  public ArrayList<Double> getWorstPerGeneration() {
    return worstPerGeneration;
  }

  /**
   * Inner interface
   * Used for creating new generations inside run()
   * */
  @FunctionalInterface
  protected interface NextGeneration extends UnaryOperator<ArrayList<Solution>> {

    @Override
    ArrayList<Solution> apply(ArrayList<Solution> pop);
  }
}
