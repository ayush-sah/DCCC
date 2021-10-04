package Exp2.RPC.Calculator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket sersock = new ServerSocket(5001);
        System.out.println("Server ready");
        Socket sock = sersock.accept();
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String receiveMessage, sendMessage, fun;
        int a = 0, b = 0, c;

        while (true) {
            fun = receiveRead.readLine();

            if (fun != null) {
                if (fun.compareTo("end") == 0) {
                    System.out.println("Closing connection");
                    sock.close();
                    receiveRead.close();
                    return;
                } else {
                    System.out.println("Operation : " + fun);
                }
            }

            if (fun.equals("date")) {
                Date date = new Date();
                System.out.println("Date: " + date.toString());
                pwrite.println("Date: " + date.toString());
            } else {
                a = Integer.parseInt(receiveRead.readLine());
                System.out.println("Parameter 1 : " + a);
                b = Integer.parseInt(receiveRead.readLine());
                System.out.println("Parameter 2 : " + b);

                if (fun.equals("add")) {
                    c = a + b;
                    System.out.println("Addition = " + c);
                    pwrite.println("Addition = " + c);
                } else if (fun.equals("sub")) {
                    c = a - b;
                    System.out.println("Substraction = " + c);
                    pwrite.println("Substraction = " + c);
                } else if (fun.equals("mod")) {
                    c = a % b;
                    System.out.println("Modulo = " + c);
                    pwrite.println("Modulo =  " + c);
                } else if (fun.equals("mul")) {
                    c = a * b;
                    System.out.println("Multiplication = " + c);
                    pwrite.println("Multiplication = " + c);
                } else if (fun.equals("div")) {
                    if (b == 0) {
                        System.out.println("Denominator cannot be zero");
                        pwrite.println("Denominator cannot be zero");
                    } else {
                        c = a / b;
                        System.out.println("Division = " + c);
                        pwrite.println("Division = " + c);
                    }
                }
            }
            System.out.flush();
        }
    }
}

