import java.util.ArrayList;
import java.util.PriorityQueue;

public class Statement extends Proposition {
    char operator;
    Proposition leftChild;
    Proposition rightChild;


    public Statement(boolean isNegative) {
        super(isNegative);
        this.leftChild = null;
        this.rightChild = null;
    }

    public Statement(Proposition left, char operator, Proposition right) {
        super(false);
        this.setLeftChild(left);
        this.operator = operator;
        this.setRightChild(right);
    }

    public Statement(Proposition left, char operator, Proposition right, boolean isNegative) {
        super(isNegative);
        this.setLeftChild(left);
        this.operator = operator;
        this.setRightChild(right);
        
    }

    /**
     * Add a child to the statement
     * @param newChild the new proposition to be added
     */
    public void addChild(Proposition newChild) {
        if (this.leftChild == null)  {
            this.leftChild = newChild;
        }
        else {
            this.rightChild = newChild;
        }
        newChild.parent = this;
    }

    /**
     * Sets the left child node and updates its parent
     */
    public void setLeftChild(Proposition newChild) {
        this.leftChild = newChild;
        this.leftChild.parent = this;
    }

    /**
     * Sets the right child node and updates its parent
     */
    public void setRightChild(Proposition newChild) {
        this.rightChild = newChild;
        this.rightChild.parent = this;
    }
    /**
     * Removes > and = operators if they exist, replacing them appropriately
     */
    public void removeImplications() {
        if (operator == EQUALS)         { this.biConditional(); }
        else if (operator == IMPLIES)   {  this.implications(); }
    }

    /**
     * Turns P=Q into (~P>Q)&(~Q>P)
     */
    private void biConditional() {
        Statement newLeft = new Statement(this.leftChild.copy().negate(), IMPLIES, this.rightChild.copy());
        Statement newRight = new Statement(this.rightChild.negate(), IMPLIES, this.leftChild);
        setLeftChild(newLeft);
        setRightChild(newRight);
        this.operator = AND;
    }

    /**
     * Turns P>Q into ~P|Q
     */
    private void implications() {
        this.leftChild.negate();
        this.operator = OR;
    }

    /**
     * Applies DeMorgan's law to the current statement, if necessary
     */
    public void applyDeMorgans() {
        if (this.isNegative) {
            this.isNegative = false;
            if (this.operator == AND) this.operator = OR;
            else if (this.operator == OR) this.operator = AND;
            else throw new Error("DeMorgan failure: operator " + this.operator + " found.");

            leftChild.negate();
            rightChild.negate();
        }
    }

    /**
     * Applies the distributive law on a given statement
     * @return true if a change was made, false otherwise
     */
    public boolean applyDistributions() {
        if (this.operator == OR) {
            Statement badChild = null;
            Proposition goodChild = null;
            if (this.leftChild.isStatement() && ((Statement) this.leftChild).operator == AND) {
                badChild = (Statement) this.leftChild;
                goodChild = this.rightChild;
            }
            else if (this.rightChild.isStatement() && ((Statement) this.rightChild).operator == AND) {
                badChild = (Statement) this.rightChild;
                goodChild = this.leftChild;
            }
            else {
                return false;
            }

            Statement newLeft = new Statement(goodChild.copy(), OR, badChild.leftChild.copy());
            Statement newRight = new Statement(goodChild.copy(), OR, badChild.rightChild.copy());
            this.setLeftChild(newLeft);
            this.setRightChild(newRight);
            this.operator = AND;
            return true;
        }
        return false;
    }

    /**
     * Collects clauses throughout the tree, recursively, assuming the tree is in CNF
     */
    public void collectClauses(ArrayList<PriorityQueue<String>> clauses) {
        if (this.operator == AND) {
            this.leftChild.collectClauses(clauses);
            this.rightChild.collectClauses(clauses);
        }
        else if (this.operator == OR) {
            PriorityQueue<String> clause = new PriorityQueue<String>();
            clauses.add(clause);
            this.leftChild.collectClause(clause);
            this.rightChild.collectClause(clause);
            clauses.add(clause);
        }
        else {
            throw new Error("Clause failure: operator " + this.operator + " found.\n");
        }
    }

    /**
     * Collects literals of a clause into an arraylist recursively; should only ever
     * be called on statements with OR operators
     * @param clause - an arraylist 
     */
    public void collectClause(PriorityQueue<String> clause) {
        if (this.operator != OR) {
            throw new Error("Clause failure: OR expected," + this.operator + " found.\n");
        }
        else {
            this.leftChild.collectClause(clause);
            this.rightChild.collectClause(clause);
        }
    }

    /**
     * Creates a copy of the current proposition and its children
     */
    public Proposition copy() {
        return new Statement(this.leftChild.copy(), this.operator, this.rightChild.copy());
    }

    /**
     * Returns if this proposition is a statement (as opposed to a literal)
     */
    public boolean isStatement() {
        return true;
    }
}
