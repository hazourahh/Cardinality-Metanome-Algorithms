package de.metanome.algorithms.dvpcsa;

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
  private int bitmapSize=32;
  
  private byte[][] bitmaps;

  public PCSA(double error) {
       int sizeinbyte=BitUtil.bit2bytesize(bitmapSize);  
      // standard error= 1/sqrt(m) => m=(0.78/error)^2  
       this.numvectors =BitUtil.nextPowerOf2((int)Math.pow(0.78/error, 2));
       bitmaps = new byte[numvectors][sizeinbyte];
  }

  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
               //hash the data value to get unsigned value
                long v=MurmurHash.hash(o);
                //System.out.println(Long.toBinaryString(v));
                int vectorindex =(int)Long.remainderUnsigned(v, numvectors);
                long lowpart=Long.divideUnsigned(v, numvectors);
                int indexinvector=BitUtil.rho(lowpart);
               // System.out.println(BitUtil.mapAsBitString(bitmaps[vectorindex]));
                  if(BitUtil.getBit(bitmaps[vectorindex],indexinvector )==0)
                  BitUtil.setBit(bitmaps[vectorindex],indexinvector,1);
                affected  = true;
                
              }

        return affected;
    }

  public long cardinality() {
    double sumR = 0;
        for (int j=0; j<numvectors; j++) {
         // System.out.println(BitUtil.mapAsBitString(bitmaps[j]));
          int R=BitUtil.LSBZero(bitmaps[j]);
         // System.out.println(R);
          sumR += R;
        }
    return (long) Math.floor(numvectors / PHI * Math.pow(2, sumR/numvectors));
}
  
}
