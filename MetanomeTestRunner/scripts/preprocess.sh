gawk -f merg_ncvoter.awk  ./ncvoter/ncvoter*.txt >ncvoter.csv
gawk -f split.awk ncvoter.csv   
rm -r ./openaddr-collected-europe/summary/
find ./openaddr-collected-europe -name "*.csv"|cat -n| while read n f; do mv "$f" "./openaddress/openaddress$n.csv"; done
gawk -f merg_openaddress.awk  ./openaddress/openaddress*.csv >openaddress.csv
gawk -f split.awk openaddress.csv   