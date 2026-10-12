import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class GameTest {
    public static void main(String[] args) {
        int[][] expected = {{0, -1, 1}, {1, 0, -1}, {-1, 1, 0}};
        for (Main.Move player : Main.Move.values()) {
            for (Main.Move computer : Main.Move.values()) {
                if (Main.outcome(player, computer) != expected[player.ordinal()][computer.ordinal()]) {
                    throw new AssertionError(player + " vs " + computer);
                }
            }
        }
        if (Main.Move.parse("  ROCK ") != Main.Move.ROCK) throw new AssertionError("Normalization");
        if (Main.Move.parse("lizard") != null) throw new AssertionError("Invalid move");
        if (Main.matchTarget(new String[] {"--best-of", "3"}) != 2) throw new AssertionError("Match target");
        try { Main.matchTarget(new String[] {"--best-of", "4"}); throw new AssertionError("Even match accepted"); }
        catch (IllegalArgumentException expectedError) { }
        Random fixed = new Random() { @Override public int nextInt(int bound) { return 0; } };
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Main.Score score = Main.play(new Scanner("invalid\nr\np\np\ns\n"), fixed, new PrintStream(bytes), 2);
        if (!score.equals(new Main.Score(2, 0, 1))) throw new AssertionError("Invalid input, ties, or match ending");
        if (!bytes.toString().contains("You won the match.")) throw new AssertionError("Missing winner");
        if (!Main.play(new Scanner(""), fixed, new PrintStream(bytes), 0).equals(new Main.Score(0, 0, 0))) throw new AssertionError("EOF");
        if (!Main.play(new Scanner("q\np\n"), fixed, new PrintStream(bytes), 2).equals(new Main.Score(0, 0, 0))) throw new AssertionError("Early quit");
        System.out.println("Passed matchups, input validation, best-of matches, ties, quit and EOF.");
    }
}
