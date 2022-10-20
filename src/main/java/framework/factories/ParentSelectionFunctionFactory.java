package framework.factories;

import framework.genetic.ParentSelectionFunction;

import java.util.function.Function;

@FunctionalInterface
public interface ParentSelectionFunctionFactory<T> extends Function<T, ParentSelectionFunction> {

  @Override
  ParentSelectionFunction apply(T obj);
}
