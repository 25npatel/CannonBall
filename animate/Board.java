package animate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {

    private static final int B_WIDTH = 1550;
    private static final int B_HEIGHT = 850;
    private static final int FLOOR = B_HEIGHT - 25;
    private static final double TIME_SCALE = 1.0;
    private Cannon cannon;
    private CannonBall cannonBall;
    private Timer timer;

    public Board() {
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        cannon = new Cannon(60, FLOOR);
        cannonBall = new CannonBall(); // Fix the constructor call with 3 parameters
        cannonBall.setTimeScale(TIME_SCALE); // Set the time scale
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cannonBall.updateBall();
                repaint();
            }
        });

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, FLOOR, B_WIDTH, FLOOR);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, FLOOR + 1, B_WIDTH, B_HEIGHT - FLOOR - 1);

        cannon.draw(g2d);
        cannonBall.draw(g2d);

        displayInformation(g2d);
    }

    private void displayInformation(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Press SPACE to fire the cannonball", 20, 20);
        g2d.drawString("Use arrow keys to rotate the cannon", 20, 40);
        g2d.drawString("Current angle: " + cannon.getAngle(), 20, 60);
        g2d.drawString("Time scale: " + TIME_SCALE, 20, 80);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            cannon.fireCannon(cannonBall);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            cannon.rotateCannonCounterclockwise();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            cannon.rotateCannonClockwise();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}