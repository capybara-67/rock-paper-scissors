# Rock, Paper, Scissors

A small Java terminal game with input validation, a running scoreboard, and correct outcomes for all nine player/computer combinations.

## Run

Requires JDK 17 or newer.

```sh
mkdir -p build
javac -d build src/Main.java
java -cp build Main
```

Enter `r`, `p`, or `s`, or spell out the move. Enter `q` to quit. Invalid input leaves the score unchanged; end-of-file exits cleanly.

## Test

```sh
javac -d build src/Main.java tests/GameTest.java
java -cp build GameTest
```

The tests check every matchup and input normalization. The rule function is separate from random computer selection and terminal input so its behavior can be checked without relying on chance.
