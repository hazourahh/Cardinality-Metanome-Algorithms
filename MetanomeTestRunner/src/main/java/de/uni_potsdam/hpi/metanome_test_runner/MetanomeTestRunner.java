package de.uni_potsdam.hpi.metanome_test_runner;

import de.uni_potsdam.hpi.metanome_test_runner.config.Config;
import de.uni_potsdam.hpi.metanome_test_runner.mocks.MetanomeMock;

public class MetanomeTestRunner {

	public static void run() {
		Config conf = new Config();
		
		MetanomeMock.execute(conf);
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
		
		MetanomeMock.execute(conf);
	}
	
	private static void wrongArguments(String[] args) {
		StringBuilder message = new StringBuilder();
		message.append("\r\nArguments not supported!");
		message.append("\r\nProvide correct values: <algorithm> <dataset>");
		throw new RuntimeException(message.toString());
	}
}
