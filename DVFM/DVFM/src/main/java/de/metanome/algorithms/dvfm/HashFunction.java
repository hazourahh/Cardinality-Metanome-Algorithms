package de.metanome.algorithms.dvfm;

public class HashFunction {
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
