import java.util.Scanner;

public class PartOne {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        sentence = sentence.replaceAll("\\s", "");
        System.out.println(sentence);

        sc.close();
    }

    static String biconditional(String sentence) {
        if (!sentence.contains("=")) return sentence;
        
        String left = "(" + sentence.substring(0, sentence.indexOf("=")) + ")";
        String right = "(" + sentence.substring(sentence.indexOf("=") + 1) + ")";

        sentence = "("+left+">"+right+")^("+right+">"+left+")";
        
        return sentence;
    }
}