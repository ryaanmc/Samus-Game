package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class fireball {

	//Image Object for fireball
	private Image imgFire;
	
	//ImageView Object for fireball
	private ImageView iviewFire;
	
	//Int fields for fireball
	private int xPos, yPos, width, height;
	
	//Random Object
	private Random rnd;

	//fireball Constructor
	public fireball() {
		
		rnd = new Random();//Initialize Random Object
		
		xPos = -150;//Initial xPos of fireball
		yPos = -150;//Initial yPos of fireball
		
		//Initialize Image Object for fireball
		imgFire = new Image ("file:images/fireball.png");
		
		//Initialize ImageView Object for fireball
		iviewFire = new ImageView(imgFire);
		
		width = (int) imgFire.getWidth();//Width of fireball
		height = (int) imgFire.getHeight();//Height of fireball
		
	}

	//Get ImageView Method for fireball
	public ImageView getImage() {

		return iviewFire;//Return ImageView Object
	}

	//Set x-position Method for fireball
	public void setX (int x) {

		xPos = x;//Set x-position to parameter
	}

	//Set y-position Method for fireball
	public void setY (int y) {

		yPos = y;//Set y-position to parameter
	}

	//Get x-position Method for fireball
	public int getX() {

		return xPos;//Return x-position for fireball
	}

	//Get y-position Method for fireball
	public int getY() {

		return yPos;//Return y-position for fireball
	}

	//Get Height Method for fireball
	public int getHeight() {

		return height;//Return height for fireball

	}

	//Get Width Method for fireball
	public int getWidth() {

		return width;//Return width for fireball

	}

	//Move Method for fireball
	public void move (int pixels) {
		yPos += pixels;//Decrease x-position of 
		iviewFire.setY(yPos);//Set x-position of ImageView Object for fireball
		iviewFire.setX(xPos);

	}

	//Set Location Method for fireball
	public void setLocation (int frameWidth, int frameHeight) {

		yPos = 0-height;//Set y-position of fireball to 0
		iviewFire.setY(yPos);//Set y-position of ImageView Object of fireball
		xPos = rnd.nextInt((int) (frameWidth - width));//Set x-position of fireball

	}

}
