package de.uni_potsdam.hpi.metanome_test_runner;

import de.uni_potsdam.hpi.metanome_test_runner.config.Config;
import de.uni_potsdam.hpi.metanome_test_runner.mocks.MetanomeMock;

public class MetanomeTestRunner {

    public static String[] inputTableNames =
            {
                    "generated_0_1",
                    "generated_0_2",
                    "generated_0_3",
                    "generated_0_4",
                    "generated_0_5",
                    "generated_0_6",
                    "generated_0_7",
                    "generated_0_8",
                    "generated_0_9",
                    "generated_1_1",
                    "generated_1_2",
                    "generated_1_3",
                    "generated_1_4",
                    "generated_1_5",
                    "generated_1_6",
                    "generated_1_7",
                    "generated_1_8",
                    "generated_1_9",
                    "generated_2_1",
                    "generated_2_2",
                    "generated_2_3",
                    "generated_2_4",
                    "generated_2_5",
                    "generated_2_6",
                    "generated_2_7",
                    "generated_2_8",
                    "generated_2_9",
                    "generated_3_1",
                    "generated_3_2",
                    "generated_3_3",
                    "generated_3_4",
                    "generated_3_5",
                    "generated_3_6",
                    "generated_3_7",
                    "generated_3_8",
                    "generated_3_9",
                    "generated_4_1",
                    "generated_4_2",
                    "generated_4_3",
                    "generated_4_4",
                    "generated_4_5",
                    "generated_4_6",
                    "generated_4_7",
                    "generated_4_8",
                    "generated_4_9",
                    "generated_5_1",
                    "generated_5_2",
                    "generated_5_3",
                    "generated_5_4",
                    "generated_5_5",
                    "generated_5_6",
                    "generated_5_7",
                    "generated_5_8",
                    "generated_5_9",
                    "generated_6_1",
                    "generated_6_2",
                    "generated_6_3",
                    "generated_6_4",
                    "generated_6_5",
                    "generated_6_6",
                    "generated_6_7",
                    "generated_6_8",
                    "generated_6_9",
                    "generated_7_1",
                    "generated_7_2",
                    "generated_7_3",
                    "generated_7_4",
                    "generated_7_5",
                    "generated_7_6",
                    "generated_7_7",
                    "generated_7_8",
                    "generated_7_9",
                    "generated_8_1",
                    "generated_8_2",
                    "generated_8_3",
                    "generated_8_4",
                    "generated_8_5",
                    "generated_8_6",
                    "generated_8_7",
                    "generated_8_8",
                    "generated_8_9",
                    "generated_9_1",
                    "generated_9_2",
                    "generated_9_3",
                    "generated_9_4",
                    "generated_9_5",
                    "generated_9_6",
                    "generated_9_7",
                    "generated_9_8",
                    "generated_9_9",
                    //------------- sorted by size-----------------
//     "ncvoter-column8",
//      "ncvoter-column9",
//   "ncvoter-column21",
//   "ncvoter-column20",
//    "ncvoter-column19",
//    "ncvoter-column13",
//      "ncvoter-column25",
//      "ncvoter-column12",
//      "ncvoter-column17",
//      "ncvoter-column24",
//      "ncvoter-column15",
//      "ncvoter-column22",
//      "ncvoter-column18",
//     "ncvoter-column16",
//      "ncvoter-column23",
//      "ncvoter-column11",
//      "ncvoter-column7",
//      "ncvoter-column6",
//     "ncvoter-column1",
//     "ncvoter-column2",
//     "ncvoter-column3",
//     "ncvoter-column4",
//     "ncvoter-column5",
//     "ncvoter-column10",
//      "ncvoter-column14",
                    //--------------------------------------------
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
                    "ncvoter-column13",
                    "ncvoter-column14",
                    "ncvoter-column15",
                    "ncvoter-column16",
                    "ncvoter-column17",
                    "ncvoter-column18",
                    "ncvoter-column19",
                    "ncvoter-column20",
                    "ncvoter-column21",
                    "ncvoter-column22",
                    "ncvoter-column23",
                    "ncvoter-column24",
                    "ncvoter-column25",
//--------------sorted by cardinality-----------------------------
//     "openaddress5",
//     "openaddress8",
//     "openaddress7",  
//     "openaddress6",
//     "openaddress9",
//     "openaddress3",
//     "openaddress4",
//      "openaddress10",
//      "openaddress2",
//      "openaddress1",
//     "openaddress11",  

//------------------------by size-------------------
//      "openaddress5",
//      "openaddress8",
//      "openaddress7",
//      "openaddress10",
//      "openaddress6",
//      "openaddress9",
//      "openaddress4",
//      "openaddress3",
//      "openaddress11",
//      "openaddress1",
//      "openaddress2",
//  

                    //==================================
                    "openaddress-column1",
                    "openaddress-column2",
                    "openaddress-column3",
                    "openaddress-column4",
                    "openaddress-column5",
                    "openaddress-column6",
                    "openaddress-column7",
                    "openaddress-column8",
                    "openaddress-column9",
                    "openaddress-column10",
                    "openaddress-column11"

            };

    public static double eps = 0.01;

    public static void run() {
        Config conf = new Config();
// for each dataset
            for (int i = 0; i < inputTableNames.length; i++) {
                conf.inputDatasetName = inputTableNames[i];
                if(  conf.inputDatasetName.contains("generated"))
                    MetanomeMock.execute(conf, eps);
                else {
                    //ten runs for realworld datasets
                    for (int j = 0; j < 9; j++)
                        MetanomeMock.execute(conf, eps);
                }
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

        MetanomeMock.execute(conf, eps);
    }

    private static void wrongArguments(String[] args) {
        StringBuilder message = new StringBuilder();
        message.append("\r\nArguments not supported!");
        message.append("\r\nProvide correct values: <algorithm> <dataset>");
        throw new RuntimeException(message.toString());
    }
}
