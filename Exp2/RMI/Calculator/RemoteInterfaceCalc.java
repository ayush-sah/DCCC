//  package Exp2.RMI.Calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterfaceCalc extends Remote {
    public int add(int n1, int n2) throws RemoteException;

    public int sub(int n1, int n2) throws RemoteException;

    public int mul(int n1, int n2) throws RemoteException;

    public int div(int n1, int n2) throws RemoteException;

}