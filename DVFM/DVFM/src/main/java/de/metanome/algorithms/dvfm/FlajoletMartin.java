package de.metanome.algorithms.dvfm;



import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * * Implementation of Probabilistic counting algorithm or FM Sketch.
 * * Reference:
 *   Flajolet, Philippe, and G. Nigel Martin. "Probabilistic counting algorithms
 *   for data base applications." Journal of computer and system sciences 31.2 
 *   (1985): 182-209. 
 * * @author Hazar.Harmouch
 * * source with modification: https://github.com/rbhide0/Columbus 
 * * Modification: 1- actual bit representation of bitmap instead of boolean for each bit.
 *                 2- No bucket is used keep the algo. as the original paper describes it.

 */


public class FlajoletMartin {
  /**
   * correction factor
   */
  private static final double PHI = 0.77351D;
  /**
   * Number of hash functions
   */
  private int numHashFunctions=64;
  /**
   * Size of the map in bits
   */
  private int bitmapSize=32; //L 
  /**
   * The u=generated hash functions
   */
  private HashFunction[] hashes;
  /**
   * Bitmap represents whether we have seen a hash function value whose binary representation ends in 0*i1
   * one for each hash function
   */
  private byte[][] bitmaps;


  public FlajoletMartin(double error) {
      int sizeinbyte=BitUtil.bit2bytesize(bitmapSize);
      // standard error= 1/sqrt(m) => m=(1/error)^2  
      this.numHashFunctions = BitUtil.nextPowerOf2((int)Math.pow(1/error, 2));
      bitmaps = new byte[numHashFunctions][sizeinbyte];
      hashes = new HashFunction[numHashFunctions];
      generateHashFunctions();
  }
 
    
  private void generateHashFunctions() {
      Map<Integer, Collection<Integer>> mnMap = new HashMap<Integer, Collection<Integer>>();
          for (int j=0; j<numHashFunctions; j++) {
              hashes[j] = generateUniqueHashFunction(mnMap);
          }
      
  }

  

  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
            for (int j=0; j<numHashFunctions; j++) {
                HashFunction f = hashes[j];
                //non-negative hash values
                long v = BitUtil.getUnsignedLong(f.hash(o));
                //index := pi(hash(x))
                int index = BitUtil.rho(v);
                //update the corresponding bit in the bitmap
                if (BitUtil.getBit(bitmaps[j],index)==0) {
                  BitUtil.setBit(bitmaps[j],index,1);
                    affected = true;
                }   
        }
    }
        return affected;
    }
  
  

  public long cardinality() {
    double sumR = 0;
        for (int j=0; j<numHashFunctions; j++) {
            sumR += (BitUtil.LSBZero(bitmaps[j]));
        }
      
    return (long) (Math.pow(2, sumR/numHashFunctions) / PHI);
}

  

  private HashFunction generateUniqueHashFunction(Map<Integer, Collection<Integer>> mnMap) {
    // Get odd numbers for both m and n.
    int m = 0;
    do {
        m = (int) (Integer.MAX_VALUE * Math.random());
    } while (m % 2 == 0);

    // Get pairs that we haven't seen before.
    int n = 0;
    do {
        n = (int) (Integer.MAX_VALUE * Math.random());
    } while ((n % 2 == 0) || contains(mnMap, m, n));

    // Make a note of the (m, n) pair, so we don't use it again.
    Collection<Integer> valueCollection = mnMap.get(m);
    if (valueCollection == null) {
        valueCollection = new HashSet<Integer>();
        mnMap.put(m, valueCollection);
    }
    valueCollection.add(n);
    return new HashFunction(m, n, bitmapSize);
}

private static boolean contains(Map<Integer, Collection<Integer>> map, int m, int n) {
    Collection<Integer> valueList = map.get(m);
    return (valueList != null) && (valueList.contains(n));
}
  private static class HashFunction {
      private int m_m;
      private int m_n;
      private int m_bitmapSize;
      private long m_pow2BitmapSize;

      public HashFunction(int m, int n, int bitmapSize) {
          if (bitmapSize > 64) {
              throw new IllegalArgumentException("bitmap size should be at max. 64");
          }
          this.m_m = m;
          this.m_n = n;
          m_bitmapSize = bitmapSize;
          m_pow2BitmapSize = 1 << m_bitmapSize;
      }

      public long hash(Object o) {
          if (o instanceof String)
              return hash(((String) o).hashCode());
          if (o instanceof Number)
              return hash(String.valueOf(o).hashCode());
          return hash(o.hashCode());
      }

      public long hash(long hashCode) {
          return m_m + m_n * hashCode;
      }
  }
}