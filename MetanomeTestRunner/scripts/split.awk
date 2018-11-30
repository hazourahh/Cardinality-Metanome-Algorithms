BEGIN  { FS=","; FPAT = "([^,]*)|(\"[^\"]+\")"}
 {
 suff="-column"
 ext=".csv"
if((ARGV[1]=="ncvoter.csv" && NF==71) || (ARGV[1]=="openaddress.csv" && NF==11))
{
for(i = 1; i <= NF; i++)
  {  gsub(/"/, "", $i);
     gsub(/^[ \t]+/,"",$i);
     if (length($i)>0)  
     {printf  $i"\n" >> gensub(ext,suff i ext,"g",ARGV[1]);} 
   }
 }
}