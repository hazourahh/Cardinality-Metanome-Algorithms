package de.metanome.algorithms.dvpcsa;

import java.util.BitSet;



/**
 * * Implementation of Probabilistic Counting with Stochastic Averaging algorithm.
 * * Reference:
 *   Flajolet, Philippe, and G. Nigel Martin. "Probabilistic counting algorithms
 *   for data base applications." Journal of computer and system sciences 31.2 
 *   (1985): 182-209. 
 * * @author Hazar.Harmouch
 */
public class PCSA {
  /**
   * correction factor
   */
  private static final double PHI = 0.77351D;
  /**
   * Number of vectors
   */
  private int numvectors=64;
  /**
   * Size of the map in bits
   */
  private int bitmapSize=64;// we use 64 bit hash function
  
  /**
   * The generated hash functions
   */
  private MurmurHash3 HashFunction;
  /**
   * Each Bitmap represents whether we have seen a hash function value whose binary representation ends in 0*i1
   * one for each hash function
   */
  private BitSet[] bitmaps;
  
  public PCSA(double error) {  
      // standard error= 1/sqrt(m) => m=(0.78/error)^2  
       this.numvectors =BitUtil.nextPowerOf2((int)Math.pow(0.78/error, 2));
       HashFunction=MurmurHash3.getInstance();
       bitmaps = new BitSet[numvectors];
       for(int i=0;i<numvectors;i++)
         bitmaps[i]=new BitSet(bitmapSize);
  }

  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
               //hash the data value to get unsigned value
                long v=HashFunction.hash64(o);
                v=v& 0xFFFFFFFFL;
                // get the first k bit to determine the bucket 
                int j =(int)(v%numvectors);
                // calculating rho(bk+1,bk+2 ....)
                byte r = (byte) (Long.numberOfTrailingZeros(v/numvectors));
                  if(bitmaps[j].get(r)==false)
                  { bitmaps[j].set(r,true);
                    affected  = true;
                  }
                
              }

        return affected;
    }

  public long cardinality() {
    double sumR = 0;
        for (int j=0; j<numvectors; j++) {
          int R=BitUtil.LSBzero(bitmaps[j]);
          sumR += R;
        }
    return (long) Math.floor(numvectors / PHI * Math.pow(2, sumR/numvectors));
}
  
  /**
   * @param v: bitmap represented as long
   * @return the position of the least significant 1-bit in the binary representation of bitmap and
   *         rho(O)=0
   */
  private int rho(long v) {
    int rho = 0;
    rho=Long.numberOfTrailingZeros(v);
    return rho == 64 ? 0 : rho;
  }  
  
}
