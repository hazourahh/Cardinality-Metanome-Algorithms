package de.metanome.algorithms.dvgee;

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



/** @author Hazar.Harmouch**/

public class DVGEE extends DVGEEAlgorithm implements BasicStatisticsAlgorithm, RelationalInputParameterAlgorithm, StringParameterAlgorithm {

	public enum Identifier {
		INPUT_GENERATOR,
		SAMPLING_PERCENTAGE
	};
	
	@Override
	public ArrayList<ConfigurationRequirement<?>> getConfigurationRequirements() {
		ArrayList<ConfigurationRequirement<?>> conf = new ArrayList<>();
		conf.add(new ConfigurationRequirementRelationalInput(DVGEE.Identifier.INPUT_GENERATOR.name()));
		ConfigurationRequirementString  input_rate=new ConfigurationRequirementString(DVGEE.Identifier.SAMPLING_PERCENTAGE.name());
		 input_rate.setRequired(false);
        String[] Defaults={"0.05"};
        input_rate.setDefaultValues(Defaults);
        conf.add( input_rate);
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
		if (!DVGEE.Identifier.INPUT_GENERATOR.name().equals(identifier))
			throw new AlgorithmConfigurationException("Input generator does not match the expected identifier: " + identifier + " (given) but " + DVGEE.Identifier.INPUT_GENERATOR.name() + " (expected)");
		this.inputGenerator = values[0];
	}

	@Override
	public void execute() throws AlgorithmExecutionException {
		super.execute();
	}
	
	@Override
    
    public void setStringConfigurationValue(String identifier, String... values)
        throws AlgorithmConfigurationException {
      if (DVGEE.Identifier.SAMPLING_PERCENTAGE.name().equals(identifier))
      {
        if(values!=null && !values[0].equals("") )
        {try{
         double percentage= Double.parseDouble(values[0]);
         if(percentage>0 && percentage<1)
         this.sampling_percentage=percentage;
         else
           throw new Exception();
        }catch(Exception ex)
        {throw new AlgorithmConfigurationException("The sampling percentage should be a positive double in ]0, 1[ range");}
        
        }
        
        
      }}

	  @Override
	  public String getAuthors() {
	    return "Hazar Harmouch";
	  }

	  @Override
	  public String getDescription() {
	    return "GEE: M. Charikar, S. Chaudhuri, R. Motwani, and V. R. Narasayya, Towards estimation error guarantees for distinct values, in PODS, 2000, pp. 268–279";
	  }


}
