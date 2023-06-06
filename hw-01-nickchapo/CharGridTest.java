
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	public void testCountPlus1(){
		char[][] grid = new char[][]{
				{'0','p','0','x','0','0'},
				{'p','p','p','x','1','1'},
				{'0','p','0','x','0','0'},
				{'1','0','1','x','1','1'},
				{'z','p','z','x','z','z'},
				{'k','p','k','x','k','k'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(1,cg.countPlus());
	}

	public void testCountPlus2(){
		char[][] grid = new char[][]{
				{'0','p','0','x','0','0'},
				{'p','z','p','x','1','1'},
				{'0','p','0','x','0','0'},
				{'1','0','1','x','1','1'},
				{'z','p','z','x','z','z'},
				{'k','p','k','x','k','k'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0,cg.countPlus());
	}

	public void testCountPlus3(){
		char[][] grid = new char[][]{
				{'0','0','0','0','0','0'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0,cg.countPlus());
	}

	public void testCountPlus4(){
		char[][] grid = new char[][]{
				{'0'},
				{'0'},
				{'0'},
				{'0'},
				{'0'},
				{'0'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0,cg.countPlus());
	}

	public void testCountPlus5(){
		char[][] grid = new char[][]{
				{'0','p','0'},
				{'p','p','p'},
				{'0','p','0'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(1,cg.countPlus());
	}
	public void testCountPlus6(){
		char[][] grid = new char[][]{
				{'p','p','p','p','p'},
				{'p','p','p','p','p'},
				{'p','p','p','p','p'},
				{'p','p','p','p','p'},
				{'p','p','p','p','p'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(1,cg.countPlus());
	}
	public void testCountPlus7(){
		char[][] grid = new char[][]{
				{'0','p','0','k','z','l','l'},
				{'p','p','p','k','z','l','l'},
				{'0','p','z','z','z','z','z'},
				{'0','x','0','k','z','l','l'},
				{'p','x','p','k','z','l','l'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(2,cg.countPlus());
	}

	public void testCountPlus10(){
		char[][] grid = new char[][]{
				{'p'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0,cg.countPlus());
	}

	
	
}
