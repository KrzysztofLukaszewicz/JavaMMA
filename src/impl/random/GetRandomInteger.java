package impl.random;

import framework.utils.GetRandom;
import framework.factories.GetRandomFactory;

public class GetRandomInteger implements GetRandomFactory<Integer> {

  @Override
  public GetRandom apply(Integer obj) {
    return (GetRandom<Integer>) rnd -> rnd.nextInt(obj);
  }
}
