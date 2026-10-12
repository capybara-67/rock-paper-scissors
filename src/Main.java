import java.io.PrintStream;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    enum Move {
        ROCK, PAPER, SCISSORS;
        static Move parse(String input) {
            return switch (input.trim().toLowerCase(Locale.ROOT)) {
                case "r", "rock" -> ROCK;
                case "p", "paper" -> PAPER;
                case "s", "scissors" -> SCISSORS;
                default -> null;
            };
        }
    }
    record Score(int wins, int losses, int ties) {}

    // Positive means player wins; negative means computer wins; zero is a tie.
    static int outcome(Move player, Move computer) {
        if (player == computer) return 0;
        return (player == Move.ROCK && computer == Move.SCISSORS)
                || (player == Move.PAPER && computer == Move.ROCK)
                || (player == Move.SCISSORS && computer == Move.PAPER) ? 1 : -1;
    }

    static int matchTarget(String[] args) {
        if (args.length == 0) return 0;
        if (args.length != 2 || !args[0].equals("--best-of")) {
            throw new IllegalArgumentException("Usage: Main [--best-of N]");
        }
        int rounds;
        try { rounds = Integer.parseInt(args[1]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("N must be an odd number from 1 to 99."); }
        if (rounds < 1 || rounds > 99 || rounds % 2 == 0) {
            throw new IllegalArgumentException("N must be an odd number from 1 to 99.");
        }
        return rounds / 2 + 1;
    }

    static Score play(Scanner scanner, Random random, PrintStream output, int target) {
        int wins = 0, losses = 0, ties = 0;
        output.println("Rock, paper, scissors. Enter r, p, s, or q to quit.");
        if (target > 0) output.printf("First to %d wins. Ties do not count toward the match.%n", target);
        while (target == 0 || (wins < target && losses < target)) {
            output.print("Your move: ");
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) break;
            Move player = Move.parse(input);
            if (player == null) {
                output.println("Choose rock (r), paper (p), or scissors (s).");
                continue;
            }
            Move computer = Move.values()[random.nextInt(Move.values().length)];
            output.println("Computer: " + computer.name().toLowerCase(Locale.ROOT));
            int result = outcome(player, computer);
            if (result > 0) { wins++; output.println("You win."); }
            else if (result < 0) { losses++; output.println("Computer wins."); }
            else { ties++; output.println("Tie."); }
            output.printf("Score — wins: %d, losses: %d, ties: %d%n", wins, losses, ties);
        }
        if (target > 0 && (wins == target || losses == target)) {
            output.println(wins == target ? "You won the match." : "Computer won the match.");
        }
        output.printf("Final score — wins: %d, losses: %d, ties: %d%n", wins, losses, ties);
        return new Score(wins, losses, ties);
    }

    public static void main(String[] args) {
        final int target;
        try { target = matchTarget(args); }
        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(2);
            return;
        }
        try (Scanner scanner = new Scanner(System.in)) {
            play(scanner, new Random(), System.out, target);
        }
    }
}
