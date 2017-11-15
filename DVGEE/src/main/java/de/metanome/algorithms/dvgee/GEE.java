package de.metanome.algorithms.dvgee;



import java.util.HashMap;


public class GEE {

  HashMap<String,Integer> Frequencymap;
  
  public GEE() {
  
    Frequencymap = new HashMap<String,Integer>();
  }
  
  /**
   *  
   * @param value:  a new value of column <code> i</code> from a sample
   */
  public void UpdateColumnValuesFrequency(String value)
  {if(value!=null && !value.isEmpty())
    {if(Frequencymap.containsKey(value))
        Frequencymap.put(value,  Frequencymap.get(value)+1);
     else
       Frequencymap.put(value,1);  
    }
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
HashMap<Integer,Integer> f_i=new HashMap<Integer,Integer>(r);
  for (HashMap.Entry<String,Integer> entry :Frequencymap.entrySet())
  {
    int current_value= entry.getValue();
    if(f_i.containsKey(current_value))
      f_i.put(current_value, f_i.get(current_value)+1);
    else
      f_i.put(current_value,1);
  }
   
  for(int i=2;i<=r;i++)
    if(f_i.containsKey(i))
       sum_fi+=f_i.get(i);
  System.out.println("done f2-n frequency");
  int f_1=0;
  if(f_i.containsKey(1)) f_1=f_i.get(1);
  return (int) Math.ceil(Math.sqrt(num_tuples/r)*f_1+sum_fi);
 }

}
