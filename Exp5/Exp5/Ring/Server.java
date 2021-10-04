package Exp5.Ring;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server extends JFrame {
    public static final int PORT = 3990;
    ArrayList<HandleClient> clients = new ArrayList<HandleClient>();

    public void process() throws Exception {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("server is online");
        while (true) {
            Socket client = s.accept();
            HandleClient c = new HandleClient(client);
            clients.add(c);
        }
    }

    public static void main(String[] args) throws Exception {
        Server c = new Server();
        c.buildInterface();
        c.process();
    }

    JFrame main;
    JTextArea iTxt;
    JTextArea oTxt;
    JLabel chek1 = new JLabel("Down");
    JLabel chek2 = new JLabel("Down");
    JLabel chek3 = new JLabel("Down");
    JLabel chek4 = new JLabel("Down");
    JLabel chek5 = new JLabel("Down");

    public void buildInterface() {
        main = new JFrame("Server Dashboard");
        main.setAlwaysOnTop(true);
        BorderLayout borderManager = new BorderLayout(2, 2);
        main.setLayout(borderManager);
        JPanel headerPanel = new JPanel();
        JLabel label = new JLabel("Network Status");
        headerPanel.add(label);
        main.add(headerPanel, BorderLayout.NORTH);
        GridLayout gridManger = new GridLayout(5, 2);
        JPanel contents = new JPanel();
        contents.setLayout(gridManger);
        JLabel node1 = new JLabel("Node 1");
        JLabel node2 = new JLabel("Node 2");
        JLabel node3 = new JLabel("Node 3");
        JLabel node4 = new JLabel("Node 4");
        JLabel node5 = new JLabel("Node 5");
        contents.add(node1);
        contents.add(chek1);
        contents.add(node2);
        contents.add(chek2);
        contents.add(node3);
        contents.add(chek3);
        contents.add(node4);
        contents.add(chek4);
        contents.add(node5);
        contents.add(chek5);
        main.add(contents, BorderLayout.CENTER);
        main.setSize(200, 200);
        main.setVisible(true);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    String msg;

    protected class HandleClient implements Runnable {
        String name = "";
        BufferedReader input;
        PrintWriter output;

        public HandleClient(Socket client) throws Exception {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            name = input.readLine();
            Thread t = new Thread(this);
            t.start();
        }

        public String getUserName() {
            return name;
        }

        public void run() {
            System.out.println("thread started");
            String line;
            try {
                while (true) {
                    line = input.readLine();
                    switch (line) {

                        case "Node1U":
                            chek1.setText("Up");
                            break;
                        case "Node1D":
                            chek1.setText("Down");
                            break;
                        case "Node1L":
                            chek1.setText("Leader");
                            notifyLeader(1);
                            break;
                        case "Node2U":
                            chek2.setText("Up");
                            break;
                        case "Node2D":
                            chek2.setText("Down");
                            break;
                        case "Node2L":
                            chek2.setText("Leader");
                            notifyLeader(2);
                            break;
                        case "Node3U":
                            chek3.setText("Up");
                            break;
                        case "Node3D":
                            chek3.setText("Down");
                            break;
                        case "Node3L":
                            chek3.setText("Leader");
                            notifyLeader(3);
                            break;
                        case "Node4U":
                            chek4.setText("Up");
                            break;
                        case "Node4D":
                            chek4.setText("Down");
                            break;
                        case "Node4L":
                            chek4.setText("Leader");
                            notifyLeader(4);
                            break;
                        case "Node5U":
                            chek5.setText("Up");
                            break;
                        case "Node5D":
                            chek5.setText("Down");
                            break;
                        case "Node5L":
                            chek5.setText("Leader");
                            notifyLeader(5);
                            break;

                        case "Nxt1":
                            sendMsg("Node1", getNextNode("Node1"));
                            break;
                        case "Nxt2":
                            sendMsg("Node2", getNextNode("Node2"));
                            break;
                        case "Nxt3":
                            sendMsg("Node3", getNextNode("Node3"));
                            break;
                        case "Nxt4":
                            sendMsg("Node4", getNextNode("Node4"));
                            break;
                        case "Nxt5":
                            sendMsg("Node5", getNextNode("Node5"));
                            break;

                        default:
                            if (line.contains(":")) {

                                System.out.println("in if");
                                String[] temp = line.split(":");
                                String msg = temp[0];
                                String node = temp[1];
                                System.out.println("Destination node is " + node + " msg is " + msg);
                                sendMsg(node, msg);

                            } else {
                                System.out.println(line);
                            }

                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        HandleClient c;

        public String getNextNode(String CurrentNode) {

            String nextNode = "";
            switch (CurrentNode) {
                case "Node1":
                    if (chek2.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node2";
                    } else if (chek3.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node3";
                    } else if (chek4.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node4";
                    } else if (chek5.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node5";
                    }
                    break;

                case "Node2":
                    if (chek3.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node3";
                    } else if (chek4.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node4";
                    } else if (chek5.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node5";
                    } else if (chek1.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node1";
                    }
                    break;
                case "Node3":
                    if (chek4.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node4";
                    } else if (chek5.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node5";
                    } else if (chek1.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node1";
                    } else if (chek2.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node2";
                    }
                    break;

                case "Node4":
                    if (chek5.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node5";
                    } else if (chek1.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node1";
                    } else if (chek2.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node2";
                    } else if (chek3.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node3";
                    }
                    break;

                case "Node5":
                    if (chek1.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node1";
                    } else if (chek2.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node2";
                    } else if (chek3.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node3";
                    } else if (chek4.getText().equalsIgnoreCase("Up")) {
                        nextNode = "Node4";
                    }
                    break;

            }

            return nextNode;
        }

        private void sendMsg(String CurrentNode, String nextNode) {
            System.out.println("Current is " + CurrentNode + " nextNode is " + nextNode);
            HandleClient temp;
            for (HandleClient c1 : clients) {
                if (c1.getUserName().equals(CurrentNode)) {
                    temp = c1;
                    c1.output.println(nextNode);
                    System.out.println("OutMSG to c1 is " + c1.getUserName() + " msg " + nextNode);
                    return;
                }
            }
        }

        private void notifyLeader(int i) {
            System.out.println("in notify");
            switch (i) {
                case 1:
                    if (chek1.getText().equalsIgnoreCase("Leader")) {
                        sendMsg("Node1", "leader is 1");
                    }
                    if (chek2.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node2", "leader is 1");
                    }
                    if (chek3.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node3", "leader is 1");
                    }
                    if (chek4.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node4", "leader is 1");
                    }
                    if (chek5.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node5", "leader is 1");
                    }
                    break;

                case 2:
                    if (chek1.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node1", "leader is 2");
                    }
                    if (chek2.getText().equalsIgnoreCase("Leader")) {
                        sendMsg("Node2", "leader is 2");
                    }
                    if (chek3.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node3", "leader is 2");
                    }
                    if (chek4.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node4", "leader is 2");
                    }
                    if (chek5.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node5", "leader is 2");
                    }
                    break;

                case 3:
                    if (chek1.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node1", "leader is 3");
                    }
                    if (chek2.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node2", "leader is 3");
                    }
                    if (chek3.getText().equalsIgnoreCase("Leader")) {
                        sendMsg("Node3", "leader is 3");
                    }
                    if (chek4.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node4", "leader is 3");
                    }
                    if (chek5.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node5", "leader is 3");
                    }
                    break;

                case 4:
                    if (chek1.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node1", "leader is 4");
                    }
                    if (chek2.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node2", "leader is 4");
                    }
                    if (chek3.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node3", "leader is 4");
                    }
                    if (chek4.getText().equalsIgnoreCase("Leader")) {
                        sendMsg("Node4", "leader is 4");
                    }
                    if (chek5.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node5", "leader is 4");
                    }
                    break;

                case 5:
                    if (chek1.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node1", "leader is 5");
                    }
                    if (chek2.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node2", "leader is 5");
                    }
                    if (chek3.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node3", "leader is 5");
                    }
                    if (chek4.getText().equalsIgnoreCase("UP")) {
                        sendMsg("Node4", "leader is 5");
                    }
                    if (chek5.getText().equalsIgnoreCase("Leader")) {
                        sendMsg("Node5", "leader is 5");
                    }
                    break;
            }
        }

    }
}