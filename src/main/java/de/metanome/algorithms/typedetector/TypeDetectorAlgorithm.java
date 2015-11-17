package de.metanome.algorithms.typedetector;


import java.util.ArrayList;
import java.util.List;

import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;
import de.metanome.algorithm_integration.results.BasicStatistic;

public class TypeDetectorAlgorithm {

  protected RelationalInputGenerator inputGenerator = null;
  protected BasicStatisticsResultReceiver resultReceiver = null;
  private RelationalInput input = null;
  // general statistic
  protected String relationName;
  protected int NumofTuples = 0;
  protected List<String> columnNames;
  protected List<ColumnMainProfile> columnsProfile;


  public void execute() throws AlgorithmExecutionException {

    ////////////////////////////////////////////
    // THE DISCOVERY ALGORITHM LIVES HERE :-) //
    ////////////////////////////////////////////

    InitialiseColumnProfiles();
    // get data types
    getColumnsProfiles();

    // output
    addStatistic("# Columns", columnNames.size(), "*", relationName);
    addStatistic("# Tuples", NumofTuples, "*", relationName);
    for (int i = 0; i < columnsProfile.size(); i++) {// System.out.println(columnsProfile.get(i).toString());
      generateColumnStatistic(columnsProfile.get(i));
    }


  }

  private void InitialiseColumnProfiles() throws InputGenerationException, InputIterationException {
    input = this.inputGenerator.generateNewCopy();
    this.relationName = input.relationName();
    this.columnNames = input.columnNames();
    columnsProfile = new ArrayList<ColumnMainProfile>();
    // generate an initial profiles according to the first record
    if (input.hasNext()) {
      NumofTuples++;
      List<String> firstrecord = input.next();
      // for each column
      for (int i = 0; i < columnNames.size(); i++) {
        ColumnMainProfile profile = new ColumnMainProfile(columnNames.get(i));
        String currentColumnvalue = firstrecord.get(i);
        // data type
        profile.setDataType(DataTypes.getDataType(currentColumnvalue));
        // semantic data type for string
        if (profile.getDataType() == DataTypes.mySTRING)
          profile.setSemantictype(DataTypes.getSemanticDataType(currentColumnvalue));
        // null value
        if (currentColumnvalue == null)
          profile.increaseNumNull();
        else {
          // longest and shortest string
          profile.setLongestString(currentColumnvalue);
          profile.setShortestString(currentColumnvalue);
          // max min sum
          if (DataTypes.isNumeric(profile.getDataType())) {
            profile.setMax(Util.getnumberfromstring(currentColumnvalue));
            profile.setMin(Util.getnumberfromstring(currentColumnvalue));
            profile.setSum(Util.getnumberfromstring(currentColumnvalue));
            // frequency
            profile.addValueforfreq(currentColumnvalue);
            profile.addValueforlengdist(currentColumnvalue.length());
            ////////////////
            // rest values
            //////////////////////
          }


        }
        columnsProfile.add(i, profile);
      }
    }
  }


  private void getColumnsProfiles() throws InputGenerationException, InputIterationException {
    List<String> currentrecord = null;
    // for each tuple
    while (input.hasNext()) {
      // read a tuple
      NumofTuples++;
      currentrecord = input.next();
      // for each column in a tuple verify the data type and update if new data type detected
      for (int i = 0; i < currentrecord.size(); i++)
        if (currentrecord.get(i) == null)
          columnsProfile.get(i).increaseNumNull();
        else
          columnsProfile.get(i).updateColumnProfile(currentrecord.get(i));
    }
    // add the calculated values
    for (int i = 0; i < columnsProfile.size(); i++)
      columnsProfile.get(i).setCalculatedFields(NumofTuples);
  }

  private void addStatistic(String StatisticName, Object Value, String ColumnName,
      String RelationName) throws AlgorithmExecutionException {
    BasicStatistic bs =
        new BasicStatistic(StatisticName, Value, new ColumnIdentifier(RelationName, ColumnName));
    System.out.println(StatisticName + " of " + ColumnName + " : " + Value);
    resultReceiver.receiveResult(bs);
  }


  private void generateColumnStatistic(ColumnMainProfile cs) throws AlgorithmExecutionException {
    //for all with string
    addStatistic("# Null", cs.getNumofNull(), cs.getColumnName(), relationName);
    addStatistic("% Null", cs.getPercentNull(), cs.getColumnName(), relationName);
    addStatistic("# Distinct", cs.getDistinctValues(), cs.getColumnName(), relationName);
    addStatistic("% Distinct", cs.getPercentDistinct(), cs.getColumnName(), relationName);
    addStatistic("Frequencies", cs.getFreq(), cs.getColumnName(), relationName);
    addStatistic("Top k", cs.getTopkValues(), cs.getColumnName(), relationName);
    
    // just for strings
    if (cs.getDataType() == DataTypes.mySTRING) {
      String stringwithlength =
          cs.getDataType() + "[" + Util.roundUp(cs.getLongestString().length(), 16) + "]";
      addStatistic("Data Type", stringwithlength, cs.getColumnName(), relationName);
      addStatistic("Longest String", cs.getLongestString(), cs.getColumnName(), relationName);
      addStatistic("Shortest String", cs.getShortestString(), cs.getColumnName(), relationName);
      addStatistic("Min String", cs.getFirstString(), cs.getColumnName(), relationName);
      addStatistic("Max String", cs.getLasttString(), cs.getColumnName(), relationName);
      addStatistic("Semantic Type", cs.getSemantictype(), cs.getColumnName(), relationName);
      addStatistic("Length Distribution", cs.getLengthdist(), cs.getColumnName(), relationName);

    } else {
      //all types not string
      addStatistic("Data Type", cs.getDataType(), cs.getColumnName(), relationName);
      
      //just numbers
      if (DataTypes.isNumeric(cs.getDataType())) {
        addStatistic("Min", cs.getMin(), cs.getColumnName(), relationName);
        addStatistic("Max", cs.getMax(), cs.getColumnName(), relationName);
        addStatistic("Avg.", cs.getAvg(), cs.getColumnName(), relationName);
        addStatistic("Standard Deviation", cs.getStdDev(), cs.getColumnName(), relationName);
        addStatistic("Median", cs.getMedian(), cs.getColumnName(), relationName);
      }

    }

  }



}
