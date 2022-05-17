package framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Methods {

  static Random rnd = new Random();

  public static <T> T getRandomChoice(List<T> ts) {
    int pick = rnd.nextInt(ts.size());
    return ts.get(pick);
  }

  public static <T, K> T getInstance(Class<T> clazz, Class<K>... params)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
          IllegalAccessException {
    return clazz.getDeclaredConstructor(params).newInstance();
  }

  public static double iomDosMap(double val) {
    if (val < 0.2 && 0.0 <= val) {
      return 1;
    } else if (val < 0.4 && 0.2 <= val) {
      return 2;
    } else if (val < 0.6 && 0.4 <= val) {
      return 3;
    } else if (val < 0.8 && 0.6 <= val) {
      return 4;
    } else if (val < 1.0 && 0.8 <= val) {
      return 5;
    } else if (val == 1.0) {
      return 6;
    }
    return 0;
  }

  public static Integer[] arrayShuffle(Integer[] array) {
    List<Integer> lst = Arrays.asList(array);
    Collections.shuffle(lst);
    Integer[] ret = lst.toArray(Integer[]::new);
    return ret;
  }
}
