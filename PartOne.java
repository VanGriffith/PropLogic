import java.util.Scanner;

public class PartOne {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        sentence = sentence.replaceAll("\\s", "");
        System.out.println(sentence);

        sc.close();
    }
}

class Clause {
    static final char NOT = '~';
    static final char OPEN = '(';
    static final char CLOSE = ')';
    static final char AND = '&';
    static final char OR = '|';
    static final char IMPLIES = '>';
    static final char EQUALS = '=';
    static final char[] OPERATORS = {AND, OR, IMPLIES, EQUALS};

    String string;
    char operator;
    int operatorIndex;
    Clause a;
    Clause b;

    public Clause(String string) {
        this.string = string;
        String tempString = string.substring(string.indexOf(OPEN) + 1, string.length() - 1);
        
        int parenCount = 0;
        int index = 0;
        boolean operatorFound = false;

        while (!operatorFound) {
            char currentChar = tempString.charAt(index);

            if (currentChar == OPEN)  {
                parenCount++;
            }
            else if (currentChar == CLOSE) {
                parenCount--;
            }
            else if (parenCount == 0) {
                for (char operator: OPERATORS) {
                    if (currentChar == operator) {
                        this.operator = currentChar;
                        operatorFound = true;
                        break;
                    }
                }
            }
        }

        
    }
}