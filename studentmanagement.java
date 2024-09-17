import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Student class to represent individual students
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNumber=" + rollNumber +
                ", grade='" + grade + '\'' +
                '}';
    }
}

// StudentManagementSystem class to manage student data
class StudentManagementSystem {
    private ArrayList<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Remove a student by roll number
    public boolean removeStudent(int rollNumber) {
        return students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    // Search for a student by roll number
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Save students to file
    public void saveToFile(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(students);
        }
    }

    // Load students from file
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            students = (ArrayList<Student>) ois.readObject();
        }
    }
}

// Main class to interact with the user
public class studentmanagement{
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        String filename = "students.dat";
        boolean exit = false;

        // Load students from file
        try {
            sms.loadFromFile(filename);
            System.out.println("Loaded data from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }

        // User interaction loop
        while (!exit) {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search for Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    scanner.nextLine();  // Clear the buffer
                    String name = scanner.nextLine();
                    // Validate name
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        continue;
                    }

                    System.out.print("Enter roll number: ");
                    int rollNumber = scanner.nextInt();
                    // Validate roll number
                    if (rollNumber <= 0) {
                        System.out.println("Roll number must be a positive integer.");
                        continue;
                    }

                    System.out.print("Enter grade: ");
                    scanner.nextLine();  // Clear the buffer
                    String grade = scanner.nextLine();
                    // Validate grade (simple non-empty validation)
                    if (grade.isEmpty()) {
                        System.out.println("Grade cannot be empty.");
                        continue;
                    }

                    sms.addStudent(new Student(name, rollNumber, grade));
                    System.out.println("Student added successfully.");
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    rollNumber = scanner.nextInt();
                    if (sms.removeStudent(rollNumber)) {
                        System.out.println("Student removed successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter roll number to search: ");
                    rollNumber = scanner.nextInt();
                    Student student = sms.searchStudent(rollNumber);
                    if (student != null) {
                        System.out.println("Student found: " + student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    // Save students to file before exiting
                    try {
                        sms.saveToFile(filename);
                        System.out.println("Data saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
