//  package Exp2.RMI.Calculator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementationMarks extends UnicastRemoteObject implements RemoteInterfaceMarks {
    public ImplementationMarks() throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname", "192.168.1.2");
        System.out.println("Implementation Class for Marks");
    }

    @Override
    public int totalMarks(int countOfSubjects, int marks[]) throws RemoteException {
        int total = 0;
        for (int i = 0; i < countOfSubjects; i++)
            total += marks[i];
        return total;
    }

    @Override
    public double percentage(int countOfSubjects, int totalScored) throws RemoteException {
        return (double) (totalScored) * 100 / (countOfSubjects * 100);
    }

    @Override
    public String grading(double percent) throws RemoteException {
        String grade[] = { "O", "A", "B", "C", "D", "E", "P", "F" },
                performance[] = { "Outstanding", "Excellent", "Very Good", "Good", "Fair", "Average", "Pass", "Fail" };
        int gp[] = { 10, 9, 8, 7, 6, 5, 4, 0 }, i = 7;
        if (percent >= 80)
            i = 0;
        else if (percent >= 75)
            i = 1;
        else if (percent >= 70)
            i = 2;
        else if (percent >= 60)
            i = 3;
        else if (percent >= 55)
            i = 4;
        else if (percent >= 50)
            i = 5;
        else if (percent >= 45)
            i = 6;
        return "Grade: " + grade[i] + "\nGrade Point: " + gp[i] + "\nPerformance: " + performance[i];
    }
}
