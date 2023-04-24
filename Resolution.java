import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Resolution {

    static ArrayList<ArrayList<String>> clauses;

    public static void main(String[] args) {
        clauses = new ArrayList<ArrayList<String>>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            clauses.add(new ArrayList<String>(Arrays.asList(sc.nextLine().split(","))));
        }
        sc.close();

        printClauses();

        for (int i = 0; i < clauses.size() - 1; i++) {

        }
    }

    public static void printClauses() {
        for (ArrayList<String> clause: clauses) {
            String cString = "";
            for (String literal: clause) {
                cString += "," + literal;
            }
            System.out.println(cString.substring(1));
        }
    }
}
