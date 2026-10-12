import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/** A terminal game with validated input and a score for the current visit. */
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
    // Positive means player wins; negative means computer wins; zero is a tie.
    static int outcome(Move player, Move computer) {
        if (player == computer) return 0;
        return (player == Move.ROCK && computer == Move.SCISSORS)
                || (player == Move.PAPER && computer == Move.ROCK)
                || (player == Move.SCISSORS && computer == Move.PAPER) ? 1 : -1;
    }
    public static void main(String[] args) {
        Random random = new Random();
        int wins = 0, losses = 0, ties = 0;
        System.out.println("Rock, paper, scissors. Enter r, p, s, or q to quit.");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Your move: ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) break;
                Move player = Move.parse(input);
                if (player == null) {
                    System.out.println("Choose rock (r), paper (p), or scissors (s).");
                    continue;
                }
                Move computer = Move.values()[random.nextInt(Move.values().length)];
                System.out.println("Computer: " + computer.name().toLowerCase(Locale.ROOT));
                int result = outcome(player, computer);
                if (result > 0) { wins++; System.out.println("You win."); }
                else if (result < 0) { losses++; System.out.println("Computer wins."); }
                else { ties++; System.out.println("Tie."); }
                System.out.printf("Score — wins: %d, losses: %d, ties: %d%n", wins, losses, ties);
            }
        }
        System.out.printf("Final score — wins: %d, losses: %d, ties: %d%n", wins, losses, ties);
    }
}
