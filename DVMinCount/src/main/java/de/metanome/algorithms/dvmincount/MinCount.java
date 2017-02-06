package de.metanome.algorithms.dvmincount;

import java.util.Arrays;



/** Implementation of SuperLogLog
 ** Reference:
 *   Durand, M., & Flajolet, P. (2003). Loglog counting of large cardinalities. In Algorithms-ESA 2003 (pp. 605-617). Springer Berlin Heidelberg.
 * * @author Hazar.Harmouch
 */
public class MinCount {
 
  /**
   * the number of buckets (m) {m=2^Range[0, 31]}
   */
  private int numofbucket=16;
  /**
   * the number of buckets (m0)=floor(m*0.7) according to the truncation rule
   */
  private int truncatednumofbucket=11;
  /**
   * the number of bits used to determine the bucket (k)=log2(m) {k=Range[0, 31]}
   */
  private int Numbits=4;
  
  /**
   * The maximum bit set
   */
  private byte[] M;
  
  /**
   * sum of maxs
   */
  private int Rsum = 0;
  
  /**The maximum cardinality*/
  private double Nmax=Math.pow(10, 19);
  
  /**
   * Restriction Rule**/
  private double B;
  
  /**
   * Correction function
   */
  protected static final double[] mAlpha = {
      0,
      0.44567926005415,
      1.2480639342271,
      2.8391255240079,
      6.0165231584809,
      12.369319965552,
      25.073991603111,
      50.482891762408,
      101.30047482584,
      202.93553338100,
      406.20559696699,
      812.74569744189,
      1625.8258850594,
      3251.9862536323,
      6504.3069874480,
      13008.948453415,
      26018.231384516,
      52036.797246302,
      104073.92896967,
      208148.19241629,
      416296.71930949,
      832593.77309585,
      1665187.8806686,
      3330376.0958140,
      6660752.5261049,
      13321505.386687,
      26643011.107850,
      53286022.550177,
      106572045.43483,
      213144091.20414,
      426288182.74275,
      852576365.81999
};

    private double Ca;
  /**
   * The generated hash functions
   */
  private MurmurHash3 HashFunction;



  public MinCount(double error) {  
       this.numofbucket =BitUtil.roundPowerOf2(Math.pow(1.05/error, 2));
       this.Numbits=(int) (Math.log(numofbucket)/Math.log(2));
       HashFunction=MurmurHash3.getInstance();
       this.M = new byte[numofbucket];
       //truncation rule parameters
       this.truncatednumofbucket=(int)Math.floor(0.7*numofbucket);
       this.Ca = mAlpha[Numbits]/numofbucket;
       //restriction Rule parameters
       B=Math.ceil(Math.log(Nmax/numofbucket)/Math.log(2)+3);
  }

  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
               //hash the data value to get unsigned value
                long v=HashFunction.hash64(o);
                // get the first k bit to determine the bucket 
             // get the first k bit to determine the bucket 
                int j =(int)(v >>> (Long.SIZE - Numbits));
                // calculating rho(bk+1,bk+2 ....)
                byte r = (byte) (Long.numberOfLeadingZeros((v << Numbits) | (1 << (Numbits - 1))) + 1);
                // get the max rho
               if (M[j] < r) {
                    M[j] = r;
                    affected = true;
                }           
    }

        return affected;
    }

  public long cardinality() {
  
  //take into account just the smallest m0 value and discard the rest
    Arrays.sort(M);
    for (int j = 0; j < truncatednumofbucket; j++)
      //use register values that are in the interval [0...ceil(log2(nmax/m)+3)]
       if(M[j]>0 && M[j]<B)
        Rsum+=M[j];// the trancated sum
    double Ravg = Rsum / (double) truncatednumofbucket;
    return (long) Math.floor(Ca*truncatednumofbucket* Math.pow(2, Ravg));
}
  }