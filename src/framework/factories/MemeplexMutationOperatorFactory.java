package framework.factories;

import framework.multimeme.MemeplexMutationOperator;

import java.util.function.Function;

@FunctionalInterface
public interface MemeplexMutationOperatorFactory<T extends Number>
    extends Function<T, MemeplexMutationOperator> {

  @Override
  MemeplexMutationOperator apply(T obj);
}
