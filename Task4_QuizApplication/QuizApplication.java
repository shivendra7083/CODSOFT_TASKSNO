import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/** CodSoft Task 4: Quiz Application with Timer. */
public class QuizApplication {
    private static final int TIME_LIMIT_SECONDS = 15;

    public static void main(String[] args) {
        List<Question> questions = List.of(
            new Question("Which keyword is used to inherit a class in Java?", new String[]{"this", "extends", "implements", "super"}, 1),
            new Question("Which collection does not allow duplicate elements?", new String[]{"List", "Queue", "Set", "ArrayList"}, 2),
            new Question("What is the default value of an int instance variable?", new String[]{"0", "1", "null", "undefined"}, 0),
            new Question("Which method is the entry point of a Java application?", new String[]{"start()", "run()", "main()", "init()"}, 2),
            new Question("Which keyword prevents a class from being inherited?", new String[]{"static", "const", "private", "final"}, 3)
        );

        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int correct = 0;
        int unanswered = 0;
        int score = 0;

        System.out.println("=================================");
        System.out.println("       QUIZ APPLICATION");
        System.out.println("=================================");
        System.out.println("You have " + TIME_LIMIT_SECONDS + " seconds per question.");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.text());
            for (int option = 0; option < q.options().length; option++) {
                System.out.println((option + 1) + ". " + q.options()[option]);
            }

            Callable<Integer> inputTask = () -> readAnswer(scanner);
            Future<Integer> future = executor.submit(inputTask);
            long start = System.nanoTime();

            try {
                int answer = future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
                double elapsed = (System.nanoTime() - start) / 1_000_000_000.0;
                if (answer == q.correctOption() + 1) {
                    correct++;
                    score += Math.max(1, TIME_LIMIT_SECONDS - (int) elapsed + 1);
                    System.out.println("Correct!\n");
                } else {
                    System.out.println("Incorrect. Correct answer: " + (q.correctOption() + 1) + "\n");
                }
            } catch (TimeoutException e) {
                future.cancel(true);
                unanswered++;
                System.out.println("Time's up! Correct answer: " + (q.correctOption() + 1) + "\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (ExecutionException e) {
                System.out.println("Unable to read the answer.\n");
            }
        }

        executor.shutdownNow();
        int incorrect = questions.size() - correct - unanswered;
        System.out.println("=================================");
        System.out.println("             RESULT");
        System.out.println("=================================");
        System.out.println("Total Questions : " + questions.size());
        System.out.println("Correct Answers : " + correct);
        System.out.println("Incorrect       : " + incorrect);
        System.out.println("Unanswered      : " + unanswered);
        System.out.println("Score           : " + score);
        System.out.println("=================================");
        scanner.close();
    }

    private static int readAnswer(Scanner scanner) {
        while (true) {
            System.out.print("Your answer (1-4): ");
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                if (answer >= 1 && answer <= 4) return answer;
            } else {
                scanner.next();
            }
            System.out.println("Please enter a number from 1 to 4.");
        }
    }

    private record Question(String text, String[] options, int correctOption) {}
}
