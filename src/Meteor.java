import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 * Edits by mblair on 10/27/2025
 */
public class Meteor {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;           //a boolean to denote if the hero is alive or dead
    public Rectangle rect;
    public double successRate;
    public int health;


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Meteor(String pName, int pXpos, int pYpos, double psuccess) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 11;
        dy = 8;
        width = 60;
        height = 60;
        isAlive = true;
        successRate=psuccess;
        health = 100;
 
    }

    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
        rect=new Rectangle(xpos+width/2, ypos + height/2, width,height);
    }

    public void moove() { // move
        xpos = xpos + dx/2;
        ypos = ypos + dy*2;
        rect=new Rectangle(xpos+width/2, ypos + height/2, width,height);
    }

    public void bounce() {
        if(xpos>=1000-width) {
            dx=-dx;}
        if(xpos<=0) {
            dx=-dx;}
        if(ypos>=700-height) {
            dy=-dy;}
        if(ypos<=0) {
            dy=-dy;}
    }
    public void wrap() {
        if(xpos>1000-width) {
            xpos=xpos-1000;}
        if(xpos<0) {
            xpos=xpos+1000;}
        if(ypos>700-height) {
            ypos=ypos-700;}
        if(ypos<0) {
            ypos=ypos+700;}

    }
}






