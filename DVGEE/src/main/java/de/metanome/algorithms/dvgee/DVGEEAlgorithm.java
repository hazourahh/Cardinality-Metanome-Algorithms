package de.metanome.algorithms.dvgee;






import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;
import de.metanome.algorithm_integration.results.BasicStatistic;
import de.metanome.algorithm_integration.results.basic_statistic_values.BasicStatisticValueLong;


/**Use Hash Table to calculate the exact distinct value number
 *  @author Hazar.Harmouch**/


/**
 * @author Hazar.Harmouch
 *
 */
/**
 * @author Hazar.Harmouch
 *
 */
public class DVGEEAlgorithm {

  protected RelationalInputGenerator inputGenerator = null;
  protected BasicStatisticsResultReceiver resultReceiver = null;
  protected final String NUMBEROFDISTINCT = "Number of Distinct Values";
  protected String relationName;
  protected List<String> columnNames;
  protected RelationalInput input;
  protected double sampling_percentage=0.05; //5%
  protected int r; //the number of tuples to be sampled
  private int NUMOFTUPLES=0;
  public void execute() throws AlgorithmExecutionException {

    ////////////////////////////////////////////
    // THE DISCOVERY ALGORITHM LIVES HERE :-) //
    ////////////////////////////////////////////
    // initialisation
    input = this.inputGenerator.generateNewCopy();
    this.relationName = input.relationName();
    this.columnNames = input.columnNames();
    ArrayList<GEE> Columns = new ArrayList<GEE>();
    for (int i = 0; i < columnNames.size(); i++)
      Columns.add(new GEE());

  
   //number of tuples
    while (input.hasNext()) {
      input.next();
      NUMOFTUPLES++;
    }
    
   //find k: the number of samples
   r=(int) Math.ceil(NUMOFTUPLES*sampling_percentage);
   
   
   //sampling
   input = this.inputGenerator.generateNewCopy();
   Iterator<IntermediateSampleData<List<String>>> samples=ReservoirSamplingWithoutReplacement(input,r);
   System.out.println("done sapling");
   //update frequency
   while (samples.hasNext()) 
   { List<String> CurrentTuple=samples.next().getElement();
     for (int i = 0; i < columnNames.size(); i++)
   {String currentvalue=CurrentTuple.get(i);

   Columns.get(i).UpdateColumnValuesFrequency(currentvalue);
   }
   
   
   }
   System.out.println("done update frequency");
//   //generate k random index.
//   IntArrayList indexes=new IntArrayList();
//   MersenneTwisterFast rn = new MersenneTwisterFast();
//   for(int j=0;j<r;j++)
//   { int index=rn.nextInt(NUMOFTUPLES);
//   while(indexes.contains(index))
//     index=rn.nextInt(NUMOFTUPLES);
//   indexes.add(rn.nextInt(NUMOFTUPLES));
//   }
//   //pass over the data
//   int tuples_index=0;
//   input = this.inputGenerator.generateNewCopy();
//    while (input.hasNext()) {
//      List<String> CurrentTuple=input.next();
//      if(indexes.contains(tuples_index)){
//      // pass for each column and update the frequency map
//      for (int i = 0; i < columnNames.size(); i++)
//      {String currentvalue=CurrentTuple.get(i);
//        if(currentvalue!=null && !currentvalue.trim().isEmpty())
//            UpdateColumnValuesFrequency(i, currentvalue);
//      }
//      }
//      tuples_index++;
//      }
    
    // add the statistic for columns
    for (int i = 0; i < columnNames.size(); i++)
    addStatistic(NUMBEROFDISTINCT,Columns.get(i).getColumnCardinalityEstimation(r,NUMOFTUPLES) , columnNames.get(i), relationName);   
  }



  private void addStatistic(String StatisticName, long Value, String ColumnName,
      String RelationName) throws AlgorithmExecutionException {
    BasicStatistic result = new BasicStatistic(new ColumnIdentifier(RelationName, ColumnName));
    result.addStatistic(StatisticName, new BasicStatisticValueLong(Value));
  System.out.println(StatisticName + " of " + ColumnName + " : " + Value);
    resultReceiver.receiveResult(result);
  }
  
 
  private Iterator<IntermediateSampleData<List<String>>> ReservoirSamplingWithoutReplacement(RelationalInput data,int numSamples) throws InputIterationException
  {
    Random random= new Random();
 // This queue holds fixed number elements with the top K weight for current partition.
    PriorityQueue<IntermediateSampleData<List<String>>> queue = new PriorityQueue<IntermediateSampleData<List<String>>>(numSamples);
    int index = 0;
    IntermediateSampleData<List<String>> smallest = null;
    while (input.hasNext()) {
      List<String> element = input.next();
        if (index < numSamples) {
            // Fill the queue with first K elements from input.
            queue.add(new IntermediateSampleData<List<String>>(random.nextDouble(), element));
            smallest = queue.peek();
        } else {
            double rand = random.nextDouble();
            // Remove the element with the smallest weight, and append current element into the queue.
            if (rand > smallest.getWeight()) {
                queue.remove();
                queue.add(new IntermediateSampleData<List<String>>(rand, element));
                smallest = queue.peek();
            }
        }
        index++;
    }
    return queue.iterator(); 
    
    
    
    
    
    
  }
}