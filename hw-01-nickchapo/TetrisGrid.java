//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	private boolean[][] grid;
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for (int row = grid[0].length - 1; row >= 0; row--) {
			boolean isComplate = true;
			for (int col = 0; col < grid.length; col++) {
				if(!grid[col][row]){
					isComplate = false;
					break;
				}
			}
			if(isComplate){
				shiftDown(row);
			}
		}
	}

	private void shiftDown(int row) {
		while(row < grid[0].length){
			replaceWithNextOne(row);
			row++;
		}
	}

	private void replaceWithNextOne(int row) {
		for(int col = 0; col < grid.length; col++){
			if(row != grid[0].length-1){
				grid[col][row] = grid[col][row+1];
			}else{
				grid[col][row] = false;
			}
		}
	}

	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
