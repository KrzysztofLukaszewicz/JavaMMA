package framework.factories;

import framework.multimeme.MemeplexInheritenceFunction;

import java.util.function.Function;

@FunctionalInterface
public interface MemeplexInheritenceFunctionFactory<T>
    extends Function<T, MemeplexInheritenceFunction> {

  @Override
  MemeplexInheritenceFunction apply(T obj);
}
