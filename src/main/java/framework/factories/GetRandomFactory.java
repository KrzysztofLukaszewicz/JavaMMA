package framework.factories;

import framework.utils.GetRandom;

import java.util.function.Function;

@FunctionalInterface
public interface GetRandomFactory<T> extends Function<T, GetRandom> {

  @Override
  GetRandom apply(T obj);
}
