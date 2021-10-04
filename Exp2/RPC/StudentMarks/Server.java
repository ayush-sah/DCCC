package Exp2.RPC.StudentMarks;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket sersock = new ServerSocket(5002);
        System.out.println("Server ready");
        Socket sock = sersock.accept();
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String receiveMessage, sendMessage, fun;
        int countOfSubjects, sum, temp;

        while (true) {
            sum = 0;
            receiveMessage = receiveRead.readLine();
            if (receiveMessage != null) {
                if (receiveMessage.compareTo("end") == 0) {
                    System.out.println("Closing connection");
                    sock.close();
                    receiveRead.close();
                    return;
                }
            }

            countOfSubjects = Integer.parseInt(receiveMessage);
            int count = 0;
            while (count++ < countOfSubjects) {
                temp = Integer.parseInt(receiveRead.readLine());
                sum += temp;
                System.out.println("Marks received for subject " + count + ": " + temp);
            }
            System.out.println("Total Marks Obtained = " + sum);
            pwrite.println("Total Marks Obtained = " + sum);
            double percent = (double) (sum * 100) / (countOfSubjects * 100);
            System.out.println("Percentage = " + percent + "%");
            pwrite.println("Percentage = " + percent + "%");
            String grade[] = { "O", "A", "B", "C", "D", "E", "P", "F" }, performance[] = { "Outstanding", "Excellent",
                    "Very Good", "Good", "Fair", "Average", "Pass", "Fail" };
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
            String grading = "Grade: " + grade[i] + "\nGrade Point: " + gp[i] + "\nPerformance: " + performance[i];
            System.out.println(grading);
            pwrite.println("Grade: " + grade[i]);
            pwrite.println("Grade Point: " + gp[i]);
            pwrite.println("Performance: " + performance[i]);
            System.out.flush();
        }
    }
}
