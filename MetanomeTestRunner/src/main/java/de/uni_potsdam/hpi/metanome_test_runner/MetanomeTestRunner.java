package de.uni_potsdam.hpi.metanome_test_runner;

import de.uni_potsdam.hpi.metanome_test_runner.config.Config;
import de.uni_potsdam.hpi.metanome_test_runner.mocks.MetanomeMock;

public class MetanomeTestRunner {

  public static String[] inputTableNames =
    {
//      "generated_0_1",
//     "generated_0_2",  
//     "generated_0_3",
//     "generated_0_4",
//     "generated_0_5",
//     "generated_0_6",
//     "generated_0_7",
//
     "generated_1_1",
     "generated_1_2",  
     "generated_1_3",
     "generated_1_4",
     "generated_1_5",
     "generated_1_6",
//     "generated_1_7",
//   
     "generated_2_1",
     "generated_2_2",  
     "generated_2_3",
     "generated_2_4",
     "generated_2_5",
     "generated_2_6",
//     "generated_2_7",
//   
     "generated_3_1",
     "generated_3_2",  
     "generated_3_3",
     "generated_3_4",
     "generated_3_5",
     "generated_3_6",
//     "generated_3_7",
//    
     "generated_4_1",
     "generated_4_2",  
     "generated_4_3",
     "generated_4_4",
     "generated_4_5",
     "generated_4_6",
//     "generated_4_7",
//     
     "ncvoter-column1",
     "ncvoter-column2",
     "ncvoter-column3",
     "ncvoter-column4",
     "ncvoter-column5",
     "ncvoter-column6",
    "ncvoter-column7",
     "ncvoter-column8",
     "ncvoter-column9",
     "ncvoter-column10",
     "ncvoter-column11",
     "ncvoter-column12",
//      "openaddress-column5",
//      "openaddress-column8",
//      "openaddress-column7",
//      "openaddress-column3",
//      "openaddress-column10",
//      "openaddress-column9",
//      "openaddress-column6",
//     "openaddress-column1",
//     "openaddress-column2",
//     "openaddress-column4",
//     "openaddress-column11",
//   "generated_4_8",
//     "generated_4_9",
//     "generated_3_8",
//      "generated_3_9",
//     "generated_2_8",
//     "generated_2_9",
//      "generated_1_8",
//      "generated_1_9",
//     "generated_0_8",
//     "generated_0_9",
     
    };
  
public static double eps=0.01;
	public static void run() {
		Config conf = new Config();
		
		for(int i=0; i<inputTableNames.length;i++)
        {conf.inputDatasetName=inputTableNames[i];
      
        MetanomeMock.execute(conf,eps);
        }
	}

	public static void run(String[] args) {
		if (args.length != 2)
			wrongArguments(args);
		
		Config.Algorithm algorithm = null;
		String algorithmArg = args[0].toLowerCase();
		for (Config.Algorithm possibleAlgorithm : Config.Algorithm.values())
			if (possibleAlgorithm.name().toLowerCase().equals(algorithmArg))
				algorithm = possibleAlgorithm;
		
		Config.Dataset dataset = null;
		String datasetArg = args[1].toLowerCase();
		for (Config.Dataset possibleDataset : Config.Dataset.values())
			if (possibleDataset.name().toLowerCase().equals(datasetArg))
				dataset = possibleDataset;

		if ((algorithm == null))
			wrongArguments(args);
		
		Config conf = new Config(algorithm, dataset);
		
		MetanomeMock.execute(conf,eps);
	}
	
	private static void wrongArguments(String[] args) {
		StringBuilder message = new StringBuilder();
		message.append("\r\nArguments not supported!");
		message.append("\r\nProvide correct values: <algorithm> <dataset>");
		throw new RuntimeException(message.toString());
	}
}
