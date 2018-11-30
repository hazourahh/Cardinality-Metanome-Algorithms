BEGIN {FS="\t"; OFS = "," ;}
    FNR==1 && NR!=1 { getline; }
    1 {$1=$1; print;}
	
