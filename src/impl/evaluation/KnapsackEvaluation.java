package impl.evaluation;

import framework.general.EvaluationFunction;
import framework.general.Solution;

import java.util.ArrayList;

public class KnapsackEvaluation implements EvaluationFunction {

  private final ArrayList<Double> weights;
  private final ArrayList<Double> prices;

  private final double limit;

  public KnapsackEvaluation(ArrayList<Double> weights, ArrayList<Double> prices, double limit) {
    this.weights = weights;
    this.prices = prices;
    this.limit = limit;
  }

  @Override
  public double applyAsDouble(Solution data) {
    double result = 0;
    double weight = 0;

    for (int i = 0; i < data.size(); i++) {
      weight += (Integer) data.get(i) * weights.get(i);
      result += (Integer) data.get(i) * prices.get(i);
    }

    if (weight > limit) result = limit - weight;

    return result;
  }
}
