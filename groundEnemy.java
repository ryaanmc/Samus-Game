package application;


import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//groundEnemy classes inherits its behaviours from the enemy parent class

public class groundEnemy extends enemy{

	public groundEnemy()
	{
		//initializing variables inherited from the enemy class
		rnd = new Random();
		dir = EAST;
		speed = 2;

		xPos = 0; 
		yPos = 0;

		//assign images depending on the direction the enemy is facing
		imgEnemy = new Image("file:images/glider.png");
		imgEnemyLeft = new Image("file:images/gliderLeft.png");

		//setting up the image view for the enemy
		ivEnemy = new ImageView(imgEnemy);
		ivEnemy = new ImageView(imgEnemy);
		ivEnemy.setPreserveRatio(true);
		ivEnemy.setFitHeight(100);

		width = imgEnemy.getWidth();
		height = 100;

	}

	//setting the x position for the enemy
	public void setX(int x) {
		xPos = x;
		ivEnemy.setX(xPos);
	}

	//setting the y position for the enemy
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
		return (int) width;
	}

	// getHeight method to return the height. 
	public double getHeight()
	{
		return height;
	}

	// setLocation method 
	public void setLocation(int frameWidth, int frameHeight)
	{
		//will always place the ground enemies on the ground
		yPos = (int) (Main.Ground - height);

		//the ground enemies shouldn't be visible in the first level so they are hidden off screen here
		if(Main.levelbg == 1)
		{
			xPos = -150;
		}
		//their x position is now set to a positive bound so they player can see and interact with them
		else if(Main.levelbg >= 2)
		{
			xPos = (rnd.nextInt((frameWidth/2) - (int) width)) + (frameWidth/2);
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

	//gets the enemies image depending on the direction they are facing
	public ImageView getImage()
	{
		if(dir == 1)
		{
			ivEnemy.setImage(imgEnemyLeft);
		}
		else
		{
			ivEnemy.setImage(imgEnemy);
		}

		return ivEnemy;
	}

	//blank method inherited
	public void attack(int x, int y) {
	}

}
