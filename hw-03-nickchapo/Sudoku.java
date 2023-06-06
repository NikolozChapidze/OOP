import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	public static final int[][] customGrid = Sudoku.stringsToGrid(
			"860794352",
			"352168749",
			"497253086",
			"218975634",
			"675341928",
			"934682517",
			"526819473",
			"743526891",
			"189437260");
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}

	private int[][] sudoku;
	private int[][] firstResult;
	private boolean wasSolved;
	private long	timeRequired;
	private int 	possibleSolutions;
	private List<Spot> remainingSpots;

	public class Spot implements Comparable<Spot>{
		int row;
		int col;
		int possibleNum;

		public Spot(int row, int col) {
			this.row = row;
			this.col = col;
			this.possibleNum = countPossibleNums();
		}

		private int countPossibleNums() {
			return getPossibleNums().size();
		}

		public HashSet<Integer> getPossibleNums() {
			HashSet<Integer> possible = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			HashSet<Integer> notAccepted = new HashSet<>();
			for (int i = 0; i < sudoku.length; i++) {
				notAccepted.add(sudoku[row][i]);
				notAccepted.add(sudoku[i][col]);
				notAccepted.add(sudoku[(row/3)*3 + i / 3][(col/3)*3 + i % 3]);
			}
			possible.removeAll(notAccepted);
			return possible;
		}

		public void set(int num){
			sudoku[row][col] = num;
		}

		@Override
		public int compareTo(Spot o) {
			return possibleNum - o.possibleNum;
		}
	}
	

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		sudoku = ints;
		firstResult = new int[ints.length][ints.length];
		wasSolved = false;
		timeRequired = 0;
		possibleSolutions = 0;
		fillRemainingSpotsArray();
	}

	public Sudoku(String text){
		this(textToGrid(text));
	}

	private void fillRemainingSpotsArray() {
		remainingSpots = new ArrayList<>();
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if(sudoku[i][j] == 0){
					remainingSpots.add(new Spot(i,j));
				}
			}
		}
		Collections.sort(remainingSpots);
	}


	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long startTime = System.currentTimeMillis();
		solveWrapper(remainingSpots);
		timeRequired = System.currentTimeMillis() - startTime;
		return possibleSolutions;
	}

	private void solveWrapper(List<Spot> leftToFill) {
		if(leftToFill.size() == 1){
			Spot currentSpot = leftToFill.get(0);
			HashSet<Integer> possibleSpotValue = currentSpot.getPossibleNums();
			if(possibleSpotValue.size() != 1){ return; }
			if(!wasSolved){
				currentSpot.set(possibleSpotValue.iterator().next());
				for (int i = 0; i < sudoku.length; i++) {
					for (int j = 0; j < sudoku.length; j++) {
						firstResult[i][j] = sudoku[i][j];
					}
				}
				currentSpot.set(0);
				wasSolved = true;
			}
			possibleSolutions++;
			return;
		}

		Spot currentSpot = leftToFill.get(0);
		HashSet<Integer> possibleSpotValues = currentSpot.getPossibleNums();

		for (int possibleValue : possibleSpotValues) {
			currentSpot.set(possibleValue);
			solveWrapper(leftToFill.subList(1, leftToFill.size()));
		}
		currentSpot.set(0);
	}

	public String getSolutionText() {
		if(!wasSolved) solve();
		if(!wasSolved) return "";
		String result = "";
		for (int i = 0; i < firstResult.length; i++) {
			for (int j = 0; j < firstResult.length; j++) {
				result += firstResult[i][j] + " ";
			}
			result+="\n";
		}
		return result;
	}
	
	public long getElapsed() {
		if(!wasSolved) solve();
		if(!wasSolved) return -1;
		return timeRequired;
	}

}
