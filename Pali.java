/**********************************************
*
* Class: Pali
* Author: Ben Partida
* Purpose: This program asks for how many
* prime number palindromes the user wants 
* and finds that many palindromes.
*
**********************************************/


import java.util.Scanner;

public class Pali 
{

    public static void main(String[] args) 
	{

        Scanner in = new Scanner(System.in);
        int start = 1;
        int prime = 2;
        final int numbPerLine = 10;

        primeCheck(prime);
        paliCheck(prime);
		
		System.out.println();
        System.out.println("How many Palindromes do you want? ");
        int userIn = in.nextInt();

        while (start <= userIn)
        { 
                if (primeCheck(prime) && paliCheck(prime)) 
				{
                    System.out.print(prime + " ");

                    if (start % numbPerLine == 0) 

                        System.out.println();

                        start++;
                }

                prime++;
        }
    }

    public static boolean primeCheck(int checkMe) 
	{
		if (checkMe % 2 == 0 && checkMe != 2) 
		{
			return false;
		}

		int sqrtPrime = (int)Math.sqrt(checkMe);
		for (int i = 3; i <= sqrtPrime; i += 2) 
		{
			if (checkMe % i == 0) 
			{
				return false;
			}
		}

		return true;
	}

    public static boolean paliCheck(int checkMe) 
	{
		int checkMeBackup = checkMe;
		int reverse = 0;

		while (checkMe > 0) 
		{
			reverse = reverse * 10 + checkMe % 10;
			checkMe = checkMe / 10;
		}

		return (checkMeBackup == reverse);
	}      
}
