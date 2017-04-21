package de.metanome.algorithms.dvams;



/**
 * * Implementation of AMS 
 * *  Reference:
 *    Alon, N., Matias, Y., & Szegedy, M. (1996, July). The space complexity of approximating the frequency moments. 
 *    In Proceedings of the twenty-eighth annual ACM symposium on Theory of computing (pp. 20-29). ACM.
 * * @author Hazar.Harmouch
 */


public class AMS {
  private int R;


  public AMS() {
    R = 0;
   
  }


  public boolean offer(Object o) {
    boolean affected = false;
    if (o != null) {
      // non-negative hash values
      long v = MurmurHash.hash64(o);
      // calculating the position of the most significant bit that set to 1 in the final bitmap but
      // while we do not save bitmap so we save the max trailing zero number over all the traffic
      int r = rho(v);
      if (R < r) {
        R = r;
        affected = true;
      }
    }

    return affected;
  }



  public long cardinality() {
    return (long) (Math.pow(2, R));
  }

  /**
   * @return the position of the least significant 1-bit in the binary representation of y
   *         rho(O)=0
   */
  private int rho(long y) {
    return Long.numberOfTrailingZeros(y);
}

}
