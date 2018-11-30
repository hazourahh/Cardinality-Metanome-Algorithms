Pre-processing: 
•	Use the generate method in  MetanomeTestRunner to generate all synthetic datasets.
•	Download zip files of ncvoter and open address and unzip them. 
•	Place the ncvoter*.txt in folder ncvoter and openaddr-collected-europe in the same folder with the scripts and this readme
•	Run bash script preprocess.sh which will do the following 
o	merge all files into one large file  
awk -f merg_ncvoter.awk  ./ncvoter/ncvoter*.txt >ncvoter.csv  
find ./openaddr-collected-europe -name “*.csv” |cat -n| while read n f; do mv “$f” ./openaddress/“openaddress$n.csv”; done
awk -f merg_open address.awk  openaddress*.csv >openaddress.csv

o	separate each column into its own file and remove all nulls. The idea behind that was to get the runtime of each algorithm to find the cardinality of each column not to a the whole table in a way comparable to the syntactic datasets.

            awk -f split.awk ncvoter.csv  
            awk -f split.awk openaddress.csv  

•	move both syntactic (generated_*_*.csv) and real-world datasets (openaddress-column*.csv and ncvoter-column*.csv) in the folder (data) in the project MetanomeTestRunner
results can be found in the files 
statistics.txt (runtime Ms)
statistics_memory.txt (memory MB)
results.txt (estimated cardinality)

Note: The number of column, tuples, distinct values may varies from the one presented in the paper because it is not guaranteed that the datasets not updated.
In case you need access to the data we used back then we can provide it.