//  package Exp2.RMI.Calculator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementationCalc extends UnicastRemoteObject implements RemoteInterfaceCalc {
    public ImplementationCalc() throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname","192.168.1.2");
        System.out.println("Implementation CLass for Calculator");
    }

    public int add(int n1, int n2) throws RemoteException {
        return n1 + n2;
    }

    public int sub(int n1, int n2) throws RemoteException {
        return n1 - n2;
    }

    public int mul(int n1, int n2) throws RemoteException {
        return n1 * n2;
    }

    public int div(int n1, int n2) throws RemoteException {
        return n1 / n2;
    }

}

