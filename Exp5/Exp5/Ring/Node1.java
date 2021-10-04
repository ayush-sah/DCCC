package Exp5.Ring;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Node1 {

    public static void main(String[] args) {
        Node1 n = new Node1();
        try {
            n.start();
        } catch (Exception ex) {
            Logger.getLogger(Node1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start() {
        try {
            Exp5.Ring.Client c = new Exp5.Ring.Client("Node1", "localhost", 1);
            c.buildInterface();
        } catch (Exception ex) {
            Logger.getLogger(Node1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}