package framework.utils;

import java.util.Random;
import java.util.function.Function;

@FunctionalInterface
public interface GetRandom<T extends Number> extends Function<Random, T> {

  @Override
  T apply(Random rnd);
}
