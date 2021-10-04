package Exp2.RPC.StudentMarks;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 5002);
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        System.out.println("Client ready, type and press Enter key");
        String receiveMessage, sendMessage, temp;

        while (true) {

            System.out.print("Enter the number of Subjects you want to calculater marks for: ");
            temp = keyRead.readLine();
            sendMessage = temp.toLowerCase();
            pwrite.println(sendMessage);

            int countOfSubjects = Integer.parseInt(temp);
            int count = 0;
            while (count++ < countOfSubjects) {
                System.out.print("Enter the marks scored in Subject " + count + ": ");
                sendMessage = keyRead.readLine();
                pwrite.println(sendMessage);
            }
            System.out.flush();
            System.out.println(receiveRead.readLine());
            System.out.println(receiveRead.readLine());
            System.out.println(receiveRead.readLine());
            System.out.println(receiveRead.readLine());
            System.out.println(receiveRead.readLine() + "\n\n");
            System.out.print("Do you want to continue? ");
            temp = keyRead.readLine();

            if (Objects.equals(temp, "no")) {
                pwrite.println("end");
                System.out.println("Connection Closed!");
                keyRead.close();
                pwrite.close();
                sock.close();
                return;
            }
        }
    }
}
