package Exp5.Ring;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame {
    int NO;
    String uname;
    PrintWriter pw;
    BufferedReader br;
    JTextArea incomingMsgsTxt;
    JTextField usrIpTxt;
    JButton btnSend, btnLogoff, btnLogin;
    Socket client;

    public Client(String uname, String servername, int i) throws Exception {
        super(uname);
        this.uname = uname;
        this.NO = i;
        client = new Socket(servername, 3990);
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        pw = new PrintWriter(client.getOutputStream(), true);
        pw.println(uname);
        MessagesThread m = new MessagesThread();
        Thread t = new Thread(m);
        t.start();
    }

    JButton connect = new JButton("Connect");

    public void buildInterface() {
        btnSend = new JButton("Election");
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = connect.getText();
                switch (cmd) {
                    case "Connect":
                        pw.println("Node" + NO + "U");
                        connect.setText("Disconnect");
                        break;
                    case "Disconnect":
                        pw.println("Node" + NO + "D");
                        connect.setText("Connect");
                        break;
                }
            }
        });
        incomingMsgsTxt = new JTextArea();
        incomingMsgsTxt.setRows(10);
        incomingMsgsTxt.setColumns(10);
        incomingMsgsTxt.setEditable(false);
        JScrollPane sp = new JScrollPane(incomingMsgsTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp, "Center");
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(btnSend);
        bp.add(connect);
        add(bp, "South");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // starting election set the flag true
                Init_flag = true;
                // ask for next node
                sendMsg("Nxt" + NO);
                // recving next node informatoin

            }
        });
        setSize(250, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        pack();
    }

    public void sendMsg(String test) {
        pw.println(test);
    }

    public void sendMsg(String msg, String node) {
        pw.println(node + ":" + msg);
    }

    public static void main(String[] args) {
        try {
            Client c = new Client("Node", "localhost", 1);
            c.buildInterface();
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }
    }

    String line;
    String Msg = "{";
    boolean Init_flag = false;
    String saveMsg = "";

    public void reciveMsg() {
        try {
            while (true) {
                line = br.readLine();
                System.out.println("recvied > " + line);
                incomingMsgsTxt.append(line + "\n");

                if (line.contains("Node")) { // recived next Node location
                    if (Init_flag) {
                        Msg = "{" + NO + ",";
                        sendMsg(line, Msg);
                    } else {
                        sendMsg(line, saveMsg);
                    }
                } else if (line.contains("{")) { // msg recivied form other node as a part of election
                    if (line.contains(NO + "")) {// determine who is leader
                        determineLeader(line);
                    } else {
                        // append own NO in the msg by saving it to temp

                        saveMsg = line + NO + ",";
                        sendMsg("Nxt" + NO);
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    private void determineLeader(String line) {
        System.out.println("Determine Leader " + line);

        String[] temp = line.split(",");
        int[] nodes = new int[temp.length];

        int i = 0;
        for (String s : temp) {
            if (s.contains("{")) {
                nodes[i] = Integer.parseInt(s.substring(1));
            } else {
                nodes[i] = Integer.parseInt(s);
            }
            i++;
        }

        int leader = nodes[0];

        for (i = 0; i < nodes.length; i++) {
            if (nodes[i] > leader) {
                leader = nodes[i];
            }
        }

        System.out.println("leader is " + leader);

        sendMsg("Node" + leader + "L");

    }

    class MessagesThread implements Runnable {
        @Override
        public void run() {
            // System.out.println("thread is client ");
            reciveMsg();
        }
    }
}