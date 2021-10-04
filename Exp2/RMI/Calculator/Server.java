// package Exp2.RMI.Calculator;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            ImplementationCalc calc = new ImplementationCalc();
            Naming.rebind("calc", calc);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

