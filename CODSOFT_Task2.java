import java.util.Scanner;

class Grade {
    public String grade(int avgp) {
        switch (avgp / 10) {
            case 10:
                return "A+";
            case 9:
                return "A";
            case 8:
                return "B+";
            case 7:
                return "B";
            case 6:
                return "C";
            case 5:
                return "D";
            default:
                return "F";
        }
    }
}

public class CODSOFT_Task2{
    public static void main(String[] args) {
        Grade g = new Grade();
        Scanner sc = new Scanner(System.in);

        // Taking input for the number of subjects
        System.out.println("Enter the number of subjects:");
        int numSubjects = sc.nextInt();
        int totalMarks = 0;

        // Taking and adding all the marks
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("Enter the marks obtained in subject " + (i + 1) + ":");
            int marks = sc.nextInt();
            totalMarks += marks;
        }

        // Calculating percentage
        double averagePercentage = (double) totalMarks / numSubjects;

        // Calculating grades
        String grade = g.grade((int) averagePercentage);

        // Displaying all the data
        System.out.println("Total marks obtained: " + totalMarks);
        System.out.println("Percentage obtained: " + String.format("%.2f", averagePercentage) + "%");
        System.out.println("Grade obtained: " + grade);

        sc.close();
        // Let's test the code
    }
}
