package animate;

import java.awt.Color;
import java.awt.Graphics2D;
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

    private double x = 0;
    private double y = 0;
    private double vx = 0;
    private double vy = 0;
    private double ax = 0;
    private double ay = 9.8;
    private double ground = 0;
    private double timescale = 1;
    private boolean boomPlayed = false;
    private STATE state = STATE.IDLE;
    private BufferedImage flameImage;
    private Clip boomClip;

    public enum STATE {

        IDLE,
        FLYING,
        EXPLODING

    }

    //Constructor
    public CannonBall(double ax, double ay, double ground) {

        System.out.println("CannonBall!");

        this.ax = ax;

        this.ay = ay;

        this.ground = ground;

        try {
        
            this.boomClip = loadAudioClip("media/boom.wav");
            this.flameImage = loadImage("media/flame01.png");

            System.out.println(flameImage);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {

            ex.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) {

        try {
            
            return ImageIO.read(new File(path));

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    //Draw method
    public void draw(Graphics2D g2d) {

        if (state == STATE.IDLE) {
            
        } else if (state == STATE.FLYING) {
            
            g2d.setColor(Color.RED);
            g2d.fillOval((int) (x - 12), (int) (y - 25), 25, 25);

        } else if (state == STATE.EXPLODING) {

            boomClip.start();
            
            g2d.drawImage(flameImage, (int) (x - 23), (int) (800), null);
        }

        updateBall();
    }

    //Update ball method
    public void updateBall() {
        if (state == STATE.FLYING) {

            vx = vx + (ax / Board.TIME_SCALE);

            vy = vy + (ay / Board.TIME_SCALE);

            x = x + (vx / Board.TIME_SCALE);

            y = y + (vy / Board.TIME_SCALE);

            if (y >= ground) {

                state = STATE.EXPLODING;

                boomPlayed = false;
            }
        }
    }

    //Launch method
    public void launch(double x, double y, double vx, double vy) {
        if (state != STATE.FLYING) {

            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;

            this.state = STATE.FLYING;

        } else if (state == STATE.EXPLODING && y >= ground) {

            this.state = STATE.EXPLODING;

            boomClip.stop();
 
            boomClip.setFramePosition(0);

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

    public void setTimeScale(double TIME_SCALE) {
        this.timescale = TIME_SCALE;
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
