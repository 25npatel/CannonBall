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

    private double x, y;
    private double angle;
    private double muzzleVelocity;
    private static final double HYPOT = 100;
    private Image cannonImage;
    private Clip wheelClip;
    private Clip cannonClip;

    // Constructor
    public Cannon(double x, double y) {

        try {
            //Load the cannon image
            cannonImage = ImageIO.read(new File("media/sm_cannon.png"));
            System.out.println("Cannon!");
            //Load sound clips
            wheelClip = loadAudioClip("media/wheel.wav");
            cannonClip = loadAudioClip("media/cannon.wav");
            //boomClip = loadAudioClip("media/boom.wav");
            //flameImage = ImageIO.read(new File("media/flame01.png"));


        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        //Set initial position and angle
        this.x = x;
        this.y = y;
        angle = (-45);

        //pixels per timer interval.
        muzzleVelocity = 37;
    }

    //Getters and setters
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

                // Stop previous clip if it's playing
                wheelClip.stop();
                // Rewind to the beginning
                wheelClip.setFramePosition(0);
                // Start the sound effect
                wheelClip.start();
            }
        } else {

            angle += 5;

            if (wheelClip != null) {

                // Stop previous clip if it's playing
                wheelClip.stop();
                // Rewind to the beginning
                wheelClip.setFramePosition(0);
                // Start the sound effect
                wheelClip.start();

            }
        }
    }

    //Method to fire the cannon
    public void fireCannon(CannonBall cannonBall) {

        if (cannonBall.getState() == CannonBall.STATE.IDLE) {

            double vx0 = muzzleVelocity * Math.cos(angle);
            double vy0 = -muzzleVelocity * Math.sin(angle); // negative because y increases downward

            double offsetX = HYPOT * Math.cos(angle);
            double offsetY = -HYPOT * Math.sin(angle); // negative because y increases downward

            double startX = x + offsetX;
            double startY = y + offsetY;

            cannonBall.launch(startX, startY, vx0, vy0);

            if (cannonClip != null) {

                //Stop previous clip if it's playing
                cannonClip.stop();
                //Rewind to the beginning
                cannonClip.setFramePosition(0);
                //Start the sound effect
                cannonClip.start();
            }
        }
    }

    //Method to draw the cannon on the board
    public void draw(Graphics2D g2d) {

        //Draw cannon image
        //AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();

        transform.translate(x - 15, y - 25);
        transform.rotate(Math.toRadians(angle), 15, 25); //Rotate around pivot point
        //g2d.setTransform(transform);
        g2d.drawImage(cannonImage, transform, null);
        //g2d.setTransform(oldTransform);

        int[] triangleX = {(int) (x), (int) (x + 20), (int) (x - 20)};

        //Y coordinates of vertices.
        int[] triangleY = {(int) (y), (int) (y + 25), (int) (y + 25)};

        //Draw the filled triangle.
        g2d.setColor(Color.PINK);
        g2d.fillPolygon(triangleX, triangleY, 3);

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) (x - 5), (int) (y - 5), 10, 10); 
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
