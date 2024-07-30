package application;


import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class flyingEnemy extends enemy{

	//creating and instantiating variables only used in this class
	int swoop = 0;
	double downForce = 5;

	public flyingEnemy()
	{
		//instantiating variables used for this class
		rnd = new Random();
		dir = EAST;
		index = rnd.nextInt(4);
		speed = rnd.nextInt(5) + 2;

		xPos = 0; 
		yPos = 0;

		//assigns the image to the flying enemies 
		imgEnemy = new Image("file:images/metroid.png");

		//setting up the image view for the flying enemy
		ivEnemy = new ImageView(imgEnemy);
		ivEnemy = new ImageView(imgEnemy);
		ivEnemy.setPreserveRatio(true);
		ivEnemy.setFitHeight(30);

		width = imgEnemy.getWidth();
		height = imgEnemy.getHeight();
	}

	//sets the enemy's x position
	public void setX(int x) {

		xPos = x;
		ivEnemy.setX(xPos);
	}

	//sets the enemy's y position
	public void setY(int y) {

		yPos = y;
		ivEnemy.setY(yPos);
	}

	//returns the enemy's x position
	public int getX() {

		return xPos;
	}

	//returns the enemy's y position
	public int getY() {

		return yPos;
	}

	// getWidth method to return the width.
	public int getWidth()
	{
		return (int)width;
	}

	// getHeight method to return the height. 
	public double getHeight()
	{
		return height;
	}

	// setLocation method 
	public void setLocation(int frameWidth, int frameHeight)
	{
		//randomizes their y pos so they all fly at slightly different heights
		yPos = rnd.nextInt(150)+200;

		//makes sure they are only on screen during the second level
		if(Main.levelbg == 1)
		{
			xPos = -150;
		}
		else if(Main.levelbg == 2)
		{
			xPos = rnd.nextInt(100)+300;
		}

		ivEnemy.setX(xPos);
		ivEnemy.setY(yPos);	
	}


	// getDirection method
	public int getDirection()
	{
		return dir;
	}

	// setDirection method
	public void setDirection(int dir)
	{
		this.dir = dir; 
		if(dir == -1)
		{
			dead = true;
		}
	}

	// move method
	public void move()
	{
		//will only move if the enemy is alive
		if(dead == false)
		{
			if (dir == EAST)
			{
				xPos += speed;

			}
			else if (dir == WEST)
			{
				xPos -= speed;
			}
		}

		ivEnemy.setX(xPos);

	}

	//returns the enemy's image
	public ImageView getImage()
	{
		ivEnemy.setImage(imgEnemy);		
		return ivEnemy;
	}

	//attack method that is only used in this class
	public void attack(int x, int y)
	{
		//checks if the flying enemy is in a certain range above the player's head
		//if they are, then the first part of the swoop attack will occur
		//the flying enemy's y position will rapidly decrease until they hit the player, or miss and hit the floor
		//once they hit the floor, they will swoop back up to their old y position
		if((xPos < x + 100 && xPos > x) || swoop == 1)
		{
			swoop = 1;

			yPos += 5;

			if((yPos == y - 10 && player.grounded == true)|| yPos >= Main.Ground)
			{
				swoop = 2;
			}
		}
		else if(swoop == 2)
		{
			yPos -= 10;

			if(yPos <= 300)
			{
				swoop = 0;
			}
		}

		ivEnemy.setY(yPos);

	}
}
