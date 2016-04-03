package de.metanome.algorithms.dvhyperloglogplus;



import java.nio.ByteBuffer;

public class BitUtil {

  public static int getBit(byte[] data, int pos) {
    int posByte = pos/8; 
    int posBit = pos%8;
    byte valByte = data[posByte];
    int valInt = valByte>>(8-(posBit+1)) & 0x0001;
    return valInt;
 }
  
 public static void setBit(byte[] data, int pos, int val) {
    int posByte = pos/8; 
    int posBit = pos%8;
    byte oldByte = data[posByte];
    oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
    byte newByte = (byte) ((val<<(8-(posBit+1))) | oldByte);
    data[posByte] = newByte;
 }
 
 
  
  public static int nextPowerOf2(final int a) {
    int b = 1;
    while (b < a) {
      b = b << 1;
    }
    return b;
  }

  public static int roundPowerOf2(final double a) {
    int b = 1;
    while (b < a) {
      b = b << 1;
    }
    int b1=(int) (Math.log(b)/Math.log(2));
    return (int) Math.pow(2, b1-1);
  }


  
  public static long getUnsignedInt(int x) {
    return x & 0x00000000ffffffffL;
  }
  
  public static long getUnsignedLong(long x) {
    return x & 0xFFFFFFFFL;
  }
  
  
  
   /**position of the least significant 1-bit in the binary representation of y, with a suitable convention for rho(O)=0
   * rho(y) =min bit( y, k) != 0 if y>O
   *=L if y = 0.
   * 
   * */
public static int rho(long v)  
{
  int rho = 0;
  for (int i=0; i<64; i++) { // size of long=64 bits.
      if ((v & 0x01) == 0) {
          v = v >> 1;
          rho++;
      } else {
          break;
      }
  }
  return rho == 64 ? 0 : rho;
}


public static int LSBZero(byte[] data) {
  int sizeinbit = 8 * data.length;
  int i = 0;
  while (i < sizeinbit && getBit(data, i) == 1) {
    i++;
  }
  return i;
}



public static String byte2Bits(byte b) {
  StringBuffer buf = new StringBuffer();
  for (int i=0; i<8; i++)
     buf.append((int)(b>>(8-(i+1)) & 0x0001));
  return buf.toString();
}

public static String bytes2String(byte[] data) {
  String s="";
  for (int i=0; i<data.length; i++) {
    s+=byte2Bits(data[i])+" ";
  }
 return s;
}

public static byte[] Long2bytearray(long value) {
  return ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
}

public static int bit2bytesize(int sizeinbit) {
  return (int) Math.ceil(sizeinbit / 8D);
}

}