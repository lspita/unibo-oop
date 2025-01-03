package a06.e1;

import java.util.List;
import java.util.function.UnaryOperator;

/** 
 * An interface modelling a factory of several FluentParsers, which are all deterministic,
 * in the sense that each parser accepts precisely one sequence.
 */
public interface FluentParserFactory {

    /**
     * @return a parser accepting 0,1,2,3,4,...
     */
    FluentParser<Integer> naturals();

    /**
     * @return a parser accepting lists (),(0),(0,1),(0,1,2),...
     */
    FluentParser<List<Integer>> incrementalNaturalLists(); 

    /**
     * @return a parser accepting 0,0,1,0,1,2,0,1,2,3,0,...
     */
    FluentParser<Integer> repetitiveIncrementalNaturals(); 

    /**
     * @param s, the initial string
     * @return (assuming s is "a") a parser accepting "a","a","aa","a","aa","aaa","a",...
     */
    FluentParser<String> repetitiveIncrementalStrings(String s);

    /**
     * @param i0, the initial int
     * @param op, an iteration function starting at i0
     * @param s, a string to populate lists of strings
     * @return (assuming i0=0, op=i->i+2, s="a") a parser accepting pairs <0,()>,<2,(a,a)>,<4,(a,a,a,a)>...
     */
    FluentParser<Pair<Integer,List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s);

}
