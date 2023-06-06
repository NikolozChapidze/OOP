import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int result = 0;
		int current = 1;
		for (int i = 0; i < str.length()-1; i++) {
			if(str.charAt(i) == str.charAt(i+1)){
				current++;
			}else{
				result = Math.max(current,result);
				current = 1;
			}
		}
		return result;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		if(str.length() == 0){
			return "";
		}
		String result = "";
		for (int i = 0; i < str.length()-1; i++) {
			char previous = str.charAt(i);
			char nextOne = str.charAt(i+1);
			if(previous>='0' && previous<='9'){
				for (int j = 0; j < Character.getNumericValue(previous); j++) {
					result+=nextOne;
				}
			}else{
				result+=previous;
			}
		}
		char last = str.charAt(str.length()-1);
		if(last < '0' || last>'9'){
			result+=last;
		}
		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		HashSet<String> substrings = new HashSet<>();
		for(int i = 0; i <= a.length() - len; i++){
			substrings.add(a.substring(i,i+len));
		}
		for (int i = 0; i <= b.length() - len; i++) {
			if(substrings.contains(b.substring(i,i+len))){
				return true;
			}
		}
		return false;
	}
}
