package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class bullet
{
	//Creating and instantiating variables and objects
	private Image imgBEast, imgBWest, imgBEastUp, imgBWestUp;
	private ImageView iviewBullet;
	public boolean fired, up;
	private double xPos, yPos, width, height; 
	public int direction = 0; 
	public final static int EAST = 0, WEST = 1;

	//Bullet Constructor
	public bullet()
	{
		//Bullet Images
		imgBWest = new Image("file:images/projectile.png");
		imgBEast = new Image("file:images/projectileLeft.png");
		imgBEastUp = new Image("file:images/projectileRightUp.png");
		imgBWestUp = new Image("file:images/projectileLeftUp.png");

		//Set fired to false
		fired = false;

		//Setting initial position of the bullet
		xPos = -200;
		yPos = 0; 
		
		//Storing the Width and height of the bullet
		width = imgBEast.getWidth();
		height = imgBEast.getHeight();
		
		//Setting the initial direction of the bullet
		direction = EAST;

		//Initializing the imageView object of the bullet and setting various properties
		iviewBullet = new ImageView(imgBWest);
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
		iviewBullet.setPreserveRatio(true);
		iviewBullet.setFitHeight(35);
	}

	//Get Direction of the bullet
	public int getDirection()
	{
		return direction;
	}
	
	//Get Height of the bullet
	public double getHeight()
	{
		return height;
	}
	
	//Get Width of the bullet
	public double getWidth()
	{
		return width;
	}
	
	//Get the x-position of the bullet
	public double getX()
	{
		return xPos;
	}
	
	//Get the y-position of the bullet
	public double getY()
	{
		return yPos;
	}
	
	//Check if bullet is fired
	public boolean isFired()
	{
		return fired;
	}
	
	//Move the bullet 
	public void move(boolean pointUp, int runs)
	{
		//Else-if statements checking for the direction of the bullet
		if(runs == 1)
		{
			up = pointUp;
		}
		if(up == false)
		{
			if (direction == EAST)
			{
				xPos += 10;
			}
			else
			{
				xPos -= 10;
			}
		}
		else
		{
			yPos -= 10;
			if (direction == EAST)
			{
				xPos += 10;
			}
			else
			{
				xPos -= 10;
			}
		}

		//Setting the x-position and y-position of the bullet
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
	}
	
	//Method for setting the position of the bullet
	public void setPosition(double playerX, double playerY, int dir, boolean up)
	{
		direction = dir;
		this.up = up;

		if(up == false)
		{
			if (direction == EAST)
			{
				xPos = playerX + 75;
				yPos = playerY + 40;
			}
			else
			{
				xPos = playerX;
				yPos = playerY + 40;
			}
		}
		else
		{
			if (direction == EAST)
			{
				xPos = playerX + 75;
				yPos = playerY + 40;
			}
			else
			{
				xPos = playerX;
				yPos = playerY + 60;
			}
		}

		fired = true;
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
	}
	
	//Method for setting the x-position of the bullet
	public void setX(int x)
	{
		xPos = x;
		iviewBullet.setX(xPos);
	}
	
	//Method for setting the y-position of the bullet
	public void setY(int y)
	{
		yPos = y;
		iviewBullet.setY(yPos);
	}
	
	//Method for stopping the bullet
	public void stopBullet() {

		fired = false;
	}

	//Check if the bullet is off the screen
	public boolean isOffScreen(double edge) {
		
		//Boolean for if bullet is off the screen
		boolean offScreen = false;

		//Check if the xPos of the bullet is off the screen
		if (xPos >= edge || xPos + width <= 0)
		{
			offScreen = true;
			fired = false;
		}
		else
		{
			offScreen = false;
		}

		return offScreen;
	}

	//Method for setting the Image of the bullet
	public void setImage(int dir, int runs) {
		
		//If-else statements to determine the Image of the bullet
		if(runs == 1)
		{
			direction = dir;
		}
		if(up == false)
		{
			if (direction == EAST)
			{
				iviewBullet.setImage(imgBEast);
			}
			else
			{
				iviewBullet.setImage(imgBWest);
			}
		}
		else
		{
			if (direction == EAST)
			{
				iviewBullet.setImage(imgBEastUp);
			}
			else
			{
				iviewBullet.setImage(imgBWestUp);
			}
		}
	}
	
	//Get the ImageView Object of the Bullet
	public ImageView getImage()
	{
		return iviewBullet;
	}

}
