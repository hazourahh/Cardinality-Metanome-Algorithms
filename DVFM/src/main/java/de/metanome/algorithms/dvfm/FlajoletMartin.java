package de.metanome.algorithms.dvfm;



import java.util.BitSet;
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
 * * @author Hazar.Harmouch.
 */


public class FlajoletMartin {
  /**
   * correction factor
   */
  private static final double PHI = 0.77351D;
  /**
   * Number of hash functions
   */
  private int numHashFunctions=64;//m depends on the error
  /**
   * Size of the map in bits
   */
  private int bitmapSize=64; //L=64 this is the max for the hash functions 
  /**
   * The u=generated hash functions
   */
  private HashFunction[] hashes;
  /**
   * Each Bitmap represents whether we have seen a hash function value whose binary representation ends in 0*i1
   * one for each hash function
   */
  private BitSet[] bitmaps;


  public FlajoletMartin(double error) {
      // standard error= 1/sqrt(m) => m=(1/error)^2  
      this.numHashFunctions = BitUtil.nextPowerOf2((int)Math.pow(1/error, 2));
      bitmaps = new BitSet[numHashFunctions];
      for(int i=0;i<numHashFunctions;i++)
        bitmaps[i]=new BitSet(bitmapSize);
      hashes = new HashFunction[numHashFunctions];
      generateHashFunctions();
  }
 
    


  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
            for (int j=0; j<numHashFunctions; j++) {
                HashFunction f = hashes[j];
                //non-negative hash values
                long v = f.hash(o);
                //index := pi(hash(x))
                int index = rho(v);
                //update the corresponding bit in the bitmap
                if (bitmaps[j].get(index)==false) {
                  bitmaps[j].set(index,true);
                    affected = true;
                }   
        }
    }
        return affected;
    }
  
  

  public long cardinality() {
    double sumR = 0;
        for (int j=0; j<numHashFunctions; j++) {
            sumR += (BitUtil.LSBzero(bitmaps[j]));
        }
      
    return (long) (Math.pow(2, sumR/numHashFunctions) / PHI);
}

  /**
   * @param v: bitmap represented as long
   * @return the position of the least significant 1-bit in the binary representation of bitmap and
   *         rho(O)=0
   */
  private int rho(long v) {
    int rho = 0;
    for (int i=0; i<bitmapSize; i++) { // size of long=64 bits.
            if ((v & 0x01) == 0) {
                    v = v >> 1;
            rho++;
            } else {
                    break;
            }
    }
    return rho == bitmapSize ? 0 : rho;
}

  
  //-------------------------------------Hash Function Generation---------------------------------------------
  private void generateHashFunctions() {
    Map<Integer, Collection<Integer>> mnMap = new HashMap<Integer, Collection<Integer>>();
        for (int j=0; j<numHashFunctions; j++) {
            hashes[j] = generateUniqueHashFunction(mnMap);
        }
    
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
}