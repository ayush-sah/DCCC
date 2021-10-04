package Exp2.RPC.Calculator;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 5001);
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        System.out.println("Client ready, type and press Enter key");
        String receiveMessage, sendMessage, temp;

        while (true) {
            System.out.print("\nEnter operation to perform(Add, Sub, Mul, Div, Mod) \nor to get current date and time type \"date\": ");
            temp = keyRead.readLine();
            sendMessage = temp.toLowerCase();
            pwrite.println(sendMessage);

            if (sendMessage.equals("end")) {
                System.out.println("Connection Closed!");
                keyRead.close();
                pwrite.close();
                sock.close();
                return;
            } else if (!sendMessage.equals("date")) {
                System.out.print("Enter first parameter : ");
                sendMessage = keyRead.readLine();
                pwrite.println(sendMessage);
                System.out.print("Enter second parameter : ");
                sendMessage = keyRead.readLine();
                pwrite.println(sendMessage);
            }
            System.out.flush();

            if ((receiveMessage = receiveRead.readLine()) != null)
                System.out.println(receiveMessage);
        }
    }
}

