import java.util.ArrayList;

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

    /**
     * Marks the thang as negative if positive and vice versa
     * @return a pointer to itself
     */
    public Proposition negate() {
        this.isNegative = !this.isNegative;
        return this;
    }

    /**
     * General Proposition constructor
     * @param isNegative
     */
    public Proposition(boolean isNegative) {
        this.isNegative = isNegative;
        this.parent = null;
    }

    abstract Proposition copy();
    abstract boolean isStatement();
    abstract void collectClause(ArrayList<String> clause);
    abstract void collectClauses(ArrayList<ArrayList<String>> clauses);
}
