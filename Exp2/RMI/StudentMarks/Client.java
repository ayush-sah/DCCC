// package Exp2.RMI.Calculator;

import java.rmi.*;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            String URL = "rmi://localhost/marks";
            boolean run = true;
            RemoteInterfaceMarks inf = (RemoteInterfaceMarks) Naming.lookup(URL);
            while (run) {
                int countOfSubjects;
                System.out.print("Enter the total count of the subjects: ");
                countOfSubjects = sc.nextInt();
                int marks[] = new int[countOfSubjects];
                int count = 0;
                while (count < countOfSubjects) {
                    System.out.print("Enter the marks scored in subject " + (count + 1) + ": ");
                    marks[count++] = sc.nextInt();
                }
                int totalMarks = inf.totalMarks(countOfSubjects, marks);
                System.out.println("Total marks scored: " + totalMarks);
                double percent = inf.percentage(countOfSubjects, totalMarks);
                System.out.println("Percentage: " + percent);
                System.out.println(inf.grading(percent));

                System.out.print("Do you want to continue: ");
                String temp = sc.next();

                if (Objects.equals(temp, "no"))
                    run = false;
            }
            sc.close();
        } catch (Exception e) {
        }
    }

}
