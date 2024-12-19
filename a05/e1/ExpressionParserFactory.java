package a05.e1;


/**
 * A factory of parsers of only certain kinds of expressions.
 * Note that we never care which number gets accepted, if a number can be accepted
 * it can actually be any number. 
 */
public interface ExpressionParserFactory {

    /**
     * @return a parser accepting only expressions of the kind: 
     * N1 + N2
     * that is, one sum of two numbers
     */
    ExpressionParser oneSum();

    /**
     * @return a parser accepting only expressions of the kind: N1 + N2 + ... + Nm (m>=1)
     */
    ExpressionParser zeroOrManySums();

    /**
     * @return a parser accepting only expressions of the kind: 
     * N + (N + N + N) + N + N + (N + N) + ...
     * that is, you can have parenthesis, but not parenthesis inside other parenthesis...
     */
    ExpressionParser oneLevelParens();

    /**
     * @return a parser accepting only expressions of the kind:
     * N + (N) + N + (((N))) + ((N)) + N + ...
     * that is, a number can be boxed inside many level of parenthesis, but this is
     * the only way of using parenthesis
     */
    ExpressionParser manySumsWithBoxingParens();
}
