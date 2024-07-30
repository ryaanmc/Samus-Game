package application;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class boss {
	
	//Creating and instantiating variables and objects
	private int yPos, xPos, dir, midY, sway = 0, xSpeed = 5, ySpeed = 2; 
	private Image imgBoss;
	private ImageView ivBoss;
	private double width, height;
	private final int EAST = 0, WEST = 1;
	
	//Constructor of the boss Object
	public boss()
	{
		//Initial Image and ImageView Objects
		imgBoss = new Image("file:images/boss.png");
		ivBoss = new ImageView(imgBoss);
		
		//Initial direction of the Boss
		dir = EAST;
		
		//Properties of the ImageView of the ImageView Object of the boss
		ivBoss.setPreserveRatio(true);
		ivBoss.setFitHeight(200);
		
		//Variables to store width and height of the boss
		width = 150;
		height = 200;
		
		//Initial x-position and y-position of the boss
		xPos = 400;
		yPos = 10;
		
		midY = 30;
		
	}
	
	//Set the location of the Boss
	public void setLocation()
	{
		//If the level equals the boss level
		if(Main.levelbg == 3)
		{
			xPos = 472-(int)width;
			yPos = 230;
		}
		else
		{
			xPos = -150;
		}
		
		//Set the ImageView to the xPos and yPos
		ivBoss.setX(xPos);
		ivBoss.setY(yPos);
	}

	//Method for returning the Boss image
	public  Node getImage()
	{
		return ivBoss;
	}

	//Method for moving the Boss
	public  void move()
	{
		//If the Boss Health is less than or equal to 100 change the horizontal and vertical speeds
		if(Main.bossHP <= 100)
		{
			xSpeed = 8;
			ySpeed = 5;
		}
		
		//If the direction of the Boss is East
		if (dir == EAST)
		{
			xPos += xSpeed;

		}
		
		//Else-if the Direction of the Boss is West
		else if (dir == WEST)
		{
			xPos -= xSpeed;
		}
		
		//If the Sway value equals to 0
		if(sway == 0)
		{
			//Increase the yPos of the Boss to the vertical speed
			yPos += ySpeed;
			
			//If the yPos is greater than the midY + 50
			if(yPos > midY + 50)
			{
				sway = 1;
			}
		}
		
		//Else if sway equals to 1
		else if(sway == 1)
		{
			//Decrease the yPos of the Boss to the verical speed
			yPos -= ySpeed;
			
			//If the yPos is less than the midY - 30
			if(yPos < midY - 30)
			{
				sway = 0;
			}
		}
		
		//Set the xPos and yPos to the ImageView Objects of the Boss
		ivBoss.setX(xPos);
		ivBoss.setY(yPos);
	}

	//Method for the x-position of the boss
	public  int getX()
	{
		return xPos;
	}
	
	//Method for the y-position of the boss
	public int getY()
	{
		return yPos;
	}

	//Method for the width of the boss
	public  int getWidth()
	{
		return (int)width;
	}

	//Method for setting the direction of the boss
	public  void setDirection(int direction)
	{
		dir = direction;
	}

	//Method for getting the direction of the boss
	public  int getDirection()
	{
		return dir;
	}
}
