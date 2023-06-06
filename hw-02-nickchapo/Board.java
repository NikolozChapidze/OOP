// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private final int width;
	private final int height;
	private boolean[][] grid;
	private boolean[][] backupGrid;
	private final boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] backupWidths;
	private int[] heights;
	private int[] backupHeights;
	private  int maxHeights;
	private  int backupMaxHeights;


	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;

		maxHeights = 0;
		heights = new int[width];
		widths	= new int[height];

		backupMaxHeights	= maxHeights;
		backupHeights		= new int[width];
		backupWidths		= new int[height];
		backupGrid			= new boolean[width][height];
	}


	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return maxHeights;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int[] debugHeights = new int[width];
			int[] debugWidths = new int[height];
			int debugMaxHeight = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(grid[x][y]){
						debugWidths[y]++;
						debugHeights[x] = Math.max(debugHeights[x], y+1);
						debugMaxHeight = Math.max(debugMaxHeight,debugHeights[x]);
					}
				}
			}
			if(debugMaxHeight != maxHeights){
				throw new RuntimeException("max height is incorrect");
			}
			if(!Arrays.equals(debugHeights,heights)){
				throw new RuntimeException("heights of grid is incorrect");
			}
			if(!Arrays.equals(debugWidths,widths)){
				throw new RuntimeException("widths of grid is incorrect");
			}
		}

	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int y = 0;
		for (int i = 0; i < piece.getWidth(); i++) {
			y = Math.max(heights[x+i] - piece.getSkirt()[i],y);
		}
		return y;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		return !inBounds(x,y) || grid[x][y];
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed = false;
		backup();
		int result = PLACE_OK;

		for (TPoint tpoint : piece.getBody()) {
			int newX = tpoint.x + x;
			int newY = tpoint.y + y;
			if(!inBounds(newX, newY)){
				return PLACE_OUT_BOUNDS;
			}
			if(grid[newX][newY]){
				return PLACE_BAD;
			}
			grid[newX][newY] = true;
			widths[newY]++;
			heights[newX] = Math.max(heights[newX], newY + 1);
			maxHeights = Math.max(maxHeights, heights[newX]);
			if(widths[newY] == width){
				result =  PLACE_ROW_FILLED;
			}
		}
		return result;
	}

	private boolean inBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed){
			committed = false;
			backup();
		}
		int rowsCleared = 0;
		int to = 0;
		for (int from = 0; from < height; from++) {
			while (from < height && width == widths[from]){
				from++;
				rowsCleared++;
			}
			widths[to] = widths[from];
			for (int col = 0; col < width; col++) {
				grid[col][to] = grid[col][from];
			}
			to++;
		}
		while(to < height){
			widths[to] = 0;
			for (int i = 0; i < width; i++) {
				grid[i][to] = false;
			}
			to++;
		}
		maxHeights = maxHeights - rowsCleared;
		Arrays.fill(heights,0);
		for (int j = maxHeights - 1; j >= 0; j--) {
			for (int i = 0; i < width; i++) {
				if (grid[i][j]) {
					if (j + 1 > heights[i]) heights[i] = j + 1;
				}
			}
		}
		sanityCheck();
		return rowsCleared;
	}

	private void backup(){
		backupMaxHeights = maxHeights;
		System.arraycopy(heights, 0, backupHeights, 0, width);
		System.arraycopy(widths, 0, backupWidths, 0, height);
		for(int i = 0 ; i < width; i++) {
			System.arraycopy(grid[i], 0, backupGrid[i], 0, height);
		}
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if (!committed) {
			committed = true;
			maxHeights = backupMaxHeights;
			int[] theights = heights;
			heights = backupHeights;
			backupHeights = theights;
			int[] twidths = widths;
			widths = backupWidths;
			backupWidths = twidths;
			boolean[][] tgrid = grid;
			grid = backupGrid;
			backupGrid = tgrid;
		}
	}

	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


