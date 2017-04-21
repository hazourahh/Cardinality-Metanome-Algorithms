package de.metanome.algorithms.dvbf;

import java.util.ArrayList;
import java.util.List;

import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;
import de.metanome.algorithm_integration.results.BasicStatistic;
import de.metanome.algorithm_integration.results.basic_statistic_values.BasicStatisticValueLong;
/**
 * * Implementation of Linear Counting algorithm.
 * * Reference: Whang, K. Y., Vander-Zanden, B. T., & Taylor, H. M. (1990). A linear-time probabilistic counting algorithm for database applications.
 *   ACM Transactions on Database Systems (TODS), 15(2), 208-229 
 * * @author Hazar.Harmouch
 */
public class DVBloomFilterAlgorithm {

  protected RelationalInputGenerator inputGenerator = null;
  protected BasicStatisticsResultReceiver resultReceiver = null;

  protected String relationName;
  protected List<String> columnNames;
  private final String NUMBEROFDISTINCT = "Number of Distinct Values";
  private RelationalInput input;
  protected int bitperelement=2;
  private int NUMOFTUPLES;
  protected int approache = 0;
  public void execute() throws AlgorithmExecutionException {
    ////////////////////////////////////////////
    // THE DISCOVERY ALGORITHM LIVES HERE :-) //
    ////////////////////////////////////////////


    input = this.inputGenerator.generateNewCopy();
    this.relationName = input.relationName();
    this.columnNames = input.columnNames();
    // (1) Specify the desired accuracy (i.e., the standard error)=0.01
    // (2) Measure the relation cardinality (complexity = 0 (q)) if it is not already available
    while (input.hasNext()) {
      input.next();
      NUMOFTUPLES++;
    }


    input = this.inputGenerator.generateNewCopy();
    this.relationName = input.relationName();
    this.columnNames = input.columnNames();
   ArrayList<BloomFilter> Columns = new ArrayList<BloomFilter>();
    
            
      for (int i = 0; i < columnNames.size(); i++)
        Columns.add(new BloomFilter(NUMOFTUPLES,bitperelement));
    
    while (input.hasNext()) {
      List<String> CurrentTuple = input.next();
      
      for (int i = 0; i < columnNames.size(); i++)
        Columns.get(i).add(CurrentTuple.get(i));

    }
    // (4.3)estimates the columns cardinality
approache=1;
    for (int i = 0; i < columnNames.size(); i++) {
      if(approache==0)
      addStatistic(NUMBEROFDISTINCT, Columns.get(i).cardinality_Swamidass(), columnNames.get(i),
          relationName);
      else
        addStatistic(NUMBEROFDISTINCT, Columns.get(i).cardinality_Papapetrou(), columnNames.get(i),
            relationName);
    }

  }

  private void addStatistic(String StatisticName, long Value, String ColumnName,
      String RelationName) throws AlgorithmExecutionException {
    BasicStatistic result = new BasicStatistic(new ColumnIdentifier(RelationName, ColumnName));
    result.addStatistic(StatisticName, new BasicStatisticValueLong(Value));
    //System.out.println(StatisticName + " of " + ColumnName + " : " + Value);
    resultReceiver.receiveResult(result);
  }

  public void setApproache(int approache) {
    this.approache = approache;
  }

  
}
