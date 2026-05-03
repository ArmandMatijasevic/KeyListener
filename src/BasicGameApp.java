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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

//*******************************************************************************

public class BasicGameApp implements Runnable, KeyListener, MouseListener {

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

    Astronaut paddle;
    Image paddleImage;
    Astronaut Ball;
    Image BallImage;
    Meteor meteor;
    Image meteorImage;
    Image background;
    Death border;
    Image borderImage;
    Death winner;
    Image winnerImage;
    int size = 10;
    Block [] blocky;
    Image blockyImage;
    int blocksnumber=10;
    public boolean firstCrash;
    Rectangle button = new Rectangle(100,70,100,70);

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
        Ball = new Astronaut("White Ball.png", 0, 0, 0.75);
        BallImage = Toolkit.getDefaultToolkit().getImage("White Ball.png");
        paddle = new Astronaut("Paddle.png", 0, 100, 0.25);
        paddleImage = Toolkit.getDefaultToolkit().getImage("Paddle.png");
        meteor = new Meteor("meteor", 500, 300, 0);
        meteorImage = Toolkit.getDefaultToolkit().getImage("smth.jpg");
        border = new Death("border of doom",0,690,1.00);
        borderImage = Toolkit.getDefaultToolkit().getImage("death.jpg");
        background = Toolkit.getDefaultToolkit().getImage("Space.jpg");
        blockyImage = Toolkit.getDefaultToolkit().getImage("Normal Block.png");
        winnerImage = Toolkit.getDefaultToolkit().getImage("You Win.jpg");
        winner = new Death("winner",0,700,1.00);
        blocky = new Block [10];
        for (int x=0; x<size; x=x+1) {
            blocky[x] = new Block("block" + x, 30 + (x * (183/2)), 100);
        }
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
            //if (paddle.isAlive == false){
            //    paddle.width = paddle.width +1;
            //    paddle.height = paddle.height +1;
            //}
            render();  // paint the graphics
            pause(30); // sleep for 10 ms

        }
    }

    public void moveThings() {
        paddle.move();
        paddle.wrap();
        Ball.moove();
        Ball.bounce();
        meteor.move();
        meteor.bounce();
        border.move();
        winner.move();
        for (int x=0; x<size; x=x+1) {
            blocky[x].move();
        }
        if(paddle.rect.intersects(Ball.rect)){
            if (firstCrash==true) {
                Ball.dy = -Ball.dy;
                background = Toolkit.getDefaultToolkit().getImage("Space.jpg");
                double rand1 = Math.random();
                double rand2 = Math.random();
                firstCrash=false;
                if (rand1 + Ball.successRate > rand2 + paddle.successRate) {
                    Ball.dy -= 4;
                } else {
                    Ball.dy += 10;
                }
                if (rand1 + Ball.successRate > rand2 + paddle.successRate) {
                    Ball.dx += 2;
                } else {
                    Ball.dx -= 5;
                }
                paddle.isAlive = false;
            }

        }
        else{
            firstCrash=true;
        }
        if(Ball.dy>50){
            Ball.dy=50;
        }
        if(Ball.ypos<5){
            Ball.ypos=15;
            Ball.dy=Math.abs(Ball.dy);
        }
        for (int x=0; x<size; x=x+1) {

        if(Ball.rect.intersects(blocky[x].rect)){
            if (firstCrash==true) {
                Ball.dy = -Ball.dy;
                firstCrash=false;
                blocky[x].xpos=-100;
                blocksnumber = blocksnumber-1;

            }

        }
        if(!Ball.rect.intersects(paddle.rect)){
            firstCrash=true;
        }
        }


        if(Ball.rect.intersects(border.rect)){
            paddle.health = paddle.health - 34;


        }
        if(paddle.health < 0 ){
            paddle.dx +=1000;
            Ball.dx +=1000;
            border.height = 700;
            border.ypos = 0;
            border.xpos = 0;
            for (int x=0; x<size; x=x+1) {
                blocky[x].xpos = -100;
            }
        }

        if (blocksnumber<1){
            paddle.dx +=1000;
            Ball.dx +=1000;
            winner.height = 700;
            winner.ypos = 0;
            winner.xpos = 0;
        }
        if(Ball.ypos > 600){
            Ball.ypos = 300;
            Ball.xpos = 500;
            Ball.dy = -10;
            Ball.dx = -5;
        }
        if(paddle.ypos > 750){
            paddle.ypos = 300;
            paddle.xpos = 500;

        }
        if(paddle.ypos < -50){
            paddle.ypos = 300;
            paddle.xpos = 500;

        }
        if(paddle.xpos < -50){
            paddle.ypos = 300;
            paddle.xpos = 500;

        }
        if(paddle.xpos > 1050){
            paddle.ypos = 300;
            paddle.xpos = 500;

        }
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        g.setColor(new Color(182, 30 ,44 ));
        g.fillRect(0,0,paddle.health*10, 20);
        g.setColor(new Color(40, 160 ,244 ));
        g.fillRect(0,20,paddle.stamina*10, 20);
        g.drawImage(paddleImage, paddle.xpos, paddle.ypos, paddle.width+60, paddle.height, null);
        g.drawImage(meteorImage, meteor.xpos, meteor.ypos, meteor.width, meteor.height, null);
        g.drawImage(BallImage, Ball.xpos, Ball.ypos, Ball.width, Ball.height, null);
        g.drawImage(borderImage, border.xpos, border.ypos, border.width, border.height, null);
        g.drawImage(winnerImage, winner.xpos, winner.ypos, winner.width, winner.height, null);
        for (int x=0; x<size; x=x+1) {
            g.drawImage(blockyImage, blocky[x].xpos, blocky[x].ypos, blocky[x].width, blocky[x].height,null);
        }
        g.dispose();
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
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
        if (e.getKeyCode()==81) {
            if(paddle.stamina>0) {
                paddle.dy = 0;
                paddle.dx = -50;
                paddle.stamina = paddle.stamina -5;
            }
        }
        if (e.getKeyCode()==69) {
            if(paddle.stamina>0) {
                paddle.dy = 0;
                paddle.dx = 50;
                paddle.stamina = paddle.stamina -5;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode()==87){
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==83) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==65) {
            paddle.dy = 0;
            paddle.dx = -10;
        }
        if (e.getKeyCode()==68) {
            paddle.dy = 0;
            paddle.dx = 10;
        }
        if (e.getKeyCode()==69) {
            if(paddle.stamina>0) {
                paddle.dy = 0;
                paddle.dx = 50;
                paddle.stamina = paddle.stamina -2;
            }
        }
        if (e.getKeyCode()==81) {
            if(paddle.stamina>0) {
                paddle.dy = 0;
                paddle.dx = -50;
                paddle.stamina = paddle.stamina -2;
            }
        }
       // if (e.getKeyCode()==38) {
       //     Ball.dy = -10;

     //   }
      //  if (e.getKeyCode()==39) {

       //     Ball.dx = 10;
      //  }
       // if (e.getKeyCode()==40) {
       //     Ball.dy = 10;

       // }
       // if (e.getKeyCode()==37) {

       //     Ball.dx = -10;
       // }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==87){
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==83) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==65) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==68) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==69) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==81) {
            paddle.dy = 0;
            paddle.dx = 0;
        }
        if (e.getKeyCode()==82) {
            if(paddle.health<1){
                paddle.dx -=1000;
                Ball.dx -=1000;
                border.height = 10;
                border.ypos = 690;
                border.xpos = 0;
                winner.height = 0;
                winner.ypos = 700;
                winner.xpos = 0;
                paddle.health=100;
                paddle.dx = 5;
                paddle.dy = 20;
                blocksnumber = 10;
                background = Toolkit.getDefaultToolkit().getImage("Space.jpg");
                paddle.stamina = 100;
                for (int x=0; x<size; x=x+1) {
                    blocky[x] = new Block("block" + x, 30 + (x * (183/2)), 100);
                }
            }
            if(blocksnumber<1){
                paddle.dx -=1000;
                Ball.dx -=1000;
                border.height = 10;
                border.ypos = 690;
                border.xpos = 0;
                winner.height = 0;
                winner.ypos = 700;
                winner.xpos = 0;
                paddle.health=100;
                paddle.dx = 5;
                paddle.dy = 20;
                blocksnumber = 10;
                background = Toolkit.getDefaultToolkit().getImage("Space.jpg");
                paddle.stamina = 100;
                for (int x=0; x<size; x=x+1) {
                    blocky[x] = new Block("block" + x, 30 + (x * (183/2)), 100);
                }
            }
        }
      //  if (e.getKeyCode()==38) {
    //        Ball.dx = 0;
    //        Ball.dy = 0;
       // }
       // if (e.getKeyCode()==39) {
          //  Ball.dx = 0;
      //      Ball.dy = 0;
      //  }
       // if (e.getKeyCode()==40) {
       //     Ball.dx = 0;
        //    Ball.dy = 0;
       // }
       // if (e.getKeyCode()==37) {
      //      Ball.dx = 0;
       //     Ball.dy = 0;
       // }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (button.contains(e.getPoint())){
            System.out.println("hi");
        }
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
