import java.util.*;

/** CodSoft Task 5: Student Course Registration System. */
public class CourseRegistrationSystem {
    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();
        seedCourses(courseDatabase);

        Scanner scanner = new Scanner(System.in);
        Student student = registerStudent(scanner, studentDatabase);
        boolean running = true;

        while (running) {
            printMenu(student);
            int choice = readInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1 -> courseDatabase.displayCourses();
                case 2 -> registerForCourse(scanner, courseDatabase, student);
                case 3 -> removeCourse(scanner, courseDatabase, student);
                case 4 -> displayRegisteredCourses(student, courseDatabase);
                case 5 -> {
                    running = false;
                    System.out.println("Thank you for using the registration system.");
                }
                default -> System.out.println("Invalid option. Please choose 1-5.");
            }
        }
        scanner.close();
    }

    private static void seedCourses(CourseDatabase db) {
        db.addCourse(new Course("CS101", "Java Programming", "Core Java and OOP fundamentals", 3, "Mon/Wed 10:00 AM"));
        db.addCourse(new Course("CS201", "Data Structures", "Arrays, linked lists, stacks, queues and trees", 3, "Tue/Thu 11:00 AM"));
        db.addCourse(new Course("CS301", "Database Systems", "Relational databases and SQL", 2, "Fri 2:00 PM"));
        db.addCourse(new Course("CS401", "Software Engineering", "Software development practices and design", 2, "Sat 10:00 AM"));
    }

    private static Student registerStudent(Scanner scanner, StudentDatabase db) {
        System.out.println("=================================");
        System.out.println(" STUDENT COURSE REGISTRATION");
        System.out.println("=================================");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        while (id.isEmpty()) {
            System.out.print("Student ID cannot be empty. Enter again: ");
            id = scanner.nextLine().trim();
        }
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Enter again: ");
            name = scanner.nextLine().trim();
        }
        Student student = new Student(id, name);
        db.addStudent(student);
        return student;
    }

    private static void printMenu(Student student) {
        System.out.println("\nLogged in: " + student.name() + " (" + student.id() + ")");
        System.out.println("1. List available courses");
        System.out.println("2. Register for a course");
        System.out.println("3. Drop a course");
        System.out.println("4. View registered courses");
        System.out.println("5. Exit");
    }

    private static void registerForCourse(Scanner scanner, CourseDatabase db, Student student) {
        System.out.print("Enter course code: ");
        String code = scanner.next().toUpperCase();
        Course course = db.findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
        } else if (student.courses().contains(code)) {
            System.out.println("You are already registered for this course.");
        } else if (course.isFull()) {
            System.out.println("Registration failed: course is full.");
        } else {
            student.courses().add(code);
            course.registerStudent();
            System.out.println("Registration successful for " + course.title() + ".");
        }
    }

    private static void removeCourse(Scanner scanner, CourseDatabase db, Student student) {
        System.out.print("Enter course code to drop: ");
        String code = scanner.next().toUpperCase();
        Course course = db.findCourse(code);
        if (!student.courses().remove(code)) {
            System.out.println("You are not registered for this course.");
        } else {
            if (course != null) course.dropStudent();
            System.out.println("Course removed successfully.");
        }
    }

    private static void displayRegisteredCourses(Student student, CourseDatabase db) {
        System.out.println("\nRegistered Courses:");
        if (student.courses().isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (String code : student.courses()) {
            Course course = db.findCourse(code);
            if (course != null) {
                System.out.println(course.code() + " - " + course.title() + " | " + course.schedule());
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) return scanner.nextInt();
            scanner.next();
            System.out.println("Please enter a valid integer.");
        }
    }

    private static class Course {
        private final String code;
        private final String title;
        private final String description;
        private final int capacity;
        private final String schedule;
        private int registered;

        Course(String code, String title, String description, int capacity, String schedule) {
            if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
            this.code = code;
            this.title = title;
            this.description = description;
            this.capacity = capacity;
            this.schedule = schedule;
        }
        String code() { return code; }
        String title() { return title; }
        String description() { return description; }
        int capacity() { return capacity; }
        String schedule() { return schedule; }
        boolean isFull() { return registered >= capacity; }
        void registerStudent() { if (!isFull()) registered++; }
        void dropStudent() { if (registered > 0) registered--; }
        int availableSlots() { return capacity - registered; }
    }

    private record Student(String id, String name, List<String> courses) {
        Student(String id, String name) { this(id, name, new ArrayList<>()); }
    }

    private static class CourseDatabase {
        private final Map<String, Course> courses = new LinkedHashMap<>();
        void addCourse(Course course) { courses.put(course.code(), course); }
        Course findCourse(String code) { return courses.get(code); }
        void displayCourses() {
            System.out.println("\nAvailable Courses:");
            for (Course course : courses.values()) {
                System.out.printf("%s | %s | %s | Capacity: %d | Available: %d | %s%n",
                    course.code(), course.title(), course.description(), course.capacity(), course.availableSlots(), course.schedule());
            }
        }
    }

    private static class StudentDatabase {
        private final Map<String, Student> students = new HashMap<>();
        void addStudent(Student student) { students.put(student.id(), student); }
    }
}
