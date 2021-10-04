package Exp1.MultipleClientsToServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static void main(String[] args) throws IOException {
//Server require its own socket
        ServerSocket s1 = new ServerSocket(107);
        System.out.println("Waiting for Client....");
        while (true) {
//normal socket is required to accept the incoming request to socket s1
            Socket ss = s1.accept();
            portThread p = new portThread(ss);
            p.start();
        }
    }
}

// extends Thread gives an ability to be a thread
class portThread extends Thread {
    private Socket ss;

    //Constructor
    public portThread(Socket s) {
        this.ss = s;
    }

    public void run() {
        try {
            String[] name;
            System.out.println("\n" + "New Client Connected...");
            int f = 0;
            while (true) {
// to except data from client
                DataInputStream din = new DataInputStream(ss.getInputStream());
                String msgin = din.readUTF();
                String[] a = msgin.split(" ");
                name = a[0].split(":");
                if (a[1].equals("bye") || a[1].equals("Bye") ||
                        a[1].equals("BYE") || a[1].equals("Bbye") || a[1].equals("bbye")) {
                    f = 1;
                    break;
                }
                System.out.print(msgin + "\n");
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(System.in));
// Taking message from Server
                System.out.print("Server: ");
                String msgout = br.readLine();
                System.out.println("");
//for sending data to client
                DataOutputStream dout = new
                        DataOutputStream(ss.getOutputStream());
                dout.writeUTF(msgout);
                dout.flush();
            }
            if (f == 1) System.out.println("Client " + name[0] + " dismissed the Connection.." + "\n");
            ss.close();
        } catch (IOException e) {
            System.out.println("Waiting for client....");
        }
    }
}
