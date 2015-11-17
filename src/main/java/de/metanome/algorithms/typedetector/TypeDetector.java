package de.metanome.algorithms.typedetector;

import java.util.ArrayList;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.algorithm_types.BasicStatisticsAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.RelationalInputParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;


public class TypeDetector extends TypeDetectorAlgorithm implements BasicStatisticsAlgorithm, RelationalInputParameterAlgorithm {

	public enum Identifier {
		INPUT_GENERATOR
	};
	
	@Override
	@SuppressWarnings("rawtypes")
	public ArrayList<ConfigurationRequirement> getConfigurationRequirements() {
		ArrayList<ConfigurationRequirement> conf = new ArrayList<>();
		conf.add(new ConfigurationRequirementRelationalInput(TypeDetector.Identifier.INPUT_GENERATOR.name()));
		//conf.add(new ConfigurationRequirementRelationalInput(MyIndDetector.Identifier.INPUT_GENERATOR.name(), ConfigurationRequirement.ARBITRARY_NUMBER_OF_VALUES)); // For IND discovery, the number of inputs is arbitrary
		//conf.add(new ConfigurationRequirementInteger(MyOdDetector.Identifier.IMPORTANT_PARAMETER.name())); // The algorithm can ask the user for other parameters if needed. If so, then implement the Integer/String/BooleanParameterAlgorithm interfaces as well
		return conf;
	}

	@Override
	public void setResultReceiver(BasicStatisticsResultReceiver resultReceiver) {
		this.resultReceiver = resultReceiver;
	}

	@Override
	public void setRelationalInputConfigurationValue(String identifier, RelationalInputGenerator... values) throws AlgorithmConfigurationException {
		if (!TypeDetector.Identifier.INPUT_GENERATOR.name().equals(identifier))
			throw new AlgorithmConfigurationException("Input generator does not match the expected identifier: " + identifier + " (given) but " + TypeDetector.Identifier.INPUT_GENERATOR.name() + " (expected)");
		this.inputGenerator = values[0];
	}

	@Override
	public void execute() throws AlgorithmExecutionException {
		super.execute();
	}

}
