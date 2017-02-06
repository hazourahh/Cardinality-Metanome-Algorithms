package de.metanome.algorithms.dvfm;



import java.util.BitSet;

/**
 * @author Hazar.Harmouch General propose functions to deal with bit operations
 **/
public class BitUtil {
  /**
   * @return the next power of 2 larger than the input number.
   **/
  public static int nextPowerOf2(final int intnum) {
    int b = 1;
    while (b < intnum) {
      b = b << 1;
    }
    return b;
  }

  /**
   * @return the position of the least significant bit that is set to 0.
   **/
  public static int LSBzero(BitSet bitmap)
  {
    int index=0;
    for(int i=0;i<bitmap.size();i++)
      if(bitmap.get(i)==false)
      {index=i;
        break;
      }
    return index;
    
  }
  /**
   * @return the position of the most significant bit that is set to 1.
   **/ 
  public static int MSBone(BitSet bitmap)
  { int index=0;
  for(int i=bitmap.size()-1;i>=0;i--)
    if(bitmap.get(i)==true)
    {index=i;
      break;
    }
  return index;}
}
