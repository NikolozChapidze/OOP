import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s1, s1Rotated;
	private Piece s2, s2Rotated;
	private Piece l1, l2, l3, l4;
	private Piece lr1, lr2, lr3, lr4;
	private Piece square;
	private Piece stick1, stick2;

	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.

	protected void setUp() throws Exception {
		b = new Board(5, 6);

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

		b.place(pyr1, 0, 0);
	}

	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}

	// Place sRotated into the board, then check some measures
	public void testPlace() {
		b.commit();
		int result = b.place(s1Rotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		b.commit();
		int result2 = b.place(stick1,0,1);
		assertEquals(5,b.getColumnHeight(0));

		b.commit();
		int result3 = b.place(square, 3,0);
		assertEquals(2,b.getColumnHeight(3));
		assertEquals(2,b.getColumnHeight(4));
	}

	public void testUndo(){
		b.commit();
		b.place(pyr1, 0, 0);
		b.commit();
		b.place(s1Rotated, 1, 1);
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		b.undo();
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		b.undo();
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
	}

	public void testClearRows(){
		b = new Board(3, 10);
		b.commit();
		b.place(pyr1,0,b.dropHeight(pyr1,0));
		b.clearRows();
		assertEquals(1, b.getColumnHeight(1));
		b.clearRows();
		assertEquals(1, b.getColumnHeight(1));

		b.commit();
		b.place(s2Rotated,0,b.dropHeight(s2Rotated,0));
		b.clearRows();
		b.commit();
		b.place(s2Rotated,0,b.dropHeight(s2Rotated,0));
		b.commit();
		b.place(stick1,2, b.dropHeight(stick1,2));

		b.commit();
		assertEquals(4,b.getColumnHeight(0));
		assertEquals(5,b.getColumnHeight(1));
		assertEquals(4,b.getColumnHeight(2));

		b.clearRows();
		assertEquals(0,b.getColumnHeight(0));
		assertEquals(1,b.getColumnHeight(1));
		assertEquals(0,b.getColumnHeight(2));

		b.commit();
		b.place(square,1,b.dropHeight(square,1));
		b.commit();
		b.place(stick1,0,b.dropHeight(stick1,0));
		b.commit();
		b.clearRows();
		assertEquals(2,b.getColumnHeight(0));
		assertEquals(1,b.getColumnHeight(1));
		assertEquals(0,b.getColumnHeight(2));
		assertEquals(2,b.getMaxHeight());

	}

	public void testFinal(){
		b = new Board(4, 6);
		b.place(square,0,b.dropHeight(square,0));
		b.commit();
		b.place(square,0,b.dropHeight(square,0));
		b.commit();
		b.place(square,2,b.dropHeight(square,2));
		b.commit();
		b.place(square,2,b.dropHeight(square,2));
		assertEquals(4,b.getColumnHeight(0));
		assertEquals(4,b.getColumnHeight(1));
		assertEquals(4,b.getColumnHeight(2));
		assertEquals(4,b.getColumnHeight(3));
		b.commit();
		b.place(stick2,0 ,b.dropHeight(stick2,0));
		assertEquals(4,b.getRowWidth(4));
		b.undo();
		b.commit();
		assertEquals(0,b.getRowWidth(4));
		b.place(s1, 0, b.dropHeight(s1,0));
		System.out.println(b.toString());
		b.commit();
		assertEquals(2,b.getRowWidth(4));
		b.undo();
		assertEquals(2,b.getRowWidth(4));

		assertEquals(6,b.getColumnHeight(1));
		assertEquals(6,b.getMaxHeight());

		b.clearRows();
		b.commit();

		System.out.println(b.toString());
		assertEquals(2,b.getColumnHeight(2));
		assertEquals(2, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getMaxHeight());

		b.place(l4,0,b.dropHeight(l4,0));
		b.commit();
		b.place(stick1,3,b.dropHeight(stick1,3));
		b.commit();
		b.clearRows();
		b.commit();
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		System.out.println(b.toString());

		assertEquals(2,b.getMaxHeight());
	}
}
