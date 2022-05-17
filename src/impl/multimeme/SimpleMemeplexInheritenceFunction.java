package impl.multimeme;

import framework.multimeme.Memeplex;
import framework.multimeme.MemeplexInheritenceFunction;
import framework.factories.MemeplexInheritenceFunctionFactory;

public class SimpleMemeplexInheritenceFunction implements MemeplexInheritenceFunctionFactory {

  @Override
  public MemeplexInheritenceFunction apply(Object obj) {
    return (memeplex, memeplex2) -> {
      Memeplex ret = new Memeplex(memeplex);

      if (memeplex.getCrossoverOperatorDelta() > memeplex2.getCrossoverOperatorDelta()) {
        ret.setCurrentCrossoverOperator(memeplex.getCurrentCrossoverOperator());
      } else {
        ret.setCurrentCrossoverOperator(memeplex2.getCurrentCrossoverOperator());
      }

      if (memeplex.getMutationOperatorDelta() > memeplex2.getMutationOperatorDelta()) {
        ret.setCurrentMutationOperator(memeplex.getCurrentMutationOperator());
      } else {
        ret.setCurrentMutationOperator(memeplex2.getCurrentMutationOperator());
      }

      if (memeplex.getHillclimbOperatorDelta() > memeplex2.getHillclimbOperatorDelta()) {
        ret.setCurrentHillclimbOperator(memeplex.getCurrentHillclimbOperator());
      } else {
        ret.setCurrentHillclimbOperator(memeplex2.getCurrentHillclimbOperator());
      }

      if (memeplex.getRnrOperatorDelta() > memeplex2.getRnrOperatorDelta()) {
        ret.setCurrentRnrOperator(memeplex.getCurrentRnrOperator());
      } else {
        ret.setCurrentRnrOperator(memeplex2.getCurrentRnrOperator());
      }

      if (memeplex.getMutateThanClimbDelta() > memeplex2.getMutateThanClimbDelta()) {
        ret.setMutateThanClimb(memeplex.isMutateThanClimb());
      } else {
        ret.setMutateThanClimb(memeplex2.isMutateThanClimb());
      }

      return ret;
    };
  }
}
