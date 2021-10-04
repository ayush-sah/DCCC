package Exp1.ChatApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.*;

public class Server {
    private DataInputStream input = null;
    static Vector ClientSockets;
    static Vector LoginNames;

    public Server() throws Exception {
        input = new DataInputStream(System.in);
        ServerSocket soc = new ServerSocket(5219);
        ClientSockets = new Vector();
        LoginNames = new Vector();

        while (true) {
            Socket CSoc = soc.accept();
            AcceptClient obClient = new AcceptClient(CSoc);
        }
    }

    public static void main(String args[]) throws Exception {

        Server ob = new Server();
    }

    class AcceptClient extends Thread {
        Socket ClientSocket;
        DataInputStream din;
        DataOutputStream dout;

        AcceptClient(Socket CSoc) throws Exception {
            ClientSocket = CSoc;

            din = new DataInputStream(ClientSocket.getInputStream());
            dout = new DataOutputStream(ClientSocket.getOutputStream());

            String LoginName = din.readUTF();

            System.out.println("User Logged In :" + LoginName);
            LoginNames.add(LoginName);
            ClientSockets.add(ClientSocket);
            start();
        }

        public void run() {
            while (true) {

                try {
                    String msgFromClient = new String();
                    msgFromClient = din.readUTF();
                    StringTokenizer st = new StringTokenizer(msgFromClient);
                    String Sendto = st.nextToken();
                    String MsgType = st.nextToken();
                    int iCount = 0;

                    if (MsgType.equals("LOGOUT")) {
                        for (iCount = 0; iCount < LoginNames.size(); iCount++) {
                            if (LoginNames.elementAt(iCount).equals(Sendto)) {
                                LoginNames.removeElementAt(iCount);
                                ClientSockets.removeElementAt(iCount);
                                System.out.println("User " + Sendto + " Logged Out ...");
                                break;
                            }
                        }
                    } else {
                        String msg = "";
                        //String line="";
                        while (st.hasMoreTokens()) {
                            msg = msg + " " + st.nextToken();
                            System.out.println(msg);

                        }
                        for (iCount = 0; iCount < LoginNames.size(); iCount++) {
                            if (LoginNames.elementAt(iCount).equals(Sendto)) {
                                Socket tSoc = (Socket) ClientSockets.elementAt(iCount);
                                DataOutputStream tdout = new DataOutputStream(tSoc.getOutputStream());
                                tdout.writeUTF(msg);
                                break;
                            }
                        }
                        String line = "";
                        if (iCount == LoginNames.size()) {
                            line = input.readLine();
                            dout.writeUTF(line);
                        }

                    }
                    if (MsgType.equals("LOGOUT")) {
                        break;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}