//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    Astronaut astro;
    Image astroImage;
    Astronaut Boi;
    Image BoiImage;
    Meteor meteor;
    Image meteorImage;
    Image space;
    Astronaut alien;
    Image alienImage;
    Death border;
    Image borderImage;



    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();
        Boi = new Astronaut("img.png", 0, 0, 0.75);
        BoiImage = Toolkit.getDefaultToolkit().getImage("img.png");
        astro = new Astronaut("this.jpg", 300, 300, 0.25);
        astroImage = Toolkit.getDefaultToolkit().getImage("this.jpg");
        meteor = new Meteor("meteor", 500, 300, 0);
        meteorImage = Toolkit.getDefaultToolkit().getImage("smth.jpg");
        border = new Death("border of doom",0,600,1.00);
        borderImage = Toolkit.getDefaultToolkit().getImage("img.png");
        run();
    } // end BasicGameApp constructor
//*******************************************************************************
//User Method Section
// put your code to do things here.
    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            //if (astro.isAlive == false){
            //    astro.width = astro.width +1;
            //    astro.height = astro.height +1;
            //}
            render();  // paint the graphics
            pause(30); // sleep for 10 ms
        }
    }
    public void moveThings() {
        astro.move();
        astro.wrap();
        Boi.moove();
        Boi.bounce();
        meteor.move();
        meteor.bounce();
        border.move();
        if(astro.rect.intersects(Boi.rect)){
            Boi.dx = -Boi.dx;
            Boi.dy = -Boi.dy;

            double rand1 = Math.random();
            double rand2 = Math.random();
           // if (rand1 + Boi.successRate > rand2 + astro.successRate) {
           //     astro.width= astro.width +10;//makes astro wider 75% of the time
           // } else {
           //     Boi.height= Boi.height +10; //makes Boi taller 25% of the time
           // }
            astro.isAlive = false;
        }
        if (Boi.dx>=100){
            Boi.dx=Boi.dx/20;
        }
        if(Boi.rect.intersects(border.rect)){
            astro.health = astro.health - 1;
        }




    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(new Color(182, 30 ,44 ));
        g.fillRect(0,0,astro.health*10, 20);
        g.drawImage(astroImage, astro.xpos, astro.ypos, astro.width, astro.height, null);
        g.drawImage(meteorImage, meteor.xpos, meteor.ypos, meteor.width, meteor.height, null);
        g.drawImage(BoiImage, Boi.xpos, Boi.ypos, Boi.width, Boi.height, null);
        g.drawImage(borderImage, border.xpos, border.ypos, border.width, border.height, null);
        g.dispose();
        bufferStrategy.show();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger

        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();

        canvas.addKeyListener(this);

        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode()==87){
            astro.dy = -10;
            astro.dx = 0;
        }
        if (e.getKeyCode()==83) {
            astro.dy = 10;
            astro.dx = 0;
        }
        if (e.getKeyCode()==65) {
            astro.dy = 0;
            astro.dx = -10;
        }
        if (e.getKeyCode()==68) {
            astro.dy = 0;
            astro.dx = 10;
        }
        if (e.getKeyCode()==69) {
            astro.dy = 0;
            astro.dx = 50;
        }
        if (e.getKeyCode()==81) {
            astro.dy = 0;
            astro.dx = -50;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==87){
            astro.dy = 0;
            astro.dx = 0;
        }
        if (e.getKeyCode()==83) {
            astro.dy = 0;
            astro.dx = 0;
        }
        if (e.getKeyCode()==65) {
            astro.dy = 0;
            astro.dx = 0;
        }
        if (e.getKeyCode()==68) {
            astro.dy = 0;
            astro.dx = 0;
        }
        if (e.getKeyCode()==69) {
            astro.dy = 0;
            astro.dx = 0;
        }
        if (e.getKeyCode()==81) {
            astro.dy = 0;
            astro.dx = 0;
        }



    }
}
