import animate.*;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
        Application ap = new Application("Cannon!");
        ap.setVisible(true);
        });
    }
}
