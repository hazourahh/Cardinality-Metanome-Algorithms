package de.uni_potsdam.hpi.metanome_test_runner.mocks;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.results.BasicStatistic;
import de.metanome.algorithm_integration.results.Result;
import de.metanome.algorithms.dva.DVA;
import de.metanome.algorithms.dvakmv.DVAKMV;
import de.metanome.algorithms.dvams.DVAMS;
import de.metanome.algorithms.dvbf.DVBloomFilter;
import de.metanome.algorithms.dvbjkst.DVBJKST;
import de.metanome.algorithms.dvfm.DVFM;
import de.metanome.algorithms.dvgee.DVGEE;
import de.metanome.algorithms.dvhyperloglog.DVHyperLogLog;
import de.metanome.algorithms.dvhyperloglogplus.DVHyperLogLogPlus;
import de.metanome.algorithms.dvlc.DVLC;
import de.metanome.algorithms.dvloglog.DVLogLog;
import de.metanome.algorithms.dvmincount.DVMinCount;
import de.metanome.algorithms.dvpcsa.DVPCSA;
import de.metanome.algorithms.dvsuperloglog.DVSuperLogLog;
import de.metanome.backend.input.file.DefaultFileInputGenerator;
import de.metanome.backend.result_receiver.ResultCache;
import de.uni_potsdam.hpi.metanome_test_runner.config.Config;
import de.uni_potsdam.hpi.metanome_test_runner.utils.FileUtils;

public class MetanomeMock {

	public static void execute(Config conf,double eps) {
	  String[] error={eps+""};


//	 execute_AKMV(conf,error);
//
//	execute_HyperLogLogplus(conf);
//
//	execute_Mincount(conf,error);
//
//	 execute_HyperLogLog(conf,error);
//
//	 execute_AMS(conf);
//
//	  execute_PCSA(conf,error);
//
//	 execute_BF(conf, 0);
//
//	  execute_BF(conf, 1);
//
//	 execute_SuperLogLog(conf,error);
//
//      execute_LogLog(conf,error);
//
//	  execute_LC(conf,error);
//
//	  execute_BJKST(conf,error);
//	 execute_exact(conf);
          
//    execute_FM(conf,error);
    execute_sampling(conf, error);  
			
	}
	
	
	
	static long getCurrentlyUsedMemory() {
	  return  ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(); 
	     // Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory();
	}
	
static void trytoclean()
{System.gc();
try {
  Thread.sleep(100);
} catch (InterruptedException e) {
  e.printStackTrace();
}}
	//---------------------------------------------------------------------
	private static String format(List<Result> results) {
		StringBuilder builder = new StringBuilder();
		for (Result result : results) {
		  BasicStatistic od = (BasicStatistic) result;
			builder.append(od.getStatisticMap().values().toArray()[0]);
			
			
		}
		return builder.toString();
	}
	public static void execute_exact(Config conf) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVA algorithm = new DVA ();
          
          algorithm.setRelationalInputConfigurationValue(DVA.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          
          //memory
          trytoclean();
          long m= getCurrentlyUsedMemory();
          
          //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          
        //memory
          m=getCurrentlyUsedMemory()-m;
          
          
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("exact done with dataset"+conf.inputDatasetName+ " time:"+ time+" memory "+m/1024+ "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_sampling(Config conf, String[] sampling_rate) {
      try {
          //sampling_rate[0]="0.2";
       // conf.inputDatasetName="generated_0_2";
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVGEE algorithm = new DVGEE();
          algorithm.setRelationalInputConfigurationValue(DVGEE.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVGEE.Identifier.SAMPLING_PERCENTAGE.name(),sampling_rate);
          
          //memory
          trytoclean();
          long m= getCurrentlyUsedMemory();
          
          //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          
        //memory
          m=getCurrentlyUsedMemory()-m;
        
          
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+sampling_rate[0]+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+sampling_rate[0]+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+sampling_rate[0]+","+conf.inputDatasetName+","+m/1024+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("sampling done with dataset"+conf.inputDatasetName+ " time:"+ time+" memory "+m/1024+ "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
   
    public static void execute_FM(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVFM algorithm = new DVFM ();
          
          algorithm.setRelationalInputConfigurationValue(DVFM.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVFM.Identifier.STANDARD_ERROR.name(),eps);
        //memory
          trytoclean();
          long m= getCurrentlyUsedMemory();
        
          //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          
          //memory
          m=getCurrentlyUsedMemory()-m;
   
          
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("FM done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_PCSA(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVPCSA algorithm = new DVPCSA ();
          
          algorithm.setRelationalInputConfigurationValue(DVPCSA.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVPCSA.Identifier.STANDARD_ERROR.name(),eps);
        //memory
          trytoclean();
          long m= getCurrentlyUsedMemory();
          
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          //memory
          m=getCurrentlyUsedMemory()-m;
        
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("PCSA done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_BJKST(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVBJKST algorithm = new DVBJKST ();
          
          algorithm.setRelationalInputConfigurationValue(DVBJKST.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVBJKST.Identifier.RELATIVE_ERROR.name(),eps);
        //memory
          trytoclean();
          long m= getCurrentlyUsedMemory();
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          //memory
          m=getCurrentlyUsedMemory()-m;
       
        
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println(" DVBJKST done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_LC(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVLC algorithm = new  DVLC  ();
          
          algorithm.setRelationalInputConfigurationValue( DVLC .Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVLC.Identifier.STANDARD_ERROR.name(),eps);
        //memory
          trytoclean();
          long m=getCurrentlyUsedMemory();
          
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
        //memory
          m=getCurrentlyUsedMemory()-m;
   
        
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("DVLC  done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_BF(Config conf, int approach) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVBloomFilter algorithm = new   DVBloomFilter  ();
          algorithm.setApproache(approach);
          algorithm.setRelationalInputConfigurationValue(  DVBloomFilter .Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
        //memory
          trytoclean();
          long m=getCurrentlyUsedMemory();
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
        //memory
          m=getCurrentlyUsedMemory()-m;
     
        
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              if(approach==0){
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_1"+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_1"+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_1"+conf.statisticsFileName+"+_memory");
              
              System.out.println(" DVBloomFilter-1  done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
              }
              else
              {
                FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_2"+conf.resultFileName);
                FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_2"+conf.statisticsFileName);
                FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+"_2"+conf.statisticsFileName+"+_memory");
                
                System.out.println(" DVBloomFilter-2  done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
              }
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_AKMV(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVAKMV algorithm = new DVAKMV ();
          
          algorithm.setRelationalInputConfigurationValue(DVAKMV.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVAKMV.Identifier.RELATIVE_ERROR.name(),eps);
        //memory
          trytoclean();
          long m=getCurrentlyUsedMemory();
          
          
          //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          
          //memory
          m=getCurrentlyUsedMemory()-m;
    
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("AKMV done with dataset"+conf.inputDatasetName+ " time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_Mincount(Config conf, String[] eps) {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVMinCount algorithm = new  DVMinCount();
          
          algorithm.setRelationalInputConfigurationValue( DVMinCount.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
          algorithm.setStringConfigurationValue(DVMinCount.Identifier.STANDARD_ERROR.name(),eps);
        //memory
          trytoclean();
          long m=getCurrentlyUsedMemory();
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          //memory
          m=getCurrentlyUsedMemory()-m;
  
          
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println(" MinCount done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_AMS(Config conf)
	 {
      try {
          RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                  conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                  conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                  conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
          
          ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
          
          DVAMS algorithm = new   DVAMS();
          
          algorithm.setRelationalInputConfigurationValue(  DVAMS.Identifier.INPUT_GENERATOR.name(), inputGenerator);
          algorithm.setResultReceiver(resultReceiver);
        //memory
          trytoclean();
          long m=getCurrentlyUsedMemory();
          
          
        //time
          long time = System.currentTimeMillis();
          algorithm.execute();
          time = System.currentTimeMillis() - time;
          //memory
          m=getCurrentlyUsedMemory()-m;
  
        
          if (conf.writeResults) {
      
              List<Result> results = resultReceiver.fetchNewResults();
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
              FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
              
              System.out.println("AMS done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
          }
      }
      catch (AlgorithmExecutionException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
  }
	public static void execute_LogLog(Config conf, String[] eps)
    {
     try {
         RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                 conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                 conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                 conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
         
         ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
         
         DVLogLog algorithm = new  DVLogLog();
         
         algorithm.setRelationalInputConfigurationValue( DVLogLog.Identifier.INPUT_GENERATOR.name(), inputGenerator);
         algorithm.setResultReceiver(resultReceiver);
         algorithm.setStringConfigurationValue(DVLogLog.Identifier.STANDARD_ERROR.name(),eps);
       //memory
         trytoclean();
         long m=getCurrentlyUsedMemory();
         
       //time
         long time = System.currentTimeMillis();
         algorithm.execute();
         time = System.currentTimeMillis() - time;
         //memory
         m=getCurrentlyUsedMemory()-m;
       
         if (conf.writeResults) {
     
             List<Result> results = resultReceiver.fetchNewResults();
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
             
             System.out.println("DVLogLog done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
         }
     }
     catch (AlgorithmExecutionException e) {
         e.printStackTrace();
     }
     catch (IOException e) {
         e.printStackTrace();
     }
 }
	public static void execute_SuperLogLog(Config conf, String[] eps)
    {
     try {
         RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                 conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                 conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                 conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
         
         ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
         
         DVSuperLogLog algorithm = new  DVSuperLogLog();
         
         algorithm.setRelationalInputConfigurationValue( DVSuperLogLog.Identifier.INPUT_GENERATOR.name(), inputGenerator);
         algorithm.setResultReceiver(resultReceiver);
         algorithm.setStringConfigurationValue(DVSuperLogLog.Identifier.STANDARD_ERROR.name(),eps);
       //memory
         trytoclean();
         long m= getCurrentlyUsedMemory();
         
       //time
         long time = System.currentTimeMillis();
         algorithm.execute();
         time = System.currentTimeMillis() - time;
         //memory
         m=getCurrentlyUsedMemory()-m;
  
       
         if (conf.writeResults) {
     
             List<Result> results = resultReceiver.fetchNewResults();
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
             
             System.out.println("DVSuperLogLog done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
         }
     }
     catch (AlgorithmExecutionException e) {
         e.printStackTrace();
     }
     catch (IOException e) {
         e.printStackTrace();
     }
 }
	public static void execute_HyperLogLog(Config conf, String[] eps)
    {
     try {
         RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                 conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                 conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                 conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
         
         ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
         
         DVHyperLogLog algorithm = new   DVHyperLogLog();
         
         algorithm.setRelationalInputConfigurationValue(  DVHyperLogLog.Identifier.INPUT_GENERATOR.name(), inputGenerator);
         algorithm.setResultReceiver(resultReceiver);
         algorithm.setStringConfigurationValue(DVHyperLogLog.Identifier.STANDARD_ERROR.name(),eps);
       //memory
         trytoclean();
         long m= getCurrentlyUsedMemory();
         
         
       //time
         long time = System.currentTimeMillis();
         algorithm.execute();
         time = System.currentTimeMillis() - time;
         //memory
         m=getCurrentlyUsedMemory()-m;
        
         if (conf.writeResults) {
     
             List<Result> results = resultReceiver.fetchNewResults();
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
             
             System.out.println(" DVHyperLogLog done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
         }
     }
     catch (AlgorithmExecutionException e) {
         e.printStackTrace();
     }
     catch (IOException e) {
         e.printStackTrace();
     }
 }
    public static void execute_HyperLogLogplus(Config conf)
    {
     try {
         RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(new ConfigurationSettingFileInput(
                 conf.inputFolderPath + conf.inputDatasetName + conf.inputFileEnding, true,
                 conf.inputFileSeparator, conf.inputFileQuotechar, conf.inputFileEscape, conf.inputFileStrictQuotes, 
                 conf.inputFileIgnoreLeadingWhiteSpace, conf.inputFileSkipLines, conf.inputFileHasHeader, conf.inputFileSkipDifferingLines, conf.inputFileNullString));
         
         ResultCache resultReceiver = new ResultCache("MetanomeMock",null);
         
         DVHyperLogLogPlus algorithm = new   DVHyperLogLogPlus();
         
         algorithm.setRelationalInputConfigurationValue(  DVHyperLogLogPlus.Identifier.INPUT_GENERATOR.name(), inputGenerator);
         algorithm.setResultReceiver(resultReceiver);
       //memory
         trytoclean();
         long m= getCurrentlyUsedMemory();
         
       
       //time
         long time = System.currentTimeMillis();
         algorithm.execute();
         time = System.currentTimeMillis() - time;
         //memory
         m=getCurrentlyUsedMemory()-m;
    
         if (conf.writeResults) {
     
             List<Result> results = resultReceiver.fetchNewResults();
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+format(results)+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.resultFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+time+"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName);
             FileUtils.writeToFile(algorithm.getClass().getSimpleName()+","+conf.inputDatasetName+","+m/1024 +"\r\n",  conf.measurementsFolderPath+File.separator+algorithm.getClass().getSimpleName()+conf.statisticsFileName+"+_memory");
             
             System.out.println("DVHyperLogLogPlus done with dataset"+conf.inputDatasetName+" time:"+ time+" memory "+m/1024 + "kbyte");
         }
     }
     catch (AlgorithmExecutionException e) {
         e.printStackTrace();
     }
     catch (IOException e) {
         e.printStackTrace();
     }
 }

}
