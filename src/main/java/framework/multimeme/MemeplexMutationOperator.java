package framework.multimeme;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface MemeplexMutationOperator extends UnaryOperator<Memeplex> {

  @Override
  Memeplex apply(Memeplex memeplex);
}
