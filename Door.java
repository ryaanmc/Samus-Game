package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Door {

	//creating variables needed for this class
	private int xPos =900, xPos2 = 970;

	Image imgDoor[] = new Image [5];
	ImageView iviewDoor = new ImageView();

	Image imgDoor2[] = new Image[5];
	ImageView iviewDoor2 = new ImageView();

	int index = -1, index2 = 5, index3 = -1;

	//constructor
	public Door() {
		//assigns both the doors' x position
		iviewDoor.setX(400);
		
		iviewDoor2.setX(400);

		//creating the door animation for both direction
		for(int i = 0; i < 5; i++) {
			imgDoor[i] = new Image ("file:images/doors" + i + ".png");
		}

		for(int i = 0; i < 5; i++) {
			imgDoor2[i] = new Image ("file:images/doorsR" + i + ".png");
		}

		iviewDoor.setPreserveRatio(true);
		iviewDoor.setFitHeight(150);

		iviewDoor2.setPreserveRatio(true);
		iviewDoor2.setFitHeight(150);

	}

	//returns the door image without updating it
	public ImageView getImage2() {
		return iviewDoor;
	}

	//updates the door image and returns the updated image
	public ImageView getImage() {

		if(index != 4)
		{
			index++;
		}

		iviewDoor.setImage(imgDoor[index]);
		iviewDoor.setX(xPos);
		iviewDoor.setY(460);

		return iviewDoor;
	}
	
	//returns the x position for this door
	//y position is not necessary since it is set to a certain y position and never changes
	public int getPosition() {
		return xPos;
	}

	//moves the door during the entry animation to level 2
	public void move() {
		xPos -= 5;
		iviewDoor.setX(xPos);
	}

	//returns the image for door after updating it
	public ImageView getImage3(int decide) {

		//This code is to change the index to make the "Door2" aka Right facing door appear to rebuild itself
		if (decide == 1) {
			if (index2 != 0) {
				index2--;
			}
			iviewDoor2.setImage(imgDoor2[index2]);
		}
		
		//If the value passed in is other than 1 
		else if (decide == 2){
			
			if(index3 != 4)
			{
				index3++;
			}
			iviewDoor2.setImage(imgDoor2[index3]);
		}

		iviewDoor2.setX(xPos2);
		iviewDoor2.setY(460);

		return iviewDoor2;
	}

	//returns the second doors image
	public ImageView getImage4() {
		return iviewDoor2;
	}

	//moves the second door in unison with the other door so it looks nice
	public void move2Left() {
		xPos2 -= 5;
		iviewDoor2.setX(xPos2);
	}
	public void move2Right() {
		xPos2 += 5;
		iviewDoor2.setX(xPos2);
	}



}
