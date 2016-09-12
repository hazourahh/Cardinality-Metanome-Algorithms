package de.metanome.algorithms.dvhyperloglogplus;

import java.util.ArrayList;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.algorithm_types.BasicStatisticsAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.RelationalInputParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.StringParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementInteger;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.BasicStatisticsResultReceiver;


public class DVHyperLogLogPlus extends DVHyperLogLogAlgorithmplus implements BasicStatisticsAlgorithm, RelationalInputParameterAlgorithm, StringParameterAlgorithm  {

	public enum Identifier {
		INPUT_GENERATOR,
		PERCISION,
		PERCISION_SPARSE
		
	};
	
	@Override
	public ArrayList<ConfigurationRequirement<?>> getConfigurationRequirements() {
		ArrayList<ConfigurationRequirement<?>> conf = new ArrayList<>();
		conf.add(new ConfigurationRequirementRelationalInput(DVHyperLogLogPlus.Identifier.INPUT_GENERATOR.name()));
		ConfigurationRequirementInteger p=new ConfigurationRequirementInteger(DVHyperLogLogPlus.Identifier.PERCISION.name());
		ConfigurationRequirementInteger ps=new ConfigurationRequirementInteger(DVHyperLogLogPlus.Identifier.PERCISION.name());
        p.setRequired(true);
        ps.setRequired(true);
        Integer[] Defaultp={14};
        p.setDefaultValues(Defaultp);
        Integer[] Defaultps={25};
        ps.setDefaultValues(Defaultps);
        conf.add(p);
        conf.add(ps);
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
		if (!DVHyperLogLogPlus.Identifier.INPUT_GENERATOR.name().equals(identifier))
			throw new AlgorithmConfigurationException("Input generator does not match the expected identifier: " + identifier + " (given) but " + DVHyperLogLogPlus.Identifier.INPUT_GENERATOR.name() + " (expected)");
		this.inputGenerator = values[0];
	}

	@Override
	public void execute() throws AlgorithmExecutionException {
		super.execute();
	}

	@Override
	  public void setStringConfigurationValue(String identifier, String... values)
	      throws AlgorithmConfigurationException {
	    if (DVHyperLogLogPlus.Identifier.PERCISION.name().equals(identifier))
	    {
	      if(values!=null && !values[0].equals("") & values[1].equals("") )
	      {try{
	       int p= Integer.parseInt(values[0]);
	       int ps= Integer.parseInt(values[1]);
	       if ((p < 4) || ((p > ps) && (ps != 0))) {
	         throw new IllegalArgumentException("p must be between 4 and sp (inclusive)");
	       }
	       if (ps > 32) {
	         throw new IllegalArgumentException("sp values greater than 32 not supported");
	       } 
	       this.p=p;
	       this.ps=ps;
	      }catch(Exception ex)
	      {throw new AlgorithmConfigurationException("The input should be an integer");}
	      
	      }
	      
	      
	    }}

  @Override
  public String getAuthors() {
    
    return "Hazar Harmouch";
  }

  @Override
  public String getDescription() {

    return "Heule, S., Nunkesser, M., & Hall, A. (2013). HyperLogLog in practice: algorithmic engineering of a state of the art cardinality estimation algorithm. In Proceedings of the 16th International Conference on Extending Database Technology";
  }

}
