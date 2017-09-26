package de.metanome.algorithms.dvgee;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class GEE {

  Object2IntOpenHashMap<String> Frequencymap;
  
  public GEE() {
  
    Frequencymap = new Object2IntOpenHashMap<String>();
  }
  
  /**
   *  
   * @param value:  a new value of column <code> i</code> from a sample
   */
  public void UpdateColumnValuesFrequency(String value)
  {
    if(Frequencymap.containsKey(value))
        Frequencymap.put(value,  Frequencymap.getInt(value)+1);
     else
       Frequencymap.put(value,1);  
    
  }
  
  /**
   * 
   * @param r: the number of sampled tuples
   * @param num_tuples: size of the dataset
   * @return cardinality estimation according to the GEE estimator
   */
  public int getColumnCardinalityEstimation(int r,int num_tuples)
  {
  int sum_fi=0;
  for(int i=2;i<=r;i++)
    sum_fi+=getF_i(i);
  return (int) Math.ceil(Math.sqrt(num_tuples/r)*getF_i(1)+sum_fi);
 }

 /**
  * 
  * @return the number of items that exactly occurs <code> frequency</code> time in the column <code> Column</code> 
  */
 private int getF_i(int frequency)
 {int f_i=0;
 for (Object2IntMap.Entry<String> entry:Frequencymap.object2IntEntrySet())
 {int freq=entry.getIntValue();
     if(freq==frequency)
       f_i++;
 }
 return f_i;
 }
}
