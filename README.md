# Rock, Paper, Scissors

A small Java terminal game with input validation, a running scoreboard, and correct outcomes for all nine player/computer combinations.

## Run

Requires JDK 17 or newer.

```sh
mkdir -p build
javac -d build src/Main.java
java -cp build Main
```

Add `--best-of 3` or `--best-of 5` to play a match. The first player to reach a majority of wins takes the match; ties extend it. `N` must be odd and between 1 and 99.

```sh
java -cp build Main --best-of 5
```

Enter `r`, `p`, or `s`, or spell out the move. Enter `q` to quit. Invalid input leaves the score unchanged; end-of-file exits cleanly.

## Test

```sh
javac -d build src/Main.java tests/GameTest.java
java -cp build GameTest
```

The tests check every matchup, input normalization, best-of match endings, ties, early quit, and end-of-file. The rule function is separate from random computer selection and terminal input so its behavior can be checked without relying on chance.
