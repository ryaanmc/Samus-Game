package application;

import javafx.scene.image.Image;


public class level {
	
	//Image Objects for backgrounds
	private Image titleImg, lvl1, lvl2, lvl3, lvl4;
	
	//Level Object Constructor
	public level()
	{
		//Title Image Object
		titleImg = new Image("file:images/mainScreen.png");
		
		//Preserve Title Image Ratio
		titleImg.isPreserveRatio();
		
		//All level backgrounds
		lvl1 = new Image("file:images/lobbyBg.png");
		lvl2 = new Image("file:images/mmBackground.png");
		lvl3 = new Image("file:images/bossBg.png");
		lvl4 = new Image("file:images/finalScreen.jpg");
	}
	
	//Get Image of level
	public Image getImage(int level)
	{
		
		//If statements for determining the background Image
		if(level == 0)
		{
			return titleImg;
		}
		else if(level == 1)
		{
			return lvl1;
		}
		else if(level == 2)
		{
			return lvl2;
		}
		else if(level == 3)
		{
			return lvl3;
		}
		else
		{
			return lvl4;
		}
	}

}
