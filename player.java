package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class player 
{
	//variables used in this class
	private Image imgPlayer;
	private Image[] imgRightRun = new Image[10],
			imgLeftRun = new Image[10], 
			imgRightRunExtended = new Image[10], 
			imgLeftRunExtended = new Image[10], imgStandRight = new Image [4],
			imgStandLeft = new Image [4], imgPointUpRight = new Image [10],
			imgPointUpLeft = new Image [10];
	private Image imgJumpLeftUp, imgJumpRightUp, imgJumpLeftDown, imgJumpRightDown, imgDead;
	private ImageView iviewPlayer;
	private int index = 0, index2 = -1;
	private int xPos, yPos;
	private double jumpForce;
	public static boolean grounded = true;
	public boolean dead = false;
	private final static int TermVel = 5;
	public final static double gravity = -9.8;
	public final static int EAST = 0;
	public final static int WEST = 1;
	private int direction = 0;

	//constructor
	public player()
	{
		//assigning the player's x and y position when first called
		xPos = 500;
		yPos = 500;

		//creating the player image and imageview and scaling the player to fit the screen
		imgPlayer = new Image("file:images/standRight0.png");
		iviewPlayer = new ImageView(imgPlayer);
		iviewPlayer.setPreserveRatio(true);
		iviewPlayer.setFitHeight(100);


		//for loops that assign each animation set
		for (int i = 0; i < imgRightRun.length; i++)
		{
			imgRightRun[i] = new Image("file:images/RunningRight" + i + ".png");

			imgLeftRun[i] = new Image("file:images/RunningLeft0" + i + ".png");

			imgRightRunExtended[i] = new Image ("file:images/RunningRightE0" + i + ".png");
			imgLeftRunExtended[i] = new Image ("file:images/RunningLeftE0" + i + ".png");

			imgPointUpRight[i] = new Image("file:images/PointUpRight0" + i + ".png");
			imgPointUpLeft[i] = new Image("file:images/PointUpLeft0" + i + ".png");

		}
		for (int i = 0; i < imgStandRight.length; i++) {
			imgStandRight[i] = new Image ("file:images/standRight" + i + ".png");
			imgStandLeft[i] = new Image ("file:images/standLeft" + i + ".png");
		}

		imgJumpLeftUp = new Image("file:images/jumpLeft0.png");
		imgJumpLeftDown = new Image("file:images/jumpLeft1.png");
		imgJumpRightUp = new Image("file:images/jumpRight0.png");
		imgJumpRightDown = new Image("file:images/jumpRight1.png");

	}

	//sets the players x position
	public void setX(int x) 
	{
		xPos = x;
		iviewPlayer.setX(xPos);
	}

	//sets the player y position
	public void setY(int y) 
	{
		yPos = y;
		iviewPlayer.setY(yPos);
	}
	
	//returns the players x position
	public int getX() 
	{
		return xPos;
	}

	//returns the players y position
	public int getY() 
	{
		return yPos;
	}

	//returns the width of the player
	public double getWidth()
	{
		return imgPlayer.getWidth();
	}

	//moves the player to the left and sets their direction
	public void moveLeft() 
	{
		xPos -= 7;
		direction = WEST;
		iviewPlayer.setX(xPos);
	}

	//moves the player to the right and sets their direction
	public void moveRight()
	{
		direction = EAST;
		xPos += 7;
		iviewPlayer.setX(xPos);
	}

	//makes the player jump a certain height into the air
	public void jump(double jumpForce, int runs)
	{
		//runs makes sure that this.jumpForce is only assigned to jumpForce the first time this method is called
		if(runs == 0)
		{
			grounded = false;
			this.jumpForce = jumpForce;
		}

		//makes sure the players jump force never exceeds a certain amount
		//decreases the jump force by a certain amount every time this method called
		if(this.jumpForce < TermVel)
		{
			this.jumpForce -= (gravity/100);
		}

		yPos += this.jumpForce;
		iviewPlayer.setY(yPos); 
	}
	
	//gets the direction the player is facing
	public int getDirection()
	{
		return direction;
	}
	
	//returns the players image without setting it to a new image
	public ImageView getImage2() {
		return iviewPlayer;
	}

	//updates the players image and then returns the new image
	//determines which set of animations the player will follow
	public ImageView getImage(int afk, boolean extend)
	{
		if(dead == false)
		{
			//index is for the moving array
			//index 2 if for the idle animation array, it's called later in
			
			index++;
			if(index > imgRightRun.length-1)
			{
				index = 0;
			}

			if (index2 >= 3) 
			{
				index2 = -1;
			}

			double futureJF = this.jumpForce - (gravity/100);
			//runs everytime this method is called
			//afk == -1 means the player is standing still
			//afk == -2 means the player is moving(doesn't matter which way)
			//so the run animation direction is determind by the idle animation direction
			//afk == -3 means the player is jumping (doesn't matter which way)
			if (direction == EAST && extend == false)
			{
				if (afk == -1) {
					index2++;
					iviewPlayer.setImage(imgStandRight[index2]);

				}
				else if(afk == -2) {

					iviewPlayer.setImage(imgRightRun[index]);

				}
				else if(afk == -3)
				{
					if(futureJF < 0)
					{
						iviewPlayer.setImage(imgJumpRightUp);
					}
					else if(futureJF > 0)
					{
						iviewPlayer.setImage(imgJumpRightDown);
					}
				}
				if (afk == -5) {

					iviewPlayer.setImage(imgPointUpRight[index]);

				}

			}
			else if (direction == EAST && extend == true  && grounded == true) {

				iviewPlayer.setImage(imgRightRunExtended[index]);

			}
			if(direction == WEST && extend == false)
			{

				if (afk == -1) {
					index2++;
					iviewPlayer.setImage(imgStandLeft[index2]);

				}
				
				else if(afk == -2){
					iviewPlayer.setImage(imgLeftRun[index]);
				}
				
				else if(afk == -3)
				{
					
					if(futureJF > 0)
					{
						iviewPlayer.setImage(imgJumpLeftUp);
					}
					
					else if(futureJF < 0)
					{
						iviewPlayer.setImage(imgJumpLeftDown);
					}	
				}

				if (afk == -5) {

					iviewPlayer.setImage(imgPointUpLeft[index]);

				}

			}
			else if(direction == WEST && extend == true && grounded == true)
			{

				iviewPlayer.setImage(imgLeftRunExtended[index]);


			}
		}
		
		return iviewPlayer;
	}

	//kills the player so they can no longer move
	public void killPlayer() {
		dead = true;
	}

}
