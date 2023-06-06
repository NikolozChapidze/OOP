
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	HashMap<T, Set<T>> map;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		map = new HashMap<>();
		for (int i = 0; i < rules.size()-1; i++) {
			T elem = rules.get(i);
			T nextElem = rules.get(i+1);
			if(elem == null || nextElem == null) {
				continue;
			}
			if(!map.containsKey(elem)){
				map.put(elem,new HashSet<>());
			}
			map.get(elem).add(nextElem);
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		 return map.getOrDefault(elem, Collections.emptySet());
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			T elem = list.get(i);
			T nextElem = list.get(i+1);
			if(elem != null && nextElem != null && map.getOrDefault(elem, Collections.emptySet()).contains(nextElem)){
				list.remove(i+1);
				i--;
			}
		}
	}
}
