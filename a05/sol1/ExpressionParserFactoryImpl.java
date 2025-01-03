package a05.sol1;

import java.util.Map;
import java.util.function.BiFunction;

public class ExpressionParserFactoryImpl implements ExpressionParserFactory {

    static enum Token {
        NUM, OPEN, CLOSE, SUM, END;
    }

    private <S> ExpressionParser fromFunction(BiFunction<S,Token,S> function, S initial){
        return new ExpressionParser() {
            private S state = initial;

            private void accept(Token token){
                var res = function.apply(state,token);
                if (res != null){
                    state = res;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void acceptNumber(int n) {
                accept(Token.NUM);
            }

            @Override
            public void acceptSum() {
                accept(Token.SUM);
            }

            @Override
            public void acceptOpenParen() {
                accept(Token.OPEN);
            }

            @Override
            public void acceptCloseParen() {
                accept(Token.CLOSE);
            }

            @Override
            public void close() {
                accept(Token.END);
            }
        };
    }

    private <S> ExpressionParser fromAutomaton(Map<Pair<S,Token>,S> transition, S initial){
        return fromFunction((s,t) -> transition.get(new Pair<>(s,t)), initial);
    }


    @Override
    public ExpressionParser oneSum() {
        enum State { INIT, NUM, OP, COMPLETE, FINAL }
        return fromAutomaton(Map.of(
            new Pair<>(State.INIT, Token.NUM), State.NUM,
            new Pair<>(State.NUM, Token.SUM), State.OP,
            new Pair<>(State.OP, Token.NUM), State.COMPLETE,
            new Pair<>(State.COMPLETE, Token.END), State.FINAL
        ), State.INIT);
    }

    @Override
    public ExpressionParser zeroOrManySums() {
        enum State { INIT, NUM, FINAL }
        return fromAutomaton(Map.of(
            new Pair<>(State.INIT, Token.NUM), State.NUM,
            new Pair<>(State.NUM, Token.END), State.FINAL,
            new Pair<>(State.NUM, Token.SUM), State.INIT
        ), State.INIT);
    }
    
   
    @Override
    public ExpressionParser oneLevelParens() {
        enum State { INIT, NUM, FINAL, PAR, PARNUM }
        return fromAutomaton(Map.of(
            new Pair<>(State.INIT, Token.NUM), State.NUM,
            new Pair<>(State.NUM, Token.END), State.FINAL,
            new Pair<>(State.NUM, Token.SUM), State.INIT,
            new Pair<>(State.INIT, Token.OPEN), State.PAR,
            new Pair<>(State.PAR, Token.NUM), State.PARNUM,
            new Pair<>(State.PARNUM, Token.CLOSE), State.NUM,
            new Pair<>(State.PARNUM, Token.SUM), State.PAR
        ), State.INIT);
    }

    @Override
    public ExpressionParser manySumsWithBoxingParens() {
        enum State { LEFT, RIGHT, FINAL }
        return this.<Pair<State,Integer>>fromFunction((p,t) -> 
            switch (p.getX()){
                case LEFT -> switch(t){
                    case NUM -> new Pair<>(State.RIGHT,p.getY());
                    case OPEN -> new Pair<>(State.LEFT,p.getY()+1);
                    default -> null;
                };
                case RIGHT -> switch(t){
                    case CLOSE -> p.getY()>0 ? new Pair<>(State.RIGHT,p.getY()-1) : null;
                    case SUM -> p.getY()==0 ? new Pair<>(State.LEFT,0) : null;
                    case END -> p.getY()==0 ? new Pair<>(State.FINAL,0) : null;
                    default -> null;
                }; 
                default -> null;
            }, new Pair<>(State.LEFT,0)
        );
    }
}
