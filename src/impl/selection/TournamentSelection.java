package impl.selection;

import framework.general.Solution;
import framework.genetic.ParentSelectionFunction;
import framework.factories.ParentSelectionFunctionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TournamentSelection implements ParentSelectionFunctionFactory<Integer> {

  final Random rnd = new Random();

  @Override
  public ParentSelectionFunction apply(Integer tourSize) {

    return (population) -> {
      ArrayList<Solution> tour = new ArrayList<>();

      for (int i = 0; i < tourSize; i++) {
        tour.add(population.get(rnd.nextInt(population.size())));
      }
      Collections.sort(tour, Collections.reverseOrder());

      return tour.get(0);
    };
  }
}
