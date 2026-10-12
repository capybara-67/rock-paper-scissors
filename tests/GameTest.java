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
        System.out.println("Passed all nine matchups and input validation.");
    }
}
