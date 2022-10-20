package framework.genetic;

import framework.general.Solution;

import java.util.ArrayList;
import java.util.function.Function;

@FunctionalInterface
public interface ParentSelectionFunction extends Function<ArrayList<Solution>, Solution> {

  @Override
  Solution apply(ArrayList<Solution> solutions);
}
