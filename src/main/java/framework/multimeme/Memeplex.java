package framework.multimeme;

import framework.general.Solution;
import framework.general.SolutionOperator;
import framework.genetic.CrossoverOperator;
import framework.utils.Methods;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stores memes and applies them to solution
 * */
public class Memeplex {

  private final Random rnd = new Random();
  private ArrayList<CrossoverOperator> crossoverOperators;
  private CrossoverOperator currentCrossoverOperator;
  private double crossoverOperatorDelta;
  private ArrayList<SolutionOperator> mutationOperators;
  private SolutionOperator currentMutationOperator;
  private double mutationOperatorDelta;
  private ArrayList<SolutionOperator> rnrOperators;
  private SolutionOperator currentRnrOperator;
  private double rnrOperatorDelta;
  private ArrayList<SolutionOperator> hillclimbOperators;
  private SolutionOperator currentHillclimbOperator;
  private double hillclimbOperatorDelta;
  private boolean mutateThanClimb;
  private double mutateThanClimbDelta;

  public Memeplex(
      ArrayList<CrossoverOperator> crossoverOperators,
      ArrayList<SolutionOperator> mutationOperators,
      ArrayList<SolutionOperator> rnrOperators,
      ArrayList<SolutionOperator> hillclimbOperators) {
    this.crossoverOperators = crossoverOperators;
    this.mutationOperators = mutationOperators;
    this.hillclimbOperators = hillclimbOperators;
    this.rnrOperators = rnrOperators;
  }

  public Memeplex(Memeplex toCopy) {
    this.crossoverOperators = toCopy.getCrossoverOperators();
    this.mutationOperators = toCopy.getMutationOperators();
    this.hillclimbOperators = toCopy.getHillclimbOperators();
    this.rnrOperators = toCopy.getRnrOperators();
  }

  public void init() {
    currentCrossoverOperator = Methods.getRandomChoice(crossoverOperators);
    currentMutationOperator = Methods.getRandomChoice(mutationOperators);
    currentHillclimbOperator = Methods.getRandomChoice(hillclimbOperators);
    currentRnrOperator = Methods.getRandomChoice(rnrOperators);
    mutateThanClimb = rnd.nextDouble() < 0.5;

    hillclimbOperatorDelta = 0;
    mutationOperatorDelta = 0;
    crossoverOperatorDelta = 0;
    mutateThanClimbDelta = 0;
    rnrOperatorDelta = 0;
  }

  public Solution[] applyCrossoverOperator(Solution p1, Solution p2) {
    double p1_val = p1.getSolutionValue();
    double p2_val = p2.getSolutionValue();

    Solution[] ret = currentCrossoverOperator.apply(p1, p2);

    double c1_val = ret[0].getSolutionValue();
    double c2_val = ret[1].getSolutionValue();

    double p1_c1_delta = c1_val - p1_val;
    double p2_c2_delta = c2_val - p2_val;

    crossoverOperatorDelta = Math.max(p1_c1_delta, p2_c2_delta);

    return ret;
  }



  public Solution applyOperators(Solution sol) {

    Solution ret;

    if (mutateThanClimb) {

      double mut_before = sol.getSolutionValue();
      ret = currentMutationOperator.apply(sol);
      double mut_after = ret.getSolutionValue();
      mutationOperatorDelta = mut_after - mut_before;

      ret = currentRnrOperator.apply(sol);
      double rnr_after = ret.getSolutionValue();
      rnrOperatorDelta = rnr_after - mut_after;

      ret = currentHillclimbOperator.apply(ret);
      double climb_after = ret.getSolutionValue();
      hillclimbOperatorDelta = climb_after - rnr_after;

      mutateThanClimbDelta = mutationOperatorDelta + hillclimbOperatorDelta;

    } else {

      double climb_before = sol.getSolutionValue();
      ret = currentHillclimbOperator.apply(sol);
      double climb_after = ret.getSolutionValue();
      hillclimbOperatorDelta = climb_after - climb_before;

      ret = currentRnrOperator.apply(sol);
      double rnr_after = ret.getSolutionValue();
      rnrOperatorDelta = rnr_after - climb_after;

      ret = currentMutationOperator.apply(ret);
      double mut_after = ret.getSolutionValue();
      mutationOperatorDelta = mut_after - rnr_after;

      mutateThanClimbDelta = mutationOperatorDelta + hillclimbOperatorDelta;
    }

    return ret;
  }

  public ArrayList<CrossoverOperator> getCrossoverOperators() {
    return crossoverOperators;
  }

  public void setCrossoverOperators(ArrayList<CrossoverOperator> crossoverOperators) {
    this.crossoverOperators = crossoverOperators;
  }

  public CrossoverOperator getCurrentCrossoverOperator() {
    return currentCrossoverOperator;
  }

  public void setCurrentCrossoverOperator(CrossoverOperator currentCrossoverOperator) {
    this.currentCrossoverOperator = currentCrossoverOperator;
  }

  public double getCrossoverOperatorDelta() {
    return crossoverOperatorDelta;
  }

  public void setCrossoverOperatorDelta(double crossoverOperatorDelta) {
    this.crossoverOperatorDelta = crossoverOperatorDelta;
  }

  public ArrayList<SolutionOperator> getMutationOperators() {
    return mutationOperators;
  }

  public void setMutationOperators(ArrayList<SolutionOperator> mutationOperators) {
    this.mutationOperators = mutationOperators;
  }

  public SolutionOperator getCurrentMutationOperator() {
    return currentMutationOperator;
  }

  public void setCurrentMutationOperator(SolutionOperator currentMutationOperator) {
    this.currentMutationOperator = currentMutationOperator;
  }

  public double getMutationOperatorDelta() {
    return mutationOperatorDelta;
  }

  public void setMutationOperatorDelta(double mutationOperatorDelta) {
    this.mutationOperatorDelta = mutationOperatorDelta;
  }

  public ArrayList<SolutionOperator> getHillclimbOperators() {
    return hillclimbOperators;
  }

  public void setHillclimbOperators(ArrayList<SolutionOperator> hillclimbOperators) {
    this.hillclimbOperators = hillclimbOperators;
  }

  public SolutionOperator getCurrentHillclimbOperator() {
    return currentHillclimbOperator;
  }

  public void setCurrentHillclimbOperator(SolutionOperator currentHillclimbOperator) {
    this.currentHillclimbOperator = currentHillclimbOperator;
  }

  public double getHillclimbOperatorDelta() {
    return hillclimbOperatorDelta;
  }

  public void setHillclimbOperatorDelta(double hillclimbOperatorDelta) {
    this.hillclimbOperatorDelta = hillclimbOperatorDelta;
  }

  public boolean isMutateThanClimb() {
    return mutateThanClimb;
  }

  public void setMutateThanClimb(boolean mutateThanClimb) {
    this.mutateThanClimb = mutateThanClimb;
  }

  public double getMutateThanClimbDelta() {
    return mutateThanClimbDelta;
  }

  public void setMutateThanClimbDelta(double mutateThanClimbDelta) {
    this.mutateThanClimbDelta = mutateThanClimbDelta;
  }

  @Override
  public String toString() {
    return "Crossover: "
        + this.currentCrossoverOperator.getClass().getSimpleName()
        + "\n"
        + "Mutation: "
        + this.currentMutationOperator.getClass().getSimpleName()
        + "\n"
        + "Hill climbing: "
        + this.currentHillclimbOperator.getClass().getSimpleName()
        + "\n"
        + "MutateThanClimb: "
        + this.mutateThanClimb
        + "\n";
  }

  public ArrayList<SolutionOperator> getRnrOperators() {
    return rnrOperators;
  }

  public void setRnrOperators(ArrayList<SolutionOperator> rnrOperators) {
    this.rnrOperators = rnrOperators;
  }

  public SolutionOperator getCurrentRnrOperator() {
    return currentRnrOperator;
  }

  public void setCurrentRnrOperator(SolutionOperator currentRnrOperator) {
    this.currentRnrOperator = currentRnrOperator;
  }

  public double getRnrOperatorDelta() {
    return rnrOperatorDelta;
  }

  public void setRnrOperatorDelta(double rnrOperatorDelta) {
    this.rnrOperatorDelta = rnrOperatorDelta;
  }
}
