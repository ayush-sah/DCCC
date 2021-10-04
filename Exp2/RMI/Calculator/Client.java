import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String URL;
            RemoteInterfaceCalc inf;
            URL = "rmi://localhost/calc";
            inf = (RemoteInterfaceCalc) Naming.lookup(URL);
            int n1, n2;
            Scanner sc = new Scanner(System.in);
            boolean run = true;
            while (run) {
                System.out.print("1. Addition\t2. Subtraction\t3. Multiplication\t4. Division\tEnter Choice:");
                int parameters = sc.nextInt();
                switch (parameters) {
                    case 1:
                        System.out.print("First Number :");
                        n1 = sc.nextInt();
                        System.out.print("Second Number :");
                        n2 = sc.nextInt();
                        System.out.println("Answer:" + inf.add(n1, n2));
                        break;
                    case 2:
                        System.out.print("First Number :");
                        n1 = sc.nextInt();
                        System.out.print("Second Number :");
                        n2 = sc.nextInt();
                        System.out.println("Answer:" + inf.sub(n1, n2));
                        break;
                    case 3:
                        System.out.print("First Number :");
                        n1 = sc.nextInt();
                        System.out.print("Second Number :");
                        n2 = sc.nextInt();
                        System.out.println("Answer:" + inf.mul(n1, n2));
                        break;
                    case 4:
                        System.out.print("First Number :");
                        n1 = sc.nextInt();
                        System.out.print("Second Number :");
                        n2 = sc.nextInt();
                        System.out.println("Answer:" + inf.div(n1, n2));
                        break;
                }
            }
        } catch (Exception e) {
        }
    }

}
