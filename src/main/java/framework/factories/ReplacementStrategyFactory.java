package framework.factories;

import framework.genetic.ReplacementStrategy;

import java.util.function.Function;

@FunctionalInterface
public interface ReplacementStrategyFactory<T> extends Function<T, ReplacementStrategy> {

  @Override
  ReplacementStrategy apply(T obj);
}
