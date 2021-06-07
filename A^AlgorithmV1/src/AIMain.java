

import java.util.ArrayList;

public class AIMain
{
	
	public static int[][] copy= new int[3][3]; 
	
	public static ArrayList<String> moveList = new ArrayList<String>(); 
	public static String lastmove=""; //make sure the last move isn;'t an opposite dummy 
	public static int[][] table = {
			{1,2,3},
			{0,4,6},
			{7,5,8}};
	public static int[][] goal =  {
	{1,2,3},
	{4,5,6},
	{7,8,0} };
	
	public static void main(String[] args)
	{
		//remember to increment depth when choosing 
		int h = -1 ; 
		
		int moves = 0; 
		long limit = 100000 ; 
		
		while(h!=0&&moves<limit )
		{
			h = seeOptions(); 
			
			moves++; 
		}
		
		
		System.out.println("MOVES:  "+ moves);
		for(int i= 0 ; i<moveList.size(); i++)
		{
			System.out.print(moveList.get(i)+", ");
		}
		
	}
	
	public static int evaluator(int[][] table, int[][] goal)
	{
		// checks how many tiles are misplaced ( h ) by comparing it to the goal table 
		// after using this once the best 
		int h = 0; // the amount of mismatched tiles 
		for(int i= 0 ; i<table.length; i++)
		{
			for(int y=0; y<table[i].length; y++)
			{
				if(table[i][y]!=0)
				{
					if(table[i][y]!=goal[i][y])
					{
						h++; 
					}
				}
			}
		}
		System.out.println(h+"*");
		return h; 
	}
	
	public static int visualize(int zeroi, int zeroy, String direction)
	{
		// makes a copy of the table to see the f(n) = g(n) +h(n)
		
		for(int i= 0 ; i<copy.length; i++)
		{
			for(int y=0; y<copy[i].length; y++)
			{
				copy[i][y] =table[i][y]; System.out.print(copy[i][y]+ "    ");
			}
			System.out.println();
		}
		System.out.println();
		int dummy; 
		switch(direction)
		{
		case "right":
			dummy=copy[zeroi][zeroy]; // zero
			copy[zeroi][zeroy]=copy[zeroi][zeroy-1]; //switching the places
			copy[zeroi][zeroy-1] = dummy ; 
			
			break; 
		case "left":
			dummy=copy[zeroi][zeroy]; // zero
			copy[zeroi][zeroy]=copy[zeroi][zeroy+1]; //switching the places
			copy[zeroi][zeroy+1] = dummy ; 
			break; 
		case "up":
			dummy=copy[zeroi][zeroy]; // zero
			copy[zeroi][zeroy]=copy[zeroi+1][zeroy]; //switching the places
			copy[zeroi+1][zeroy] = dummy ; 
			break; 
		case "down":
			dummy=copy[zeroi][zeroy]; // zero
			copy[zeroi][zeroy]=copy[zeroi-1][zeroy]; //switching the places
			copy[zeroi-1][zeroy] = dummy ; 
			
			break; 
		}
		
		return evaluator(copy,goal); //checks the visualization to the goal 
	}
	
	public static void  move(int zeroi, int zeroy, String direction) //actually changes the table 
	{
		int dummy; 
		switch(direction)
		{
		case "right":
			dummy=table[zeroi][zeroy]; // zero
			table[zeroi][zeroy]=table[zeroi][zeroy-1]; //switching the places
			table[zeroi][zeroy-1] = dummy ; 
			
			break; 
		case "left":
			dummy=table[zeroi][zeroy]; // zero
			table[zeroi][zeroy]=table[zeroi][zeroy+1]; //switching the places
			table[zeroi][zeroy+1] = dummy ; 
			break; 
		case "up":
			dummy=table[zeroi][zeroy]; // zero
			table[zeroi][zeroy]=table[zeroi+1][zeroy]; //switching the places
			table[zeroi+1][zeroy] = dummy ; 
			break; 
		case "down":
			dummy=table[zeroi][zeroy]; // zero
			table[zeroi][zeroy]=table[zeroi-1][zeroy]; //switching the places
			table[zeroi-1][zeroy] = dummy ; 
			
			break; 
		}
	}
	public static int relayOptions(int zeroi,int zeroy,ArrayList<String> options) // remember to save the last chosen option
	{
		int lowestSum=-1; //if it equals -1 then it hasn't found the lowest yet
		
		String opposite=""; 
		switch(lastmove)
		{
		case "right" :
			opposite="left"; 
			break; 
		case "left":
			opposite ="right"; 
			break;
		case "down":
			opposite ="up"; 
			break;
		case "up":
			opposite = "down"; 
			break;
		}
		String direction=""; // the direction that had the lowestSum
		for(int i = 0 ; i<options.size(); i++)
		{
			if(options.get(i)!=opposite)
			{
				if(lowestSum==-1)//first try
				{
					lowestSum = visualize(zeroi,zeroy,options.get(i)); 
					direction = options.get(i); // sets the direction 
				}else if(visualize(zeroi,zeroy,options.get(i))<lowestSum)
				{
					lowestSum = visualize(zeroi,zeroy,options.get(i)); 
					direction = options.get(i); // sets the direction 
				}
			}
			
		}
		// now direction should be the direction with the lowest h(n)
		lastmove = direction; 
		System.out.println("h(n): "+lowestSum);
		System.out.println(direction); // add direction to the list; 
		moveList.add(direction); 
		// actually change the table '
		//for the console version
		
		move(zeroi,zeroy,direction); 
		for(int i= 0 ; i<table.length; i++)
		{
			for(int y=0; y<table[i].length; y++)
			{
				System.out.print(table[i][y]+ "  ");
			}
			System.out.println();
		}
		
		return lowestSum; 
	}
	public static int seeOptions()
	{
		ArrayList<String> options = new ArrayList<String>(); 
		
		int zeroi=2, zeroy=2; 
		for(int i= 0 ; i<table.length; i++)
		{
			for(int y=0; y<table[i].length; y++)
			{
				if(table[i][y]==0)
				{
					zeroi=i; 
					zeroy =y ; // finding the 0 position 
				}
			}
		}
		
		switch(zeroi)
		{
		case 0://top
			switch(zeroy)
			{
			case 0://top left 
				//allowed: up/left
				options.add("up"); 
				options.add("left"); 
				break; 
			case 1://top middle
				//allowed: up/right/left
				options.add("up"); 
				options.add("right"); 
				options.add("left"); 
				break; 
			case 2: //top right
				//allowed: up/right
				options.add("up"); 
				options.add("right"); 
				break; 
			}
			break; 
		case 1 : //middle
			switch(zeroy)
			{
			case 0://middle left
				//allowed:  up/down/left
				options.add("up"); 
				options.add("down"); 
				options.add("left");
				break; 
			case 1:// center
				//allowed: up/down/left/right
				options.add("up"); 
				options.add("down"); 
				options.add("right");
				options.add("left");
				break; 
			case 2: //middle right
				//allowed:up/down/right
				options.add("up"); 
				options.add("down"); 
				options.add("right");
				break; 
			}
			break; 
		case 2 ://bottom 
			switch(zeroy)
			{
			case 0://bottom left
				//allowed:down/left
				options.add("down");
				options.add("left"); 
				break; 
			case 1://bottom middle
				//allowed:left/down/right
				options.add("down");
				options.add("right"); 
				options.add("left"); 
				break; 
			case 2: //bottom right 
				//allowed: down/right
				options.add("down");
				options.add("right"); 
				break; 
			}
			break; 
		
		}
		
		return relayOptions(zeroi , zeroy,options); 
		
	}
	public void search(int g, int h )
	{
		// this is going to be a recursive functions that determines the moves of the tiles
		//everytime depth should be incremented 
		
		if(h == 0) 
		{
			System.out.println("DONE");
		}else
		{ 
			
			search(g,h); 
		}
	}

}
