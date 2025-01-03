package a05.sol1;

/**
 * This interface models the parser of a mathematical expression, like: (1+2)+3.
 * 
 * This is achieved by calling accept methods in the right order, and method close at the end.
 * To see if (1+2)+3 is parsed correctly you have to:
 * - create a new parser
 * - call acceptOpenParen()
 * - call acceptNumber(1)
 * - call acceptSum()
 * - call acceptNumber(2)
 * - call acceptCloseParen()
 * - call acceptSum()
 * - call acceptNumber(3)
 * - call close()
 * 
 * If you call accept methods in the wrong order (e.g., two consecutive sums), or if
 * you call close at the wrong moment (e.g. after a sum), you get an InvalidStateException
 * and that call is ignored: you can proceed calling some other methods.  
 */
public interface ExpressionParser {

   /**
    * @param n, tries to accept number n
    * @throws IllegalStateExcpetion if it cannot be accepted now
    */
   void acceptNumber(int n);

   /**
    * tries to accept a sum symbol
    * @throws IllegalStateExcpetion if it cannot be accepted now 
    */
   void acceptSum();

   /**
    * tries to accept an open parenthesis symbol
    * @throws IllegalStateExcpetion if it cannot be accepted now 
    */
   void acceptOpenParen();

   /**
    * tries to accept a close parenthesis symbol
    * @throws IllegalStateExcpetion if it cannot be accepted now 
    */
   void acceptCloseParen();

   /**
    * tries to conclude parsing
    * @throws IllegalStateExcpetion if it cannot be closed now 
    */
    void close();
    
}
