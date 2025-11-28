import java.util.Scanner;

public class GCDFinder
{ 
// 
   public static void main(String[] args)
{
   int a;
   int b; 
   int rem;
   GCF= 0;
   String str;
   Scanner input= new Scanner(System.in);
   System.out.print("Enter first Number =====>   ");
   str= input.nextLine();
   a= Integer.parseInt(str);
   System.out.print("Enter next number =====>    ");
   str= input.nextLine();
   b= Interger.parseInt(str);
  while (GCF=0)
 {
   rem= a%b;
   if (rem= 0)
       GCF= b;
   else  
      {
        a=b;
        b= rem;
      }
 }
   System.out.println("The GCF is " + GCF);
 }

      
   