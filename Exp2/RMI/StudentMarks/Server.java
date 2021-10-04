// package Exp2.RMI.Calculator;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            ImplementationMarks marks = new ImplementationMarks();
            Naming.rebind("marks", marks);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
