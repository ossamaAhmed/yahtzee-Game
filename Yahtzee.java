/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame()
	{   
		initGame();
		for(int i=0;i<13;i++)
		{
			for(int j=1;j<=nPlayers;j++)
			{
				firstRoll(j);
				secondRoll(j);
				thirdRoll(j);
				updateScore(j);
			}
		}
		showRestScores();		
	}
	//calculates the upper score and upper bonus and lower score 
	private void showRestScores()
	{
		for(int i=0;i<nPlayers;i++)
		{ 
			for(int j=0;j<N_CATEGORIES;j++)
			{
				if((j>=0)&&(j<6))
				{
					score[i][UPPER_SCORE-1]+=score[i][j];
				}
				else if((j>7)&&(j<15))
				{
					score[i][LOWER_SCORE-1]+=score[i][j];
				}
			}
			display.updateScorecard(UPPER_SCORE, (i+1), (score[i][UPPER_SCORE-1]));
			display.updateScorecard(LOWER_SCORE, (i+1), (score[i][LOWER_SCORE-1]));
		}
		addBonus();
	}
	//this function adds bonus for the player if any
	private void addBonus()
	{
		for(int i=0;i<nPlayers;i++)
		{
			if(score[i][UPPER_SCORE-1]>=63)
			{
				score[i][UPPER_BONUS-1]=35;
				score[i][TOTAL-1]+=35;
				display.updateScorecard(TOTAL, (i+1), (score[i][TOTAL-1]));
			}
			display.updateScorecard(UPPER_BONUS, (i+1), (score[i][UPPER_BONUS-1]));
		}
		
	}
	//this function updates the score for the current player 
	private void updateScore(int player)
	{  
		display.printMessage("Select a category for the roll");
		int category=display.waitForPlayerToSelectCategory();
		while(true)
		{ if(flags[player-1][category-1]==false)
		   {break;}
		  else
		  {
			display.printMessage("this category was chosen before !!Select another category for the roll ");
			category=display.waitForPlayerToSelectCategory();
		  }	
		}
		int scores=calculateScore(category,player);
		display.updateScorecard(category, player, scores);
		display.updateScorecard(TOTAL, player, (score[player-1][TOTAL-1]));
		
	}
	//calculates the sore for a specified category
	private int calculateScore(int category,int player)
	{
		if(category==ONES)
		{
		  return onesScore(player);
		}
		else if(category==TWOS)
		{
		  return twosScore(player);
		}
		else if(category==THREES)
		{
		  return threesScore(player);
		}
		else if(category==FOURS)
		{
		  return foursScore(player);
		}
		else if(category==FIVES)
		{
		  return fivesScore(player);
		}
		else if(category==SIXES)
		{
		  return sixesScore(player);
		}
		else if(category==THREE_OF_A_KIND)
		{
			return threeOfKindScore(player);
		}
		else if(category==FOUR_OF_A_KIND)
		{
			return fourOfKindScore(player);
		}
		else if(category==FULL_HOUSE)
		{
			return fullHouseScore(player);
		}
		else if(category==SMALL_STRAIGHT)
		{
			return smallStraightScore(player);
		}
		else if(category==LARGE_STRAIGHT)
		{
			return largeStraightScore(player);
		}
		else if(category==YAHTZEE)
		{
			return yahtzeeScore(player);
		}
		else if(category==CHANCE)
		{
			return chanceScore(player);
		}
		else 
		{
			return 0;
		}
	}
//	returns the score for ones category
	private int onesScore(int player)
	{   
	 flags[player-1][ONES-1]=true;
	 int temp=0;
	 for(int i=0;i<N_DICE;i++)
		{
		  if(dice[i]==1)
		  {
			temp+=1;
		  }
		}
      score[player-1][ONES-1]=temp;
      score[player-1][TOTAL-1]+=temp;
	  return temp;
		
	}
//	returns the score for twos category
	private int twosScore(int player)
	{  
		flags[player-1][TWOS-1]=true;
		int temp=0;
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]==2)
			{
			  temp+=2;
			 }
		}
		score[player-1][TWOS-1]=temp;
	    score[player-1][TOTAL-1]+=temp;
		return temp;
	}
//	returns the score for threes category
	private int threesScore(int player)
	{
		flags[player-1][THREES-1]=true;
		int temp=0;
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]==3)
			  {
				temp+=3;
			  }
		}
		score[player-1][THREES-1]=temp;
	    score[player-1][TOTAL-1]+=temp;
		return temp;
	}
//	returns the score for fours category
	private int foursScore(int player)
	{
	 flags[player-1][FOURS-1]=true;
	 int temp=0;
	 for(int i=0;i<N_DICE;i++)
	  {
		if(dice[i]==4)
	     {
		  temp+=4;
		  }
	   }
	score[player-1][FOURS-1]=temp;
	score[player-1][TOTAL-1]+=temp;
	return temp;
		
	}
//	returns the score for fives category
	private int fivesScore(int player)
	{   
		flags[player-1][FIVES-1]=true;
		int temp=0;
		for(int i=0;i<N_DICE;i++)
		 {
		 if(dice[i]==5)
			{
			 temp+=5;
			}
		 }
		score[player-1][FIVES-1]=temp;
	    score[player-1][TOTAL-1]+=temp;
	    return temp;
		
	}
//	returns the score for sixes category
	private int sixesScore(int player)
	{
	   flags[player-1][SIXES-1]=true;
	   int temp=0;
    	for(int i=0;i<N_DICE;i++)
		{
	    	if(dice[i]==6)
			{
		    	temp+=6;
			}
		}
     score[player-1][SIXES-1]=temp;
	 score[player-1][TOTAL-1]+=temp;
	 return temp;
	}
	//this function handles the three of a kind 
	private int threeOfKindScore(int player)
	{
		flags[player-1][THREE_OF_A_KIND-1]=true;
		if(checkThree())
		{
			int temp=0;
		    for(int i=0;i<N_DICE;i++)
		    {
				 temp+=dice[i];
			}
		    score[player-1][THREE_OF_A_KIND-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;

		}
		else 
		{
			score[player-1][THREE_OF_A_KIND-1]=0;
			return 0;
		}
	}
	//checks the three of a kind
	private boolean checkThree()
	{ 
		int temp=0;
		boolean value=false;
		for(int i=0;i<(N_DICE-2);i++)
		{
			for(int j=i;j<N_DICE;j++)
			{
				if(dice[i]==dice[j])
				{
					temp++;
				}
			 }
			if(temp==3)
			{
				value=true;
				break;
			}
			temp=0;
		}
		return value;
	}
	//this functions handles the four of a kind score 
	private int fourOfKindScore(int player)
	{
		flags[player-1][FOUR_OF_A_KIND-1]=true;
		if(checkFour())
		{
			int temp=0;
		    for(int i=0;i<N_DICE;i++)
		    {
				 temp+=dice[i];
			}
		    score[player-1][FOUR_OF_A_KIND-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;

		}
		else 
		{
			score[player-1][FOUR_OF_A_KIND-1]=0;
			return 0;
		}
	}
	//this function checks for four of a kind 
	private boolean checkFour()
	{ 
		int temp=0;
		boolean value=false;
		for(int i=0;i<(N_DICE-3);i++)
		{
			for(int j=i;j<N_DICE;j++)
			{
				if(dice[i]==dice[j])
				{
					temp++;
				}
			 }
			if(temp==4)
			{
				value=true;
				break;
			}
			temp=0;
		}
		return value;
	}
	//this function handles the score of a fullhouse
	private int fullHouseScore(int player)
	{
		flags[player-1][FULL_HOUSE-1]=true;
		if(checkHouse())
		{    
			int temp=25;
		    score[player-1][FULL_HOUSE-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;
		}
		else 
		{
			score[player-1][FULL_HOUSE-1]=0;
			return 0;
		}
	}
	//this function checks whether it is full house 
	private boolean checkHouse()
	{ 
		int counter1=0;
		int counter2=0;
		int firstvalue=dice[0];
		int secondvalue=0;
		boolean value=false;
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]==firstvalue)
			{
			  counter1++;
			}
	    }
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]!=firstvalue)
			{
			  secondvalue=dice[i];
			  break;
			}
	    }
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]==secondvalue)
			{
			  counter2++;
			}
	    }
		if((counter1==3&&counter2==2)||(counter1==2&&counter2==3))
		{value=true;}
		
	    return value;
	}
	//this function handles the small straight score
	private int smallStraightScore(int player)
	{
		flags[player-1][SMALL_STRAIGHT-1]=true;
		if(checkSmall())
		{    
			int temp=30;
		    score[player-1][SMALL_STRAIGHT-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;
		}
		else 
		{
			score[player-1][SMALL_STRAIGHT-1]=0;
			return 0;
		}
	}
	//this function checks for a small straight
	private boolean checkSmall()
	{
		int[]temp=new int[N_DICE];
		int sort;
		boolean value=false;
		int counter=0;
		for(int i=0;i<N_DICE;i++)
		{
			temp[i]=dice[i];
		}
		for(int i=0;i<N_DICE;i++)
		{
			for(int j=0;j<N_DICE-1-i;j++)
			{
				if(temp[j]>temp[j+1])
				{
					sort=temp[j+1];
					temp[j+1]=temp[j];
					temp[j]=sort;
				}
			}
		}
		sort=0;
		//for repeated dice
		for(int i=0;i<N_DICE;i++)
		{
			for(int j=(i+1);j<N_DICE;j++)
			{
				if(temp[i]==temp[j])
				{
					sort=temp[j];
					temp[j]=temp[0];
					temp[0]=sort;
					break;
				}
			}
		}
		sort=0;
		for(int i=0;i<N_DICE;i++)
		{
			for(int j=1;j<N_DICE-1-i;j++)
			{
				if(temp[j]>temp[j+1])
				{
					sort=temp[j+1];
					temp[j+1]=temp[j];
					temp[j]=sort;
				}
			}
		}
		
		//checks for the first four dice
		for(int i=0;i<N_DICE-2;i++)
		{
			if(temp[i+1]==temp[i]+1)
			{counter++;}
			else
			{
				counter=0;
				break;
			}
		}
		//checks for the last four dice
		if(counter==0)
		{
			for(int i=1;i<N_DICE-1;i++)
			{
				if(temp[i+1]==temp[i]+1)
				{counter++;}
				else
				{
					counter=0;
					break;
				}
			}
		}
		if(counter>=3)
		{
			value=true;
		}
		return value;
	}
	//this function handles the large straight score
	private int largeStraightScore(int player)
	{
		flags[player-1][LARGE_STRAIGHT-1]=true;
		if(checkLarge())
		{    
			int temp=40;
		    score[player-1][LARGE_STRAIGHT-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;
		}
		else 
		{
			score[player-1][LARGE_STRAIGHT-1]=0;
			return 0;
		}
	}
	//this function checks for the large straight
	private boolean checkLarge()
	{
		int[]temp=new int[N_DICE];
		int sort;
		boolean value=false;
		int counter=0;
		for(int i=0;i<N_DICE;i++)
		{
			temp[i]=dice[i];
		}
		for(int i=0;i<N_DICE;i++)
		{
			for(int j=0;j<N_DICE-1-i;j++)
			{
				if(temp[j]>temp[j+1])
				{
					sort=temp[j+1];
					temp[j+1]=temp[j];
					temp[j]=sort;
				}
			}
		}
		//checks for the first four dice
		for(int i=0;i<N_DICE-1;i++)
		{
			if(temp[i+1]==temp[i]+1)
			{counter++;}
			else
			{
				counter=0;
				break;
			}
		}
		//checks for the last four dice
		if(counter>=4)
		{
			value=true;
		}
		return value;
	}
	//this function handles the yahtzee category
	private int yahtzeeScore(int player)
	{
		flags[player-1][YAHTZEE-1]=true;
		if(checkYahtzee())
		{    
			int temp=50;
		    score[player-1][YAHTZEE-1]=temp;
			score[player-1][TOTAL-1]+=temp;
			return temp;
		}
		else 
		{
			score[player-1][YAHTZEE-1]=0;
			return 0;
		}
	}
	//this function checks for yahtzee
	private boolean checkYahtzee()
	{
		boolean value=true;
		int temp=dice[0];
		for(int i=0;i<N_DICE;i++)
		{
			if(dice[i]!=temp)
			{
				value=false;
				break;
			}
		}
		return value;
	}
	//this function handles the chance category
	private int chanceScore(int player)
	{
		flags[player-1][CHANCE-1]=true;
		int temp=0;
		for(int i=0;i<N_DICE;i++)
		  {
		   temp+=dice[i];
		  }
		score[player-1][CHANCE-1]=temp;
	    score[player-1][TOTAL-1]+=temp;
		return temp;
	}
    //	this function handles the third roll where the player chooses the dice to roll again 
	private void thirdRoll(int player)
	{
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
		display.waitForPlayerToSelectDice();
		for(int i=0;i<N_DICE;i++)
		{ 
			if(display.isDieSelected(i))
			{dice[i]=rgen.nextInt(1, 6);}
		}
		display.displayDice(dice);
	}
	//this function handles the second roll where the player chooses the dice to roll again 
	private void secondRoll(int player)
	{   
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
		display.waitForPlayerToSelectDice();
		for(int i=0;i<N_DICE;i++)
		{ 
			if(display.isDieSelected(i))
			{dice[i]=rgen.nextInt(1, 6);}
		}
		display.displayDice(dice);
	}
	//this function handles the first roll of the player
	private void firstRoll(int player)
	{
		display.printMessage(playerNames[player-1]+"'s turn !! CliCk \" Roll Dice\" button to roll dice");
		display.waitForPlayerToClickRoll(player );
		randomizeDice();
		//the following lines are for debugging reasons to put the values of the dice in the first roll intead of randomizing
		/*dice[0]=5;
		dice[1]=3;
		dice[2]=4;
		dice[3]=6;
		dice[4]=6;
		display.displayDice(dice);*/
		
	}
	//makes the 5 dices in a random numbers 
	private void randomizeDice()
	{
		for(int i=0;i<N_DICE;i++)
		{
			dice[i]=rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
	}
	//initializes the arrays
	private void initGame()
	{
		initDiceArray();
		initScoreArray();
		initflags();
	}
	//puts zeros in the dice array 
	private void initDiceArray()
	{
		dice=new int[N_DICE];
		for(int i=0;i<N_DICE;i++)
		{
			dice[i]=0;
		}
	}
    //puts zeros in the score array
	private void initScoreArray()
	{
		score=new int[nPlayers][N_CATEGORIES];
		for(int i=0;i<nPlayers;i++)
		{ 
			for(int j=0;j<N_CATEGORIES;j++)
			{
				score[i][j]=0;
			}
		}
	}
   //puts zeros in the array that keeps record for the category clicked
	private void initflags()
    { 
		flags=new boolean[nPlayers][N_CATEGORIES];
		
		for(int i=0;i<nPlayers;i++)
		{  
			for (int j=0;j<N_CATEGORIES;j++)
			  {
				flags[i][j]=false;
			  }
		}
    	
    }
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[]dice;
	private int[][]score;
	private boolean[][]flags;

}
