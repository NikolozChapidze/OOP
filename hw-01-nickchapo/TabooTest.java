// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    public void testNoFollow1(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("a", "b", "c", "a", null ,"d",null, "a", "c"));

        Set<String> testRes1 = new HashSet<String>(Arrays.asList("b", "c"));
        Set<String> testRes2 = new HashSet<String>(Arrays.asList("c"));
        Set<String> testRes3 = new HashSet<String>(Arrays.asList("a"));
        Set<String> testRes4 = new HashSet<String>(Arrays.asList());

        assertEquals(testRes1, testTaboo.noFollow("a"));
        assertEquals(testRes2, testTaboo.noFollow("b"));
        assertEquals(testRes3, testTaboo.noFollow("c"));
        assertEquals(testRes4, testTaboo.noFollow("d"));
    }
    public void testNoFollow2(){
        Taboo<String> testTaboo1 = new Taboo<String>(Arrays.asList("a", "a", "c", null, "a" ,null ,"d", "a",null, "a"));
        Set<String> testRes1 = new HashSet<String>(Arrays.asList("a", "c"));
        assertEquals(testRes1, testTaboo1.noFollow("a"));

        Taboo<String> testTaboo2 = new Taboo<String>(Arrays.asList());
        Set<String> testRes2 = new HashSet<String>(Arrays.asList());
        assertEquals(testRes2, testTaboo2.noFollow("a"));
    }

    public void testReduce1(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("a", "c", "a", "b"));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("a", "c", "b", "x", "c", "a"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("a", "x", "c"));
        assertEquals(reducedList, toReduce);
    }

    public void testReduced2(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("a", "a", "b", "c", "a", null ,"d",null, "a", "c"));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("a", "a", "b", "c", "a", "d", "a", "c"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("a","d","a"));
        assertEquals(reducedList,toReduce);
    }

    public void testReduced3(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList(null, null, "a"));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("a", "b", "c", "a", "d", "a", "c"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("a", "b", "c", "a", "d", "a", "c"));
        assertEquals(reducedList,toReduce);
    }

    public void testReduced4(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList());
        List<String>  toReduce = new ArrayList<String>(Arrays.asList());
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList());
        assertEquals(reducedList,toReduce);
    }

    public void testReduced5(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("f","o","o", null, "b","a","r"));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("g","o","o","g","l"," ","b","a","r"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("g","o","g","l"," ","b","r"));
        assertEquals(reducedList,toReduce);
    }

    public void testReduced6(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("o","o", null));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("o","o","o","o","o"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("o"));
        assertEquals(reducedList,toReduce);
    }

    public void testReduced7(){
        Taboo<String> testTaboo = new Taboo<String>(Arrays.asList("o"));
        List<String>  toReduce = new ArrayList<String>(Arrays.asList("o","o","o","o","o"));
        testTaboo.reduce(toReduce);
        List<String> reducedList = new ArrayList<String>(Arrays.asList("o","o","o","o","o"));
        assertEquals(reducedList,toReduce);
    }
}
