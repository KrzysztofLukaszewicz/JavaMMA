package implementations.replacement;

import framework.general.Solution;
import framework.genetic.ReplacementStrategy;
import framework.factories.ReplacementStrategyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class TransGenerationalElitism implements ReplacementStrategyFactory<Double> {

  /**
   * @param gap Generational gap from 1/N to 1.0
   * @return
   */
  @Override
  public ReplacementStrategy apply(Double gap) {
    return (currentPop, nextPop) -> {
      int count = (int) (currentPop.size() * gap) - 1;
      int popCount = currentPop.size();

      ArrayList<Solution> combined = new ArrayList<>();
      ArrayList<Solution> res = new ArrayList<>();

      for (Solution sol : currentPop) {
        combined.add(sol);
      }

      for (int i = 0; i < count; i++) {
        combined.add(nextPop.get(i));
      }

      combined =
          (ArrayList<Solution>)
              combined.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

      for (int i = 0; i < popCount; i++) {
        res.add(combined.get(i));
      }

      return res;
    };
  }
}
