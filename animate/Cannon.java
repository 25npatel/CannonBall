package animate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class Cannon {

    private double x = 0;
    private double y = 0;
    private double angle;
    private double muzzleVelocity;
    private static final double HYPOT = 100;
    private Image cannonImage;
    private Clip wheelClip;
    private Clip cannonClip;

    //Constructor.
    public Cannon(double x, double y) {

        try {

            //Load the cannon image
            cannonImage = ImageIO.read(new File("media/sm_cannon.png"));
            System.out.println("Cannon!");

            //Load sound clips
            wheelClip = loadAudioClip("media/wheel.wav");
            cannonClip = loadAudioClip("media/cannon.wav");

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {

            ex.printStackTrace();
        }

        //Set initial position and angle
        this.x = x;
        this.y = y;
        angle = (-45);

        //pixels per timer interval.
        muzzleVelocity = 100;
    }

    //Getters and Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getMuzzleVelocity() {
        return muzzleVelocity;
    }

    public void setMuzzleVelocity(double muzzleVelocity) {
        this.muzzleVelocity = muzzleVelocity;
    }

    //Method to rotate cannon counterclockwise
    public void rotateCannonCounterclockwise() {

        if (angle <= -90) {
            if (wheelClip != null) {

                //Stop previous clip if it's playing
                wheelClip.stop();

                //Rewind to the beginning
                wheelClip.setFramePosition(0);

                //Start the sound effect
                wheelClip.start();
            }
        } else {

            angle -= 5;

            if (wheelClip != null) {

                //Stop previous clip if it's playing
                wheelClip.stop();

                //Rewind to the beginning
                wheelClip.setFramePosition(0);

                //Start the sound effect
                wheelClip.start();
            }
        }
    }

    //Method to rotate cannon clockwise
    public void rotateCannonClockwise() {

        if (angle >= 0) {

            if (wheelClip != null) {

                //Stop previous clip if it's playing
                wheelClip.stop();
                //Rewind to the beginning
                wheelClip.setFramePosition(0);
                //Start the sound effect
                wheelClip.start();
            }
        } else {

            angle += 5;

            if (wheelClip != null) {

                //Stop previous clip if it's playing
                wheelClip.stop();
                //Rewind to the beginning
                wheelClip.setFramePosition(0);
                //Start the sound effect
                wheelClip.start();

            }
        }
    }

    //Method to fire the cannon
    public void fireCannon(CannonBall cannonBall) {

        double vx0 = muzzleVelocity * Math.cos(Math.abs(Math.toRadians(angle)));
        double vy0 = -muzzleVelocity * Math.sin(Math.abs(Math.toRadians(angle)));

        double offsetX = HYPOT * Math.cos(Math.abs(Math.toRadians(angle)));
        double offsetY = -HYPOT * Math.sin(Math.abs(Math.toRadians(angle)));

        double startX = x + offsetX;
        double startY = y + offsetY;

        cannonBall.launch(startX, startY, vx0, vy0);

        cannonClip.start();

        cannonBall.updateBall();

        if (cannonClip != null) {

            //Stop previous clip if it's playing
            cannonClip.stop();
            //Rewind to the beginning
            cannonClip.setFramePosition(0);
            //Start the sound effect
            cannonClip.start();
        }
    }

    //Method to draw the cannon on the board
    public void draw(Graphics2D g2d) {

        //Draw cannon image
        AffineTransform transform = new AffineTransform();

        transform.translate(x - 15, y - 45);

        //Rotate around pivot point
        transform.rotate(Math.toRadians(angle), 15, 25);

        g2d.drawImage(cannonImage, transform, null);

        int[] triangleX = {(int) (x), (int) (x + 20), (int) (x - 20)};
        int[] triangleY = {(int) (y - 20), (int) (y + 22), (int) (y + 22)};

        //Draw the filled triangle.
        g2d.setColor(Color.PINK);
        g2d.fillPolygon(triangleX, triangleY, 3);

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) (x - 5), (int) (y - 20), 10, 10); 
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