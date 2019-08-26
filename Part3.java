import edu.duke.*;
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        //startIndex=dna.indexOf("ATG");
        int stopIndex=dna.indexOf(stopCodon,startIndex+3);
        while(stopIndex != -1){
            if((stopIndex-startIndex)%3==0){
                return stopIndex;
            }
            else{
                stopIndex=dna.indexOf(stopCodon,stopIndex+1);
            }
        }
        return dna.length();
    }
    
    public String findGene(String dna,int where){
        int startIndex=dna.indexOf("ATG",where);
        if(startIndex==-1){
            return "";
        }
        int TAAindex=findStopCodon(dna,startIndex,"TAA");
        int TGAindex=findStopCodon(dna,startIndex,"TGA");
        int TAGindex=findStopCodon(dna,startIndex,"TAG");
        int minStopIndex=Math.min(TAAindex,Math.min(TGAindex,TAGindex));
        if(minStopIndex != dna.length()){
            return dna.substring(startIndex,minStopIndex+3);
        }
        return "";
    }

    public void printAllGene(String dna){
        int where = 0;
        int count=0;
        while(true){
            String gene=findGene(dna,where);
            if(gene.isEmpty()){
                break;
            }
            System.out.println("Gene found ! "+gene);
            count++;
            where=(dna.indexOf(gene,where) + gene.length());
        }
        System.out.println("Total genes found: "+count);
    }
    
    public StorageResource getAllGene(String dna){
        int where = 0;
        int count=0;
        StorageResource sr = new StorageResource();
        while(true){
            String gene=findGene(dna,where);
            if(gene.isEmpty()){
                break;
            }
            //System.out.println("Gene found ! "+gene);
            sr.add(gene);
            count++;
            where=(dna.indexOf(gene,where) + gene.length());
        }
        System.out.println("Total genes found: "+count);
        return sr;
    }
    
    public void processGenes(StorageResource sr){
        int strMore9=0,highCG=0,longestStrLength=0,strMore60=0;
        float cgRatio=0;
        int totalCTG=0;
        for(String gene:sr.data()){
            if(gene.length()>longestStrLength){
                longestStrLength=gene.length();
            }
            
            if(gene.length()>9){
                System.out.println("String with more than 9 characters: "+gene);
                strMore9++;
            }
            
            if(gene.length()>60){
                System.out.println("String with more than 60 characters: "+gene);
                strMore60++;
            }
            totalCTG+=countCTG(gene);
            System.out.println("The total number of CTGs found in this gene "+gene+" is: "+countCTG(gene));
            cgRatio=findCGRatio(gene);
            if(cgRatio>0.35){
                System.out.println("Gene with more than 0.35 CGRatio: "+gene);
                highCG++;
            }
        }
        System.out.println("Total number of strings"+ sr.size());
        System.out.println("Total numbers of strings with more than 9 characters: "+strMore9);
        System.out.println("Total numbers of strings with more than 60 characters: "+strMore60);
        System.out.println("Total number of strings with more than 0.35 CG ratio: "+highCG);
        System.out.println("The longest string length recorded is: "+longestStrLength);
        System.out.println("Total CTGs: "+totalCTG);
        
        
    }
    
    public void testProcessGenes(){
        /*StorageResource sr=new StorageResource();
        sr.add("ATGFCCWEAQTAA");
        sr.add("ATGTAA");
        sr.add("ATGCCCCCCTAA");
        sr.add("ATGAAABBBGTAA");
        sr.add("ATGGCCGCRTAG");*/
        //processGenes(sr);
        // FileResource fr = new FileResource("fdna.fa");
        // String dna=fr.asString();
        // processGenes(getAllGene(dna));
        
        FileResource fr1 = new FileResource("roger.fa");
        String dna1=fr1.asString();
        StorageResource sr2=new StorageResource();
        /*sr2=getAllGene(dna1);
        processGenes(sr2);*/
        System.out.println(countCTG(dna1));
    }
    
    public int countCTG(String dna){
        int start=0,where=0,count=0;
        while(true){
          start=dna.indexOf("CTG",where);
          if(start==-1 || start==dna.length()){
                break;
          }
          count++;
          where=start+3;
        }
        return count;
    }
    
        public float findCGRatio(String dna){
        int c=0,g=0;
        c=findChar(dna,"C");
        g=findChar(dna,"G");
        //int cg=c+g;
        //System.out.println("length "+dna.length());
        float CGratio=(float)(c+g)/dna.length();
        return CGratio;
    }
    
    public int findChar(String dna, String input){
        int where=0,count=0,inputIndex=0;
        while(true){           
            inputIndex=dna.indexOf(input,where);
            if(where==dna.length() || inputIndex==-1){
                break;
            }
            
            if(inputIndex!=-1){
                count++;
            }
            where=inputIndex+1;
        }
        //System.out.println("count for "+input+" is"+count);
        return count;
    }
    

}
