package implementations.crossover;

import framework.general.Solution;
import framework.genetic.CrossoverOperator;
import framework.factories.CrossoverOperatorFactory;

import java.util.Random;

public class OnePointCrossover implements CrossoverOperatorFactory<Double> {

  final Random rnd = new Random();

  /**
   * @param prob - Crossover probability
   * @return
   */
  @Override
  public CrossoverOperator apply(Double prob) {
    return (p1, p2) -> {
      if (Math.random() < prob) {

        int split = rnd.nextInt(p1.size() - 2) + 1;
        int size = p1.size();

        Solution p1_head = p1.subList(0, split);
        Solution p1_tail = p1.subList(split, size);

        Solution p2_head = p2.subList(0, split);
        Solution p2_tail = p2.subList(split, size);

        p1_head.addAll(p2_tail);
        p2_head.addAll(p1_tail);

        return new Solution[] {p1_head, p2_head};
      } else {
        return new Solution[] {p1, p2};
      }
    };
  }
}
