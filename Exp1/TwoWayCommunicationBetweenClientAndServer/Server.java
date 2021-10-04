package Exp1.TwoWayCommunicationBetweenClientAndServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    //initialize socket and input stream
    private Socket           socket   = null;
    private ServerSocket     server   = null;
    private DataInputStream  in       = null;
    private DataOutputStream out      = null;
    private DataInputStream  input    = null;

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            // sends input to client
            out = new DataOutputStream(socket.getOutputStream());

            // takes input from terminal
            input  = new DataInputStream(System.in);

            String incoming = "", outgoing = "";
            // reads message from client until "Over" is sent
//            while (!incoming.equals("Over") && !outgoing.equals("Over"))
//            {
                while((incoming = in.readUTF()) != null){
                    System.out.println(incoming);
                    outgoing = input.readLine();

                    out.writeUTF(outgoing);
                }
//                try
//                {
//                    line = in.readUTF();
//                    System.out.println(line);
//
//                }
//                catch(IOException i)
//                {
//                    System.out.println(i);
//                }
//            }

            socket.close();
            in.close();
            out.close();
            input.close();
            server.close();
        }
        catch(IOException i)
        {
            System.out.println("Closing connection");
            // close connection
        }
    }

    public static void main(String args[])
    {
        Server server = new Exp1.TwoWayCommunicationBetweenClientAndServer.Server(5000);
    }
}
