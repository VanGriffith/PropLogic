import java.util.ArrayList;
import java.util.PriorityQueue;

abstract class Proposition {
    public static final char NOT = '~';
    public static final char OPEN = '(';
    public static final char CLOSE = ')';
    public static final char AND = '&';
    public static final char OR = '|';
    public static final char IMPLIES = '>';
    public static final char EQUALS = '=';
    public static final char[] OPERATORS = {AND, OR, IMPLIES, EQUALS};
    
    public boolean isNegative;
    public Statement parent;

    public Proposition negate() {
        this.isNegative = !this.isNegative;
        return this;
    }

    public Proposition(boolean isNegative) {
        this.isNegative = isNegative;
        this.parent = null;
    }

    abstract Proposition copy();
    abstract boolean isStatement();
    abstract void collectClause(PriorityQueue<String> clause);
    abstract void collectClauses(ArrayList<PriorityQueue<String>> clauses);
}
