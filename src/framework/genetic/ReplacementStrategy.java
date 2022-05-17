package framework.genetic;

import framework.general.Solution;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

@FunctionalInterface
public interface ReplacementStrategy extends BinaryOperator<ArrayList<Solution>> {

  @Override
  ArrayList<Solution> apply(ArrayList<Solution> currentPop, ArrayList<Solution> nextPop);
}
