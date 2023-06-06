import junit.framework.TestCase;

import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}

	public void testSameCount3() {
		Integer[] a = new Integer[]{1,2,3,1,1,0,2};
		assertEquals(0, Appearances.sameCount(Arrays.asList(a), Arrays.asList(0, 0, 6, 9, 1)));
	}

	public void testSameCount4(){
		assertEquals(0,Appearances.sameCount(Arrays.asList(), Arrays.asList()));
		assertEquals(0,Appearances.sameCount(Arrays.asList(), Arrays.asList(1,2,3)));
		assertEquals(0,Appearances.sameCount(Arrays.asList(1,1,1), Arrays.asList(1,2,3)));
	}

	public void testSameCount5(){
		assertEquals(0,Appearances.sameCount(Arrays.asList(1,2,3), Arrays.asList("1","2","3")));
		assertEquals(0,Appearances.sameCount(Arrays.asList('1','2','3'), Arrays.asList(1,2,3)));
		assertEquals(1,Appearances.sameCount(Arrays.asList(1.0,2.0,3.0,1.0), Arrays.asList(1.0,1.0,"2","4")));
	}

	public void testSameCount6(){
		assertEquals(1,Appearances.sameCount(Arrays.asList(Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(2)),
														Arrays.asList(Arrays.asList(1),Arrays.asList(2),Arrays.asList(2))));
		assertEquals(0,Appearances.sameCount(Arrays.asList('1','2','3'), Arrays.asList(1,2,3)));
		assertEquals(1,Appearances.sameCount(Arrays.asList(1.0,2.0,3.0,1.0), Arrays.asList(1.0,1.0,"2","4")));
	}

	public void testSameCount7(){
		assertEquals(1,Appearances.sameCount(Arrays.asList(new Point(1.2,3.4)),Arrays.asList(new Point(1.2,3.4))));
		assertEquals(2,Appearances.sameCount(Arrays.asList(new Point(1.2,3.4),new Point(0.0,0.0)),
														Arrays.asList(new Point(1.2,3.4),new Point(0.0,0.0),new Point(9,0.9))));
	}
}
