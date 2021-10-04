package Exp1.ServerSendsReverseSting;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private DataInputStream  in      = null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input  = new DataInputStream(System.in);

            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());

            // takes input from the server socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String incoming = "", outgoing = "";

        try {
            // keep reading until "Over" is input
            while (!(outgoing = input.readLine()).equals("Over") && !incoming.equals("Over")) {
                out.writeUTF(outgoing);
                incoming = in.readUTF();
                System.out.println(incoming);
            }
        }
        catch (IOException i){
            System.out.println(i);
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
