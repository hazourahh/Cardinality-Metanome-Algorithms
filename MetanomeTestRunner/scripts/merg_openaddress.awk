BEGIN {OFS = "," ;}
    FNR==1 && NR!=1 { getline; }
    1 {print;}
	
