package implementations.crossover;

import framework.general.Solution;
import framework.genetic.CrossoverOperator;
import framework.factories.CrossoverOperatorFactory;

import java.util.Random;

public class UniformCrossover implements CrossoverOperatorFactory<Double> {

  final Random rnd = new Random();

  /**
   * @param prob - Crossover probability
   * @return
   */
  @Override
  public CrossoverOperator apply(Double prob) {
    return (p1, p2) -> {
      if (Math.random() < prob) {
        int size = p1.size();

        for (int i = 0; i < size; i++) {
          if (Math.random() < 0.5) {
            p2.set(i, p1.get(i));
          }
        }

        return new Solution[] {p1, p2};
      } else {
        return new Solution[] {p1, p2};
      }
    };
  }
}
