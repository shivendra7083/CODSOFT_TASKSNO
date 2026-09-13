import java.util.Random;
import java.util.Scanner;

/** CodSoft Task 1: Number Game. */
public class NumberGame {
    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        int roundsWon = 0;

        System.out.println("=================================");
        System.out.println("        NUMBER GUESSING GAME");
        System.out.println("=================================");
        System.out.println("Guess a number between 1 and 100.");

        boolean playAgain = true;
        while (playAgain) {
            int number = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts = 0;
            boolean won = false;

            while (attempts < MAX_ATTEMPTS) {
                System.out.print("\nAttempt " + (attempts + 1) + "/" + MAX_ATTEMPTS + ". Enter your guess: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid integer.");
                    scanner.next();
                    continue;
                }

                int guess = scanner.nextInt();
                if (guess < MIN || guess > MAX) {
                    System.out.println("Guess must be between 1 and 100.");
                    continue;
                }

                attempts++;
                if (guess == number) {
                    won = true;
                    roundsWon++;
                    int roundScore = MAX_ATTEMPTS - attempts + 1;
                    totalScore += roundScore;
                    System.out.println("Correct! You guessed the number in " + attempts + " attempt(s).");
                    System.out.println("Round score: " + roundScore);
                    break;
                } else if (guess < number) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Too high!");
                }
            }

            if (!won) {
                System.out.println("Out of attempts! The number was " + number + ".");
            }

            System.out.print("\nPlay another round? (y/n): ");
            playAgain = scanner.next().equalsIgnoreCase("y");
        }

        System.out.println("\n=================================");
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Total score: " + totalScore);
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
