package de.metanome.algorithms.dvams;

import java.util.ArrayList;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.algorithm_types.BasicStatisticsAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.RelationalInputParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.StringParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementString;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;


public class DVAMS extends DVAMSAlgorithm implements BasicStatisticsAlgorithm, RelationalInputParameterAlgorithm, StringParameterAlgorithm  {

	public enum Identifier {
		INPUT_GENERATOR,
		RATIO_ERROR
		
	};
	
	@Override
	public ArrayList<ConfigurationRequirement<?>> getConfigurationRequirements() {
		ArrayList<ConfigurationRequirement<?>> conf = new ArrayList<>();
		conf.add(new ConfigurationRequirementRelationalInput(DVAMS.Identifier.INPUT_GENERATOR.name()));
		ConfigurationRequirementString inputstandard_error=new ConfigurationRequirementString(DVAMS.Identifier.RATIO_ERROR.name());
        inputstandard_error.setRequired(false);
        String[] Defaults={"3"};
        inputstandard_error.setDefaultValues(Defaults);
        conf.add(inputstandard_error);
        
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
		if (!DVAMS.Identifier.INPUT_GENERATOR.name().equals(identifier))
			throw new AlgorithmConfigurationException("Input generator does not match the expected identifier: " + identifier + " (given) but " + DVAMS.Identifier.INPUT_GENERATOR.name() + " (expected)");
		this.inputGenerator = values[0];
	}

	@Override
	public void execute() throws AlgorithmExecutionException {
		super.execute();
	}

	@Override
	  public void setStringConfigurationValue(String identifier, String... values)
	      throws AlgorithmConfigurationException {
	    if (DVAMS.Identifier.RATIO_ERROR.name().equals(identifier))
	    {
	      if(values!=null && !values[0].equals("") )
	      {try{
	       double error= Double.parseDouble(values[0]);
	       if(error>2)
	       this.eps=error;
	       else
	         throw new Exception();
	      }catch(Exception ex)
	      {throw new AlgorithmConfigurationException("The Ratio Error Epsilon should be larger than 2 to get a Standard error >=1");}
	      
	      }
	      
	      
	    }}

  @Override
  public String getAuthors() {
    return "Hazar Harmouch";
  }

  @Override
  public String getDescription() {
    return "AMS Cardinality Estimator. Alon, N., Matias, Y., & Szegedy, M. (1996, July). The space complexity of approximating the frequency moments. In Proceedings of the twenty-eighth annual ACM symposium on Theory of computing (pp. 20-29). ACM.";
  }

}
