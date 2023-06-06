import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s1, s1Rotated;
	private Piece s2, s2Rotated;
	private Piece l1, l2, l3, l4;
	private Piece lr1, lr2, lr3, lr4;
	private Piece square;
	private Piece stick1, stick2;



	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s1 = new Piece(Piece.S1_STR);
		s1Rotated = s1.computeNextRotation();

		s2 = new Piece(Piece.S2_STR);
		s2Rotated = s2.computeNextRotation();

		l1 = new Piece(Piece.L1_STR);
		l2 = l1.computeNextRotation();
		l3 = l2.computeNextRotation();
		l4 = l3.computeNextRotation();

		lr1 = new Piece(Piece.L2_STR);
		lr2 = lr1.computeNextRotation();
		lr3 = lr2.computeNextRotation();
		lr4 = lr3.computeNextRotation();

		square = new Piece(Piece.SQUARE_STR);

		stick1 = new Piece(Piece.STICK_STR);
		stick2 = stick1.computeNextRotation();

	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());

		assertEquals(2,square.getWidth());
		assertEquals(2,square.getHeight());

		assertEquals(3,s1.getWidth());
		assertEquals(2,s1.getHeight());

		assertEquals(4, stick1.getHeight());
		assertEquals(1, stick1.getWidth());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1Rotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stick2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, stick1.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0,0}, square.getSkirt()));

	}

	public void testEquals(){
		assertTrue(square.equals(square.computeNextRotation()));
		assertTrue(l1.equals(l4.computeNextRotation()));
		assertTrue(s1.equals(s1Rotated.computeNextRotation()));
		assertTrue(s2.equals(s2Rotated.computeNextRotation()));
		assertTrue(lr1.equals(lr4.computeNextRotation()));
		assertTrue(stick1.equals(stick2.computeNextRotation()));
		assertTrue(pyr1.equals(pyr4.computeNextRotation()));
		assertTrue(square.equals(square.computeNextRotation().computeNextRotation()));
	}
	
}
