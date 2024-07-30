package application;

import java.util.Random;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//a parent class for both the enemy types to inherit
public abstract class enemy {

	//creating common variables used in both classes
	Random rnd; 
	int dir; 
	int index, yPos, xPos, speed; 
	Image imgEnemy, imgEnemyLeft;
	ImageView ivEnemy;
	double width, height;
	final int EAST = 0, WEST = 1;
	boolean dead= false;
	
	//series of methods in both classes
	//explained more in detail in each class
	public abstract void setLocation(int width, int height);

	public abstract Node getImage();

	public abstract void move();

	public abstract int getX();

	public abstract int getWidth();

	public abstract void setDirection(int dir);

	public abstract int getDirection();
	
	public abstract void attack(int x, int y);
}
