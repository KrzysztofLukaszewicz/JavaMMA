package implementations.crossover;

import framework.general.Solution;
import framework.genetic.CrossoverOperator;
import framework.factories.CrossoverOperatorFactory;

import java.util.Random;

public class TwoPointCrossover implements CrossoverOperatorFactory<Double> {

  final Random rnd = new Random();

  /**
   * @param prob - Crossover probability
   * @return
   */
  @Override
  public CrossoverOperator apply(Double prob) {
    return (p1, p2) -> {
      if (Math.random() < prob) {

        int split1 = rnd.nextInt(p1.size() - 2) + 1;
        int split2 = rnd.nextInt(p1.size() - 2) + 1;
        while (split1 == split2) split2 = rnd.nextInt(p1.size() - 2) + 1;

        int size = p1.size();

        if (split1 < split2) {
          Solution p1_head = p1.subList(0, split1);
          Solution p1_mid = p1.subList(split1, split2);
          Solution p1_tail = p1.subList(split2, size);

          Solution p2_head = p2.subList(0, split1);
          Solution p2_mid = p2.subList(split1, split2);
          Solution p2_tail = p2.subList(split2, size);

          p1_head.addAll(p2_mid);
          p1_head.addAll(p1_tail);

          p2_head.addAll(p1_mid);
          p2_head.addAll(p2_tail);

          return new Solution[] {p1_head, p2_head};

        } else {

          Solution p1_head = p1.subList(0, split2);
          Solution p1_mid = p1.subList(split2, split1);
          Solution p1_tail = p1.subList(split1, size);

          Solution p2_head = p2.subList(0, split2);
          Solution p2_mid = p2.subList(split2, split1);
          Solution p2_tail = p2.subList(split1, size);

          p1_head.addAll(p2_mid);
          p1_head.addAll(p1_tail);

          p2_head.addAll(p1_mid);
          p2_head.addAll(p2_tail);

          return new Solution[] {p1_head, p2_head};
        }

      } else {
        return new Solution[] {p1, p2};
      }
    };
  }
}
