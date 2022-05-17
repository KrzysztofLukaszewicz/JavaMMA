package impl.multimeme;

import framework.multimeme.Memeplex;
import framework.multimeme.MemeplexMutationOperator;
import framework.factories.MemeplexMutationOperatorFactory;
import framework.utils.Methods;

public class SimpleMemeplexMutationOperator implements MemeplexMutationOperatorFactory<Double> {

  @Override
  public MemeplexMutationOperator apply(Double innovationRate) {
    return (memeplex) -> {
      Memeplex ret = new Memeplex(memeplex);

      // Mutate crossover
      if (Math.random() < innovationRate) {
        ret.setCurrentCrossoverOperator(Methods.getRandomChoice(ret.getCrossoverOperators()));
      } else {
        ret.setCurrentCrossoverOperator(memeplex.getCurrentCrossoverOperator());
      }

      // Mutate hillclimb
      if (Math.random() < innovationRate) {
        ret.setCurrentHillclimbOperator(Methods.getRandomChoice(ret.getHillclimbOperators()));
      } else {
        ret.setCurrentHillclimbOperator(memeplex.getCurrentHillclimbOperator());
      }

      // Mutate mutation
      if (Math.random() < innovationRate) {
        ret.setCurrentMutationOperator(Methods.getRandomChoice(ret.getMutationOperators()));
      } else {
        ret.setCurrentMutationOperator(memeplex.getCurrentMutationOperator());
      }

      // Mutate rnr
      if (Math.random() < innovationRate) {
        ret.setCurrentRnrOperator(Methods.getRandomChoice(ret.getRnrOperators()));
      } else {
        ret.setCurrentRnrOperator(memeplex.getCurrentRnrOperator());
      }

      // Mutate order
      if (Math.random() < innovationRate) {
        ret.setMutateThanClimb(!memeplex.isMutateThanClimb());
      } else {
        ret.setMutateThanClimb(memeplex.isMutateThanClimb());
      }

      return (ret);
    };
  }
}
