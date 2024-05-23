package animate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

public class CannonBall {

    public enum STATE {
        IDLE,
        FLYING,
        EXPLODING
    }

    private double x = 0;
    private double y = 0;
    private double vx = 0;
    private double vy = 0;
    private double ax = 0;
    private double ay = 0;
    private double ground = 0;
    private double timescale = 1;
    private STATE state = STATE.IDLE;
    private BufferedImage flameImage;
    private Clip boomClip;
    
    //Constructor
    public void NewCannonBall(double ax, double ay, double ground) {

        this.ax = ax;

        this.ay = ay;

        this.ground = ground;

        try {

            this.boomClip = loadAudioClip("media/cannon.wav");

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {

            ex.printStackTrace();
        }
    }

    //Draw method
    public void draw(Graphics2D g2d) {

        if (state == STATE.FLYING) {
            System.out.println("draw me!");
            g2d.setColor(Color.RED);
            g2d.fillOval((int) (x - 5), (int) (y - 5), 10, 10);

        } else if (state == STATE.EXPLODING) {

            try {

                boomClip.start();

                flameImage = ImageIO.read(new File("media/flame01.png"));

            } catch (IOException e) {
                
                e.printStackTrace();
            }
            g2d.drawImage(flameImage, (int) x - flameImage.getWidth() / 2, (int) y - flameImage.getHeight() / 2, null);
        }
    }

    //Update ball method
    public void updateBall() {
        if (state == STATE.FLYING) {

            vx = vx + ax;

            vy = vy + ay;

            x = x + vx;

            y = y + vy;

            if (y >= ground) {

                //boomClip.start();

                //state = STATE.EXPLODING; 
            }
        }
       //System.out.print(state);
    }

    //Launch method
    public void launch(double x, double y, double vx, double vy) {
        if (state != STATE.FLYING) {

            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;

            state = STATE.FLYING;
        } else if (state == STATE.EXPLODING) {

            boomClip.start();
        }
    }

    //Get methods
    public STATE getState() {
        return state;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVX() {
        return vx;
    }

    public double getVY() {
        return vy;
    }

    public double getAX() {
        return ax;
    }

    public double getAY() {
        return ay;
    }

    public double getTimeScale() {
        return timescale;
    }

    public double getGround() {
        return ground;
    }

    //Set methods
    public void setState(STATE newState) {
        this.state = newState;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVX(double vx) {
        this.vx = vx;
    }

    public void setVY(double vy) {
        this.vy = vy;
    }

    public void setAX(double ax) {
        this.ax = ax;
    }

    public void setAY(double ay) {
        this.ay = ay;
    }

    public void setTimeScale(double timeScale) {
        this.timescale = timeScale;
    }

    public void changeTimeScale(double delta) {
        this.timescale += delta;
    }

    public void setGround(double ground) {
        this.ground = ground;
    }

     //Method to load audio clip from file
    private Clip loadAudioClip(String filename) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = null;
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }
}
