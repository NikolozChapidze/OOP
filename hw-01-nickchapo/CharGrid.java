// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int startX = Integer.MAX_VALUE, startY = Integer.MAX_VALUE;
		int endX = Integer.MIN_VALUE, endY = Integer.MIN_VALUE;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == ch){
					if(startX > j) { startX = j;}
					if(startY > i) { startY = i;}
					if(endX < j) { endX = j;}
					if(endY < i) { endY = i;}
				}
			}
		}
		if(startX != Integer.MAX_VALUE){
			return (1 + endX - startX) * (1 + endY - startY);
		}
		return 0;
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public boolean inBound(int y, int x){
		if(y < 0 || y >= grid.length){
			return false;
		}
		if(x < 0 || x >= grid[y].length){
			return false;
		}
		return true;
	}

	public int legthOfSameChar(int y, int x, int yStep, int xStep, char ch){
		y = y + yStep;
		x = x + xStep;
		if(inBound(y, x) && grid[y][x] == ch){
			return 1 + legthOfSameChar(y,x,yStep,xStep,ch);
		}
		return 0;
	}

	public boolean checkPlus(int y, int x){
		char ch = grid[y][x];
		int back = legthOfSameChar(y,x, 0, -1,ch);
		int front = legthOfSameChar(y,x, 0, 1,ch);
		int up = legthOfSameChar(y,x, 1, 0,ch);
		int down = legthOfSameChar(y,x, -1, 0,ch);

		return up!=0 && up == down && up == back && up == front;
	}

	public int countPlus() {
		int result = 0;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				if(checkPlus(row,col)) {
					result++;
				}
			}
		}
		return  result;
	}
	
}
