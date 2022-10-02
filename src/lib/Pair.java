package lib;

public class Pair<F, S> {
  public F first;
  public S second;

  /**
   * Membuat pair dengan first sebagai elemen pertama dan second sebagai elemen kedua.
   * @param first
   * @param second
    */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

}
