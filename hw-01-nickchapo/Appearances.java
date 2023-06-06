import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int result = 0;
		HashMap<T,Integer> frequencyInA = new HashMap<>();
		HashMap<T,Integer> frequencyInB = new HashMap<>();
		for(T elem : a){
			frequencyInA.put(elem, frequencyInA.getOrDefault(elem,0)+1);
		}
		for(T elem : b){
			frequencyInB.put(elem, frequencyInB.getOrDefault(elem,0)+1);
		}
		for (T key : frequencyInA.keySet()) {
			if(frequencyInA.get(key).equals(frequencyInB.get(key))){
				result++;
			}
		}
		return result;
	}
	
}
