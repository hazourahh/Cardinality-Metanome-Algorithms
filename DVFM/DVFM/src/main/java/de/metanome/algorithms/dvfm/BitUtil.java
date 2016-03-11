package de.metanome.algorithms.dvfm;


import java.nio.ByteBuffer;

/**
 * @author Hazar.Harmouch General propose functions to deal with bit operations
 **/
public class BitUtil {

  /**
   * @param data:the bitmap which represented as byte array
   * @param pos: the position of the bit in the bitmap
   * @return the value (0 or 1) for the bit at specified position in the bitmap
   **/
  public static int getBit(byte[] data, int pos) {
    int posByte = pos / 8;
    int posBit = pos % 8;
    byte valByte = data[posByte];
    int valInt = valByte >> (8 - (posBit + 1)) & 0x0001;
    return valInt;
  }


  /**
   * @param data:the bitmap which represented as byte array
   * @param pos: the position of the bit in the bitmap
   * @param val: the new value for the bit
   * @return set the value (0 or 1) for the bit at specified position in the bitmap
   **/
  public static void setBit(byte[] data, int pos, int val) {
    int posByte = pos / 8;
    int posBit = pos % 8;
    byte oldByte = data[posByte];
    oldByte = (byte) (((0xFF7F >> posBit) & oldByte) & 0x00FF);
    byte newByte = (byte) ((val << (8 - (posBit + 1))) | oldByte);
    data[posByte] = newByte;
  }


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
   * @return the unsigned value of integer number
   **/
  public static long getUnsignedInt(int x) {
    return x & 0x00000000ffffffffL;
  }

  /**
   * @return the unsigned value of long number
   **/
  public static long getUnsignedLong(long x) {
    return x & 0xFFFFFFFFL;
  }



  /**
   * @param v: bitmap represented as long
   * @return the position of the least significant 1-bit in the binary representation of bitmap and
   *         rho(O)=0
   */
  public static int rho(long v) {
    int rho = 0;
    for (int i = 0; i < 64; i++) { // size of long=64 bits.
      if ((v & 0x01) == 0) {
        v = v >> 1;
        rho++;
      } else {
        break;
      }
    }
    return rho == 64 ? 0 : rho;
  }


  /**
   * @param data:the bitmap which represented as byte array
   * @return the position of the least significant 0-bit in the binary representation of bitmap
   */
  public static int LSBZero(byte[] data) {
    int sizeinbit = 8 * data.length;
    int i = 0;
    while (i < sizeinbit && getBit(data, i) == 1) {
      i++;
    }
    return i;
  }


  /**
   * @return the binary representation of byte as string
   **/
  public static String byte2Bits(byte b) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < 8; i++)
      buf.append((int) (b >> (8 - (i + 1)) & 0x0001));
    return buf.toString();
  }

  /**
   * @return the binary representation of byte array as string
   **/
  public static String bytes2String(byte[] data) {
    String s = "";
    for (int i = 0; i < data.length; i++) {
      s += byte2Bits(data[i]) + " ";
    }
    return s;
  }

  /**
   * @return convert long to byte array
   **/
  public static byte[] Long2bytearray(long value) {
    return ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
  }

  /**
   * @param sizeinbit : the number of bits
   * @return the number of bytes required to represent a specific number of bits
   **/
  public static int bit2bytesize(int sizeinbit) {
    return (int) Math.ceil(sizeinbit / 8D);
  }

}
