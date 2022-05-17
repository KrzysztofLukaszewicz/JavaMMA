package framework.multimeme;

import java.util.function.BinaryOperator;

public interface MemeplexInheritenceFunction extends BinaryOperator<Memeplex> {

  @Override
  Memeplex apply(Memeplex memeplex, Memeplex memeplex2);
}
