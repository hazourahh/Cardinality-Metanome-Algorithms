
# Single Column Profiling Algorithms:
The research area of data profiling includes a large set of methods and processes to examine a given dataset and determine metadata about it ([1](https://hpi.de/fileadmin/user_upload/fachgebiete/naumann/publications/2013/profiling_vision.pdf)). Typically, the results comprise various statistics about the columns and the relationships among them, in particular dependencies. Among the basic
statistics about a column are data type, the number of unique values, maximum and minimum values, the number of null values, and the value distribution.

This repository has two parts:
### Single Column Data Profiler (SCDP) 
It collects the following statistics about each column of the input dataset (*.csv file) 
* Data type	(REAL, SMALLINT, VARCHAR,...)
* Exact number and percentage of distinct values
* Number and percentage of Nulls
* Top 10 frequent items	and their frequencies.
* Min, Max, Standard deviation, Average
* ...

### Cardinality estimation algorithms (projects starts with DV): 

This part contains the implementaion code for [repetability](https://hpi.de/naumann/projects/repeatability/data-profiling/cardinality-estimation.html) of the experiments in my publication:
```
Harmouch, H., Naumann, F.: Cardinality Estimation: An Experimental Survey. Proceedings of the VLDB Endowment (PVLDB). pp. 499 - 512 (2017).
```

This includes the following twelve algorithms which are the most popular and well-know cardinality estimation algorithms:
* Flajolet and Martin (FM)  
* Probabilistic counting with stochastic averaging(PCSA)
* Linear Counting (LC) 
* Alon, Martias and Szegedy (AMS)
* Baryossef, Jayram, Kumar, Sivakumar and Trevisan(BJKST)           
* LogLog
* SuperLogLog
* MinCount 
* AKMV
* HyperLogLog 
* Bloom Filters
* HyperLogLog++

In addition to: 
* GEE Sampling-Based algorithm. 
* As a baseline we used a hash table. 

## Metanome Tool and Profiling Algorithms
[Metanome](www.metanome.de) is a framework that handles both algorithms and datasets as external resources. All the algorithms above have been developed to work within Metanome.

### Run the algorithms using Metanome GUI:

1. Download latest release of Metanome from [Metanome releases page](https://github.com/HPI-Information-Systems/Metanome/releases) as well as
the algorithms from the [Algorithm releases page](https://hpi.de/naumann/projects/data-profiling-and-analytics/metanome-data-profiling/algorithms.html). 
2. Unzip deployment/target/deployment-1.1-SNAPSHOT-package_with_tomcat.zip
3. Go into the unzipped folder, place the algorithm jar-file into the folder /WEB-INF/classes/algorithms and the datasets in the folder /WEB-INF/classes/inputData
5. Start the run script, either run.sh or run.bat(Windows Systems)
6. Open a browser at http://localhost:8080/ and register both the algorithm and the dataset in the Metanome frontend
7. Choose the algorithm and datasource, setting parameter and then run!

### Development:

MetanomeTestRunner: is a project to run the algorithms in development phase. As it is a MVN project all the required Metanome libraries will be automatically downloaded.
If you want to build your own algorithm, give it a look [here](https://github.com/HPI-Information-Systems/metanome-algorithms).

## License

Metanome and all the algorithms developed by the developers group has the following [license](https://github.com/HPI-Information-Systems/Metanome/blob/master/LICENSE). 
