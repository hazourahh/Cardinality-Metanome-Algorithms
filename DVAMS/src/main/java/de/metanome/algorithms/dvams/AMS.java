package de.metanome.algorithms.dvams;

import java.util.Random;

/**
 * * Implementation of AMS 
 * * Reference:
 
 * * @author Hazar.Harmouch
 */


public class AMS {
   private long hashfunction;
   public static final long PRIME_MODULUS = (1L << 31) - 1;
   private int seed=-1;
   private int width=64; // 32 and 64
   private byte R;


  public AMS(double error) { 
      generateHashFunction();
  }
 
  // We're using a linear hash functions
  // of the form (a*x+b) mod p.  
  private void generateHashFunction() {
    Random r = new Random(seed);
    hashfunction=r.nextInt(Integer.MAX_VALUE);
  }

  int hash(long item) {
    long hash = hashfunction * item;
    // A super fast way of computing x mod 2^p-1
    // See http://www.cs.princeton.edu/courses/archive/fall09/cos521/Handouts/universalclasses.pdf
    // page 149, right after Proposition 7.
    hash += hash >> 32;
    hash &= PRIME_MODULUS;
    // Doing "%" after (int) conversion is ~2x faster than %'ing longs.
    return ((int) hash) % width;
}
  
  
  public int hash(Object o) {
    if (o instanceof String)
        return hash(((String) o).hashCode());
    if (o instanceof Number)
        return hash(String.valueOf(o).hashCode());
    return hash(o.hashCode());
}



  public boolean offer(Object o) {
    boolean affected = false;    
    if(o!=null){
                //non-negative hash values
                int v=MurmurHash.hash(o);
                // calculating the position of th emost significant bit that set to 1 
               byte r = (byte) (Integer.numberOfLeadingZeros(v));
               if(R<r)
               {
                 R=r;
                 affected=true;
                 
               }
               
                }   
      
        return affected;
    }
  
  

  public long cardinality() {    
    return (long) (Math.pow(2, R));
}

  
}