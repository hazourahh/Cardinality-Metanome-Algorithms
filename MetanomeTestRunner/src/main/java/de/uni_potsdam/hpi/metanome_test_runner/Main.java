package de.uni_potsdam.hpi.metanome_test_runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.uni_potsdam.hpi.metanome_test_runner.config.Config;
import de.uni_potsdam.hpi.metanome_test_runner.utils.*;


public class Main {

  public static void main(String[] args) {
    MetanomeTestRunner.run();
    // MetanomeTestRunner.run(args);

    //generate()

  }


  void generate() {
    Config conf = new Config();
    ArrayList<Long> cardinality = new ArrayList<>();
    //1 to 9
    for (int i = 1; i < 10; i++)
      cardinality.add((long) Math.pow(10, i));

    String outputPath = conf.measurementsFolderPath + "generated" + File.separator;
    //5 runs
    for (int n = 0; n < 5; n++) {
      MersenneTwisterFast rnd = new MersenneTwisterFast();

      for (int i = 0; i < cardinality.size(); i++) {

        long current = cardinality.get(i);

        try {
          for (int j = 0; j < current; j++) {

            FileUtils.writeToFile(rnd.nextLong() + "\n",
                outputPath + "generated_" + n + "_" + (i+1) + ".csv");
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
