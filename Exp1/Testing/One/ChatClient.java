package Exp1.Testing.One;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {
    Socket soc;
    BufferedReader br, br1;
    PrintWriter out;
    String str;

    public ChatClient() {
        try {
            soc = new Socket(InetAddress.getLocalHost(), 9999);
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(soc.getOutputStream(), true);
            System.out.println("ChatClient Started....");
            while (true) {
                str = br.readLine();
                out.println(str);

                new InnerClient();
            }
        } catch (Exception e) {
        }
    }

    class InnerClient extends Thread {
        String str1;

        InnerClient() {
            try {
                br1 = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                start();
            } catch (Exception e) {
            }
        }
    }

    public void run() {
        try {
            while (true) {
                str = br.readLine();
                System.out.println("Server Says: " + str);
            }
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        new ChatClient();
    }
}