package a06.e1;

/**
 * This interface models the parser of a specific infinite sequence of values of type T.
 * 
 * This is achieved by calling an accept method that takes the first element in the sequence. 
 * This has a fluent interface, namely, it returns the next Parser where the next accept has 
 * to be called for the second element, and so on. The next Parser may or may not be a new one: 
 * e.g. it could be 'this' if the implementation is coherent with that.
 *  
 * To see if sequence 0,1,2,3,4,5,... is parsed correctly you have to:
 * - create a new parser
 * - on it, call accept(0), and get a new Parser 
 * - on it, call accept(1), and get a new Parser
 * - on it, call accept(2)
 * - ...
 * 
 * If you call an accept method that does not correspond to the sequence to be parsed 
 * (e.g., calling accept(0) and then accept(2) in the case above), you get an InvalidStateException.
 * At that point, the state of the parser is compromised, and the object is assumed not to be used further.
 *   
 */
public interface FluentParser<T> {

   /**
    * @param value, tries to accept value t
    * @throws IllegalStateExcpetion if it cannot be accepted
    * @return the new parser to be called for the next elements
    */ 
   FluentParser<T> accept(T value);    
}
