import java.io.File;
import java.util.Scanner;
import java.util.*;

/* You are allowed (and expected!) to use either Java's ArrayDeque or LinkedList class to make stacks and queues,
 * and Java's PriorityQueue class to make a priority queue */

public class CookieMonster {

    private int [][] cookieGrid;
    private int numRows;
    private int numCols;
    
    private ArrayDeque <OrphanScout> orphans = new ArrayDeque<OrphanScout>();
    private PriorityQueue <OrphanScout> orphanPQ = new PriorityQueue<OrphanScout>();
    
    //Constructs a CookieMonster from a file with format:
    //numRows numCols
    //<<rest of the grid, with spaces in between the numbers>>
    public CookieMonster(String fileName) {
		int row = 0;
		int col = 0;
		try
		{
			Scanner input = new Scanner(new File(fileName));

			numRows    = input.nextInt();  
			numCols    = input.nextInt();
			cookieGrid = new int[numRows][numCols];

			for (row = 0; row < numRows; row++) 
				for (col = 0; col < numCols; col++)
					cookieGrid[row][col] = input.nextInt();
			
			input.close();
		}
		catch (Exception e)
		{
			System.out.print("Error creating maze: " + e.toString());
			System.out.println("Error occurred at row: " + row + ", col: " + col);
		}

    }

    public CookieMonster(int [][] cookieGrid) {
        this.cookieGrid = cookieGrid;
        this.numRows    = cookieGrid.length;
        this.numCols    = cookieGrid[0].length;
    }

	/* RECURSIVELY calculates the route which grants the most cookies.
	 * Returns the maximum number of cookies attainable. */
	public int recursiveCookies() {
		return recursiveCookiesHelper(0, 0);
	}
	
	// Recursively calculates the route which grants the most cookies based on the initial position.
	public int recursiveCookiesHelper(int r, int c) {
		if(!isPossible(r+1, c) && !isPossible(r, c+1))
			return cookieGrid[r][c];
		else
		{
			if(!isPossible(r+1, c))
				return(cookieGrid[r][c] + recursiveCookiesHelper(r, c+1));
			else if(!isPossible(r, c+1))
				return(cookieGrid[r][c] + recursiveCookiesHelper(r+1, c));
			else
				return (cookieGrid[r][c] + max(recursiveCookiesHelper(r+1, c), recursiveCookiesHelper(r, c+1)));
		}
	}
	
	public int max(int a, int b)
	{
		if(a >= b)
			return a;
		return b;
	}
	
	public boolean isPossible(int row, int col)
	{
		if(row == numRows || col == numCols)
			return false;
		else if(cookieGrid[row][col] == -1)
			return false;
		return true;
	}

	/* Calculate which route grants the most cookies using a QUEUE.
	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
    public int queueCookies() {
    	int maxCookies = cookieGrid[0][0];
    	int r, c;
    	orphans.add(new OrphanScout(0, 0, cookieGrid[0][0]));
    	while(orphans.size() > 0)
    	{
    		for(int i = 0; i < orphans.size(); i++)
    		{
        		r = orphans.getFirst().getEndingRow();
        		c = orphans.getFirst().getEndingCol();
    			if(isPossible(r+1, c))
    			{
    				orphans.add(new OrphanScout(r + 1, c, orphans.getFirst().getCookiesDiscovered() + cookieGrid[r+1][c]));
    			}
    			if(isPossible(r, c + 1))
    			{
    				orphans.add(new OrphanScout(r, c + 1, orphans.getFirst().getCookiesDiscovered()+ cookieGrid[r][c+1]));
    			}
    			if(orphans.getFirst().getCookiesDiscovered() > maxCookies)
    			{
    				maxCookies = orphans.getFirst().getCookiesDiscovered();
    			}
    			orphans.removeFirst();
    		}
    	}
    	return maxCookies;
    }

    /* Calculate which route grants the most cookies using a stack.
 	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
    public int stackCookies() {
    	int maxCookies = cookieGrid[0][0];
    	int r, c;
    	orphans.add(new OrphanScout(0, 0, cookieGrid[0][0]));
    	while(orphans.size() > 0)
    	{
    		for(int i = 0; i < orphans.size(); i++)
    		{
        		r = orphans.getLast().getEndingRow();
        		c = orphans.getLast().getEndingCol();
    			if(isPossible(r+1, c))
    			{
    				orphans.addFirst(new OrphanScout(r + 1, c, orphans.getLast().getCookiesDiscovered() + cookieGrid[r+1][c]));
    			}
    			if(isPossible(r, c+1))
    			{
    				orphans.addFirst(new OrphanScout(r, c + 1, orphans.getLast().getCookiesDiscovered()+ cookieGrid[r][c+1]));
    			}
    			if(orphans.getLast().getCookiesDiscovered() > maxCookies)
    			{
    				maxCookies = orphans.getLast().getCookiesDiscovered();
    			}
    			orphans.removeLast();
    		}
    	}
    	return maxCookies;
    }

    /* Calculate which route grants the most cookies using a priority queue.
	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
    public int pqCookies() {
    	OrphanScout o;
    	int maxCookies = cookieGrid[0][0];
    	int r, c;
    	orphanPQ.add(new OrphanScout(0, 0, cookieGrid[0][0]));
    	while(orphanPQ.size() > 0)
    	{
    		for(int i = 0; i < orphanPQ.size(); i++)
    		{
    			o = orphanPQ.remove();
        		r = o.getEndingRow();
        		c = o.getEndingCol();
    			if(isPossible(r+1, c))
    			{
    				orphanPQ.add(new OrphanScout(r + 1, c, o.getCookiesDiscovered() + cookieGrid[r+1][c]));
    			}
    			if(isPossible(r, c + 1))
    			{
    				orphanPQ.add(new OrphanScout(r, c + 1, o.getCookiesDiscovered()+ cookieGrid[r][c+1]));
    			}
    			if(o.getCookiesDiscovered() > maxCookies)
    			{
    				maxCookies = o.getCookiesDiscovered();
    			}
    		}
    	}
    	return maxCookies;
    }
    
    public int runPath(int[] path, int[][] grid) {
    	int cookies = 0;
    	int r = 0;
    	int c = 0;
    	for(int i = 0; i < path.length; i++)
    	{
    		if(path[i] == -1)
    			return cookies;
    		else if(path[i] == 0)
    		{
    			r++;
    			cookies += grid[r][c];
    		}
    		else
    		{
    			c++;
    			cookies += grid[r][c];
    		}
    	}
    	return cookies;
    }


}
