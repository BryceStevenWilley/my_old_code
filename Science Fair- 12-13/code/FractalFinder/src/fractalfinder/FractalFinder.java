
package fractalfinder;
import java.io.*;
import java.util.*;

/*
 * @author bryce
 */
  public class FractalFinder 
   {   
    /*  The following global variables will be declared so the helper methods 
     * can use them.  
     */

    static String str;       
    static boolean keepgoing= false;    
    
    //  creates 5 RAM frames for storing the song data while the program is 
    // running 
    static CurrentSongFile current;
    static CurrentSongFile current1;
    static CurrentSongFile current2;
    static CurrentSongFile current3;
    static CurrentSongFile current4;
    static CurrentSongFile current5;
    static ArrayList points;
        
    
   /*
     * The proper formatting for the input files are as follows:
     *    the name of the file should be: 
     *       <name of song>raw<L for left hand, R for right hand>.dat
     *       ex:  inventionInCrawR.dat
     *    the file: 1st line= the number of notes in the piece(n)
     *              2nd line- n + 1 lines= the notes of the piece in the format:
     *                   <note letter><accidental><octave>
     *                n + 2- end (2n +1)= the origin of each note as a double 
     *                    value, with 0 as the first beat of the song
     * 
     * the output of the file is controlled by which function is currently
     * hardwired in, but the name of the output function should be either 
     * <name of song>ss<L or R><reduction number>.dat for Spreadsheet or 
     * <name of Song>vi<L or R><reduction number>.dat for Visual
     *                   
     */
    public static void main(String[] args)throws IOException 
   {     loadFile();
         reduceAndCopyFile();
         Scanner input= new Scanner(System.in);
         String buff="";
         String buff_beta="";
         do
         {
            System.out.println("Which Reduction of the song would you like? (Press \'q\' to quit)");
            buff=input.next();
            System.out.println("Visual or Spreadsheet? (type v or s)");
            buff_beta=input.next();
            switch(buff.charAt(0))
            {
                case '0':
                   if (buff_beta.charAt(0)=='s')
                   {current.exportReducedSpreadSheet(str,0);}
                   else if (buff_beta.charAt(0)=='v')
                   {current.exportVisualData(str);}       
                   break;
                case '1':
                    if (buff_beta.charAt(0)=='s')
                   {current1.exportReducedSpreadSheet(str,1);}
                   else if (buff_beta.charAt(0)=='v')
                   {current1.exportVisualData(str);}
                   break;
                    
                case '2':
                     if (buff_beta.charAt(0)=='s')
                   {current2.exportReducedSpreadSheet(str,2);}
                   else if (buff_beta.charAt(0)=='v')
                   {current2.exportVisualData(str);}
                   break;
                    
                case '3':
                     if (buff_beta.charAt(0)=='s')
                   {current3.exportReducedSpreadSheet(str,3);}
                   else if (buff_beta.charAt(0)=='v')
                   {current3.exportVisualData(str);}
                   break;
                    
                case '4':
                     if (buff_beta.charAt(0)=='s')
                   {current4.exportReducedSpreadSheet(str,4);}
                   else if (buff_beta.charAt(0)=='v')
                   {current4.exportVisualData(str);}
                   break;
                    
                case '5':
                     if (buff_beta.charAt(0)=='s')
                   {current5.exportReducedSpreadSheet(str,5);}
                   else if (buff_beta.charAt(0)=='v')
                   {current5.exportVisualData(str);}
                   break;
                    
                case 'q':
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Incorrect reduction!  Please Try again.");
                    break;
            } 
            
         } while(!(buff.equals("q")));
         
    } 


    public static void loadFile() throws IOException
    {     

         Scanner input= new Scanner(System.in);
         System.out.println("What is the name of the file?");
         str= input.nextLine();
         current= new CurrentSongFile(str);          
         System.out.println("Importing Song Data into Java song manipulator");
         keepgoing=true;        
     
    }

    public static void reduceAndCopyFile() 
   { 
      current1= new CurrentSongFile(current);
      for( int i=1; i <= current1.getNumNotes()/2; i+=1)
         ((Song)current1.getData()).deleteNote(i);   
      current1.updateNumNotes(); 
      current2= new CurrentSongFile(current1);
      for( int i=1; i <= current2.getNumNotes()/2; i+=1)
           ((Song)current2.getData()).deleteNote(i);  
      current2.updateNumNotes();
      current3= new CurrentSongFile(current2);
      for( int i=1; i <= current3.getNumNotes()/2; i+=1)
           ((Song)current3.getData()).deleteNote(i); 
      current3.updateNumNotes();
      current4= new CurrentSongFile(current3);
      for( int i=1; i <= current4.getNumNotes()/2; i+=1)
           ((Song)current4.getData()).deleteNote(i);
      current4.updateNumNotes();
      current5= new CurrentSongFile(current4);
      for( int i=1; i <= current5.getNumNotes()/2; i+=1)
           ((Song)current5.getData()).deleteNote(i);       
      current5.updateNumNotes();
      
  }
     public static void createPoints()
     {
         points= new ArrayList(8);
         points.add(new Double(0));
         points.add((double)(current.getData()).getTotalIntervals());
         points.add(new Double(1));
         points.add((double)(current1.getData()).getTotalIntervals());
         points.add(new Double(2));
         points.add((double)(current2.getData()).getTotalIntervals());
         points.add(new Double(3));
         points.add((double)(current3.getData()).getTotalIntervals());
             
     }
   
  }

class CurrentSongFile implements Cloneable

{    
    private int sizeOfDat;
    private int numNotes=0;
    private Song data;
    public CurrentSongFile()
    {           }
       
    public CurrentSongFile(String s) throws IOException
    {   
        BufferedReader inStream;
        File file1;
        
        // temporary storage for readline function
        String temp="";
        
        System.out.println("Checking if file exists, is readable, and is writeable");
        file1= new File(s);
        if (file1.exists() && file1.canRead() && file1.canWrite())
          {
            System.out.println("All systems are go.");
            System.out.println("Loading existing file.");
          inStream= new BufferedReader(new FileReader(s)); 
            //  The next few methods require that each .dat file has the number 
            //  of notes on the very first line of the file        
          
            temp=inStream.readLine();  
            numNotes= Integer.parseInt(temp);
            
            //  Try out this algorithm too.  
            //  sizeOfDat= file1.length();
            //  numNotes= (sizeOfDat*1000)/5;
            
            data = new Song(numNotes); 
            int y= 0;
            while ((temp= inStream.readLine()) != null && y < numNotes)  
            { 
              char n= temp.charAt(0);
              char sf= temp.charAt(1);
              int oct= (int) temp.charAt(2) - 48; 
              System.out.println(temp);
              System.out.println("" + n + sf+ oct);         
              data.writeNote(y, n, sf, oct); 
              y++;
            }
             y=0;
            while (y < numNotes)
            {
                double ori= Double.parseDouble(temp); 
                data.editNote(y, ori);
                y++;
                temp=inStream.readLine(); 
            }
           data.findIntervalsFirst();
          }
        else
        {System.err.println("A problem has occured.  Please try again.");}
       
    
       
     }
   public CurrentSongFile(CurrentSongFile c)
   {
       System.out.println("Calling Copy Constructor");
       sizeOfDat= c.sizeOfDat;
       numNotes= c.numNotes;
       data= new Song(c.data);
       
   }
    // public void addToSongFile() throws IOException
    // {
     /*  int out=-1;     
      *   Song letsStart= new Song(10);
      *  letsStart.writeNote(1, 'C', 4);
      *  letsStart.writeNote(2, 'C', '#', 4);
      *  System.out.println(letsStart);
      *  System.out.println(letsStart.getNoteDistance(1));
      *  System.out.println(letsStart.getNoteDistance(2));
      *  letsStart.findIntervals();
      *  for (int i=0; i < letsStart.getNumberOfNotes(); i++)
      *  {
      *      hey=letsStart.getNoteName(i+1);
      *      outStream.write(hey);
      *  }
      *  theseintervals= (ArrayList)(letsStart.getIntervals()).clone();
      * for(int i = 0; i < letsStart.getNumberOfNotes(); i++ )
      *     //something that assigns integer out the values contained in the 
      *      Integer Objects inside these intervals arraylist. 
      *     outStream.write(String.valueOf(out));
      *  System.out.println(theseintervals);
      *  outStream.close();
  *  }
  */ 
        void canRead(File file1)
    {
             if(file1.exists() &&  file1.canRead() && file1.canWrite())
             {System.out.println("All systems are a go.");}
         else
             {System.out.println("Something is wrong");}
         
    }
       
      public void exportReducedSpreadSheet(String importName, int redct) throws IOException
    { 
      int len= importName.length();
      int mark=-1;
      for (int y=0; y < len-4; y++)
      {
         if (importName.substring(y,y+3).toLowerCase().equals("raw"))
            mark=y;
         // else if(importName.substring(y));
      }
      String nameOfExport= importName.substring(0,mark) + "ss" + 
                           importName.substring(mark+3,mark+4)+ 
                           (char)(redct+48) + importName.substring(mark+4);
      data.findIntervalsAnotherTime();
      File file23= new File(nameOfExport);
      file23.createNewFile();
      FileWriter outFile= new FileWriter(nameOfExport);
      BufferedWriter outStream=  new BufferedWriter(outFile);
      outStream.write("" + numNotes);
      outStream.newLine();
      for (int i= 0; i < this.numNotes; i++) 
      {
         outStream.write("" + data.getNoteOrgin(i));
         outStream.newLine();
      }
      outStream.newLine();
      for (int i =0; i < this.numNotes; i++)
      {
         outStream.write("" + data.getNoteName(i));
         outStream.newLine();
      }
      outStream.newLine();
      for (int i = 0; i < this.numNotes; i ++)
      {
         outStream.write("" + data.getNoteDistance(i));
         outStream.newLine();
      }
      outStream.newLine();
      for (int i =0; i < this.numNotes; i++)
      {
         outStream.write("" + data.getNoteHertz(i));
         outStream.newLine();
      }
      outStream.newLine();
      for ( int i = 0; i < this.numNotes-1;  i++)
      {
         outStream.write("" + data.getSingleInterval(i));
         outStream.newLine();
      }  
      outStream.newLine();
      outStream.write("Total Number of Intervals:  ");
      outStream.write("" + data.getTotalIntervals());
      outStream.newLine(); 
      outStream.write("Total Number of Notes:   ");
      outStream.write("" + data.getNumberOfNotes());
      outStream.newLine();
      outStream.write("Average Size of Intervals:   ");
      double AvgSize= ((double)data.getTotalIntervals()/ (double)(data.getNumberOfNotes()-1));
      outStream.write("" + AvgSize);
      outStream.close();
    }
      
      
      public void exportVisualData(String nameOfExport) throws IOException
    { 
      data.findIntervalsAnotherTime();
      File file23= new File("V"+nameOfExport);
      file23.createNewFile();
      FileWriter outFile= new FileWriter("V"+nameOfExport);
      BufferedWriter outStream=  new BufferedWriter(outFile);
      outStream.write("" + numNotes);
      outStream.newLine();
      // writes the info for the first note seperately b/c there is no interval data
      outStream.write(""+data.getNoteOrgin(0)+" "+data.getNoteName(0)+" "+data.getNoteDistance(0)+
                     " "+data.getNoteHertz(0));
      outStream.newLine();
      for( int i =1;  i < numNotes; i++)
      {
        outStream.write(""+data.getNoteOrgin(i)+" "+data.getNoteName(i)+" "+data.getNoteDistance(i)+
                        " "+data.getNoteHertz(i)+" " + ((ArrayList)data.getIntervals()).get(i-1)); 
        outStream.newLine();
      }
      outStream.close();
    }
      public void updateNumNotes()
    {
      numNotes=((ArrayList)data.getSong()).size();
    }
      public int getNumNotes()
    {
      return numNotes;
    } 
      public Song getData()
    {
      return data;
    }
}

class DataCrusher
{
    /*************************************************************************
 *  Compilation:  javac LinearRegression.java StdIn.java
 *  Execution:    java LinearRegression < data.txt
 *  
 *  Reads in a sequence of pairs of real numbers and computes the
 *  best fit (least squares) line y  = ax + b through the set of points.
 *  Also computes the correlation coefficient and the standard errror
 *  of the regression coefficients.
 *
 *  Note: the two-pass formula is preferred for stability.
 *
 *************************************************************************/
static double R2;
static String form;
    public static void findLinearRegression(ArrayList points) { 
        int MAXN = 1000;
        int n = 0;
        double[] x = new double[MAXN];
        double[] y = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(n < points.size()) {
            x[n] = (double)(((Double)(points.get(n))).doubleValue());
            y[n] = (double)((Double)(points.get(n+1))).doubleValue();
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n+= 2;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        form= "y   = " + beta1 + " * x + " + beta0;
        System.out.println(form);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1*x[i] + beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
        System.out.println("R^2                 = " + R2);
        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
        svar0 = svar * sumx2 / (n * xxbar);
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
    }
   public double getR2()
   {
       return R2;
   }
   public String getForm()
   {
       return form;
   }
}

