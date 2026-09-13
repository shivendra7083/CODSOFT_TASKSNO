import java.util.Scanner;

/** CodSoft Task 2: Student Grade Calculator. */
public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=================================");
        System.out.println("      STUDENT GRADE CALCULATOR");
        System.out.println("=================================");

        int subjects = readPositiveInt(scanner, "Enter number of subjects: ");
        int totalMarks = 0;

        for (int i = 1; i <= subjects; i++) {
            int marks = readMarks(scanner, "Enter marks for subject " + i + " (0-100): ");
            totalMarks += marks;
        }

        double averagePercentage = (double) totalMarks / subjects;
        String grade = calculateGrade(averagePercentage);

        System.out.println("\n------------- RESULT ------------");
        System.out.println("Total Marks       : " + totalMarks + "/" + (subjects * 100));
        System.out.printf("Average Percentage: %.2f%%%n", averagePercentage);
        System.out.println("Grade             : " + grade);
        System.out.println("---------------------------------");
        scanner.close();
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value > 0) return value;
            } else {
                scanner.next();
            }
            System.out.println("Please enter a positive integer.");
        }
    }

    private static int readMarks(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int marks = scanner.nextInt();
                if (marks >= 0 && marks <= 100) return marks;
            } else {
                scanner.next();
            }
            System.out.println("Marks must be between 0 and 100.");
        }
    }

    private static String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }
}
