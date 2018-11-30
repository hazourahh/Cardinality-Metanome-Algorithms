BEGIN  {  FPAT = "([^,]*)|(\"[^\"]+\")"}
 {
 suff="-column"
 ext=".csv"
 for (i = 1; i <= NF; i++)
  {if (length($i) != 0)
       print  $i >> gensub(ext,suff i ext,"g",ARGV[1])
   }
 }