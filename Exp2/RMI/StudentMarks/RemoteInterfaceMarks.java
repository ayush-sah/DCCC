//  package Exp2.RMI.Calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterfaceMarks extends Remote {
    public int totalMarks(int countOfSubjects, int marks[]) throws RemoteException;

    public double percentage(int countOfSubjects, int totalMarks) throws RemoteException;

    public String grading(double percent) throws RemoteException;
}