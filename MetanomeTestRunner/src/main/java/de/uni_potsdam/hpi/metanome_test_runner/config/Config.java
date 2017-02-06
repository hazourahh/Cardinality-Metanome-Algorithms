package de.uni_potsdam.hpi.metanome_test_runner.config;

import java.io.File;

public class Config {

	public enum Algorithm {
		DVA
	}
	
	public enum Dataset {
		PLANETS, SYMBOLS, SCIENCE, SATELLITES, GAME, ASTRONOMICAL, ABALONE, ADULT, BALANCE, BREAST, BRIDGES, CHESS, ECHODIAGRAM, FLIGHT, HEPATITIS, HORSE, IRIS, LETTER, NURSERY, PETS, NCVOTER_1K, UNIPROD_1K
	}
	
	public Config.Algorithm algorithm;
	public Config.Dataset dataset;
	
	public String inputDatasetName;
	public String inputFolderPath = "data" + File.separator;
    public String inputFileEnding = ".csv";
    public char inputFileSeparator = '\t';
    public char inputFileQuotechar = '\0';
    public char inputFileEscape = '\0';
    public int inputFileSkipLines = 0;
    public boolean inputFileStrictQuotes = false;
    public boolean inputFileIgnoreLeadingWhiteSpace = false;
    public boolean inputFileHasHeader = false;
    public boolean inputFileSkipDifferingLines = false;
    public String inputFileNullString = "\\N";   
	


	
	public String measurementsFolderPath = "io" + File.separator + "measurements" + File.separator;
	
	public String statisticsFileName = "statistics.txt";
	public String resultFileName = "results.txt";
	
	public boolean writeResults = true;
	
	public Config() {
		this(Config.Algorithm.DVA, Config.Dataset.PLANETS);
	}

	public Config(Config.Algorithm algorithm, Config.Dataset dataset) {
		this.algorithm = algorithm;
		this.setDataset(dataset);
	}

	@Override
	public String toString() {
		return "Config:\r\n\t" +
			"algorithm: " + this.algorithm.name() + "\r\n\t" +
			"dataset: " + this.inputDatasetName + this.inputFileEnding;
	}

	private void setDataset(Config.Dataset dataset) {
		this.dataset = dataset;
		switch (dataset) {
			case PLANETS:
				//this.inputDatasetName = "WDC_planetz";
			 
			    this.inputDatasetName = "edit_note";
				//this.inputFileSeparator = ',';
                //this.inputFileHasHeader = true;
				break;

			case SYMBOLS:
				this.inputDatasetName = "WDC_symbols";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case SCIENCE:
				this.inputDatasetName = "WDC_science";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case SATELLITES:
				this.inputDatasetName = "WDC_satellites";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case GAME:
				this.inputDatasetName = "WDC_game";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case ASTRONOMICAL:
				this.inputDatasetName = "WDC_astronomical";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case ABALONE:
				this.inputDatasetName = "abalone";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case ADULT:
				this.inputDatasetName = "adult";
				this.inputFileSeparator = ';';
				this.inputFileHasHeader = false;
				break;
			case BALANCE:
				this.inputDatasetName = "balance-scale";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case BREAST:
				this.inputDatasetName = "breast-cancer-wisconsin";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case BRIDGES:
				this.inputDatasetName = "bridges";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case CHESS:
				this.inputDatasetName = "chess";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case ECHODIAGRAM:
				this.inputDatasetName = "echocardiogram";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case FLIGHT:
				this.inputDatasetName = "flight_1k";
				this.inputFileSeparator = ';';
				this.inputFileHasHeader = true;
				break;
			case HEPATITIS:
				this.inputDatasetName = "hepatitis";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case HORSE:
				this.inputDatasetName = "horse";
				this.inputFileSeparator = ';';
				this.inputFileHasHeader = false;
				break;
			case IRIS:
				this.inputDatasetName = "iris";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case LETTER:
				this.inputDatasetName = "letter";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case NURSERY:
				this.inputDatasetName = "nursery";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = false;
				break;
			case PETS:
				this.inputDatasetName = "pets";
				this.inputFileSeparator = ';';
				this.inputFileHasHeader = true;
				break;
			case NCVOTER_1K:
				this.inputDatasetName = "ncvoter_1001r_19c";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
			case UNIPROD_1K:
				this.inputDatasetName = "uniprot_1001r_223c";
				this.inputFileSeparator = ',';
				this.inputFileHasHeader = true;
				break;
		}
	}
}
