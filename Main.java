/*ICS4U1 CPT: Return of Samus
 * Group Member Names: Mohammad Arshad and Ryaan Chaudhary
 * Teacher’s Name: Mr Conway
 * Program Description: In this game you play as Samus and your goal is to try to beat 
 the boss at the end of the game and achieve the highest score possible. However, it is not easy as it may sound.
 You must go through a level with both flying enemies and ground enemies before reaching the boss level. You must utilize
 the diaganol shots of Samus to truly win the game. Samus only has 1 Hp in this game so the player must be on their toes at all times.
 The 2 types of enemies will attack you from all angles, so the player must be ready to attack while dodging the enemy attacks.
 Once the player beats all the enemies, they can move on to the boss which tests their knowledge, speed, and skills. The player must 
 shoot the boss in the eye 20 times to kill it and will have to dodge a barrage of fireballs launched from the top of the screen. 
 The longer the player takes to win, the lower the score the player will recieve, so the player will have to get through both levels as
 fast as possible. Once the player beats the game, they get to see their top 5 scores which save between games.
 * Program Details: This program uses fade transitions which were not taught in class. The website we got this information form is
 https://docs.oracle.com/javafx/2/api/javafx/animation/FadeTransition.html. That is the only piece of code that was used in this
 program that was not taught in class.
 */
package application;

//importing all important and relevant packages into this class
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	//creating and instantiating all variables used in this program.
	//variables are created outside of main so it can be accessed anywhere in this class
	//some variables are repeated throughout classes, so they have been moved to this section
	//and are made public for all classes to access
	private AnimationTimer timer;
	private boolean goLeft, goRight, jump = false, extend = false, right = false, left = false, pointUp = false, checkFade = false, checkUnfade = false, doorBroken = false, moveDoor = false, invincible = false;
	private int runs = 0, bulletRuns = 0, doorNum, firecounter = -1, time = 0, score = 20000, phase = 900,finalScore;
	private Timeline jumpTimer, moveTimer, idleTimer, jumpAnimTimer, bulletTimer, doorTimer, fireballTimer;
	private double oldY;
	private Image imgBg;
	private ImageView iviewBg;
	private groundEnemy[] gEnemies = new groundEnemy[4];
	private flyingEnemy[] fEnemies = new flyingEnemy[4];
	public static int levelbg = 0, bossHP = 200;
	private Button playBtn = new Button();
	private Pane root = new Pane();
	private Scene scene = new Scene(root, 943, 683);

	private AudioClip shoot = new AudioClip("file:sounds/shoot.mp3");
	private AudioClip death = new AudioClip("file:sounds/death.mp3");

	private Alert controls = new Alert(AlertType.INFORMATION);
	public final static int Ground = 600;

	private ArrayList <fireball> fireballs = new ArrayList <fireball>();

	//creating an object for every relevant class used in this class(Main)
	private boss boss = new boss();
	private player player = new player();
	private level level = new level();
	private bullet bullet = new bullet();
	private Door door = new Door();

	// Declare and initialize a File, Media and MediaPlayer object
	//this is used for the music throughout the entire game until the end game screen
	private File music = new File("sounds/MMmusic.mp3");
	private Media media = new Media(music.toURI().toString());
	private MediaPlayer mplayer = new MediaPlayer(media);

	// Declare and initialize a File, Media and MediaPlayer object
	//this is used for the end game screen where the high scores are displayed
	private File credits = new File("sounds/MMCredits.mp3");
	private Media media2 = new Media(credits.toURI().toString());
	private MediaPlayer mplayer2 = new MediaPlayer(media2);

	String lineOfText;

	FadeTransition fade;

	//creating rectangles used for collisions and setting them for every enemy, the boss, and the player
	Rectangle gEnemyRect[] = new Rectangle[4];
	Rectangle fEnemyRect[] = new Rectangle[5];
	Rectangle playerRect;
	Rectangle bossRect;
	Rectangle fadeRect;
	Rectangle healthBar;

	public void start(Stage primaryStage) {
		try {		

			// Play the background Music audio file
			mplayer.play();

			//setting up the hitbox for the boss
			//setting its x and y position, and setting its height and width 
			//this hitbox is placed directly on the eye of the boss
			bossRect = new Rectangle();
			bossRect.setX(-100);
			bossRect.setY(-100);
			bossRect.setWidth(45);
			bossRect.setHeight(45);

			// create a Rectangle object to represent the health bar
			healthBar = new Rectangle();

			// set the initial width of the rectangle to represent full health
			healthBar.setWidth(bossHP);

			// set the height of the rectangle
			healthBar.setHeight(30);

			healthBar.setX(-1000);
			healthBar.setY(-1000);

			// set the color of the rectangle to green
			healthBar.setFill(Color.GREEN);

			//Background Image Object
			imgBg = new Image("file:images/mmBackground.png");

			//Background ImageViwe Object
			iviewBg = new ImageView(level.getImage(levelbg));

			//Background ImageView Object Properties
			iviewBg.isPreserveRatio();//To preserve the ratio of the image's width and height
			iviewBg.setFitHeight(imgBg.getHeight());//Set background ImageView object to certain height
			iviewBg.setFitWidth(imgBg.getWidth());//Set background ImageView object to certain height

			playBtn.setText("Go");//Set Play Button Text

			// Preferred Size is 200 x 50
			playBtn.setPrefSize(200, 50);

			// Located on 20 on the Y axis
			playBtn.setLayoutY(imgBg.getHeight() - 200);

			// Located at half the pane width subtracted from half button preferred width
			playBtn.setLayoutX(imgBg.getWidth() - 250);

			//If play button is clicked
			playBtn.setOnAction(e-> titleSelect(0));

			//Set Nodes to Pane
			root.getChildren().addAll(iviewBg, playBtn);

			//For loop for Initializing, setting location, and adding enemies to pane
			for (int i = 0; i < gEnemies.length; i++)
			{
				gEnemies[i] = new groundEnemy();
				gEnemies[i].setLocation((int)imgBg.getWidth(), (int)imgBg.getHeight());
				gEnemyRect[i] = new Rectangle();
				gEnemyRect[i].setX(-100);
				gEnemyRect[i].setY(-100);
				gEnemyRect[i].setWidth(50);
				gEnemyRect[i].setHeight(100);

				fEnemies[i] = new flyingEnemy();
				fEnemies[i].setLocation((int)imgBg.getWidth(), (int)imgBg.getHeight());
				fEnemyRect[i] = new Rectangle();
				fEnemyRect[i].setX(-100);
				fEnemyRect[i].setY(-100);
				fEnemyRect[i].setWidth(40);
				fEnemyRect[i].setHeight(30);
			}

			//adds the boss to the screen
			root.getChildren().addAll(boss.getImage());

			//add the health bar to the scene
			root.getChildren().add(healthBar);

			//this method is only called to set the boss off screen so it can be summoned
			//once the player hits the final level
			boss.setLocation();

			//Initialize Player's Rectangle Object for collision
			playerRect = new Rectangle();
			playerRect.setWidth(40);//Set the width of player's rectangle
			playerRect.setHeight(70);//Set the height of player's rectangle

			primaryStage.setScene(scene);//Setting scene of the stage
			primaryStage.setTitle("Return of Samus");//Setting the title of the game
			primaryStage.show();//Showing the stage

			//Method to let the scene to listen for any key being pressed
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle (KeyEvent e)
				{
					//If the player is not dead
					if(player.dead == false)
					{
						
						//If The Up-arrow key is pressed
						if (e.getCode() == KeyCode.UP)
						{
							//Set Extend Samus's gun boolean to false
							extend = false;

							//If player is on the ground
							if (player.grounded) {

								//Set the pointUp boolean to true
								pointUp = true;
							}

							//Stop the jump timer
							jumpAnimTimer.stop();
						}

						//If the right-arrow key is pressed
						if (e.getCode() == KeyCode.RIGHT)
						{
							//Set Extend Samus's gun boolean to false
							extend = false;

							//Stop the timer responsible of running the idle animation
							idleTimer.stop();

							//Stop the timer responsible of running the jump animation
							jumpAnimTimer.stop();

							//Set going Right boolean to true
							goRight = true;

							//Set Right boolean to true
							right = true;
						}

						//If the Left-arrow key is pressed
						else if (e.getCode() == KeyCode.LEFT)
						{
							//Set Extend Samus's gun boolean to false
							extend = false;

							//Stop the timer responsible of running the idle animation
							idleTimer.stop();

							//Stop the timer responsible of running the jump animation
							jumpAnimTimer.stop();

							//Set going Left boolean to true
							goLeft = true;

							//Set Left boolean to true
							left = true;
						}

						//If the Space bar is pressed
						else if(e.getCode() == KeyCode.SPACE)
						{

							//If the player has not jumped
							if(jump == false)
							{
								//Set the oldY value to the player's y-value
								oldY = player.getY();

								//Set runs variable to 0
								runs = 0;

								//Play the jump timer
								jumpTimer.play();

								//Play the timer responsible for running the Jumping animation
								jumpAnimTimer.play();

								//Stop the timer responsible for running the running the idle animation
								idleTimer.stop();

								//Set the pointUp boolean to false
								pointUp = false;
							}

						}

						//If the D key is pressed
						if (e.getCode() == KeyCode.D)
						{
							shoot.play();

							//If player is moving left or right
							if(left || right)
							{
								//Set Extend Samus's gun boolean to true
								extend = true;
							}

							//If the Bullet is not fired
							if (bullet.isFired() == false)
							{
								//Set the bulletRuns value to 1
								bulletRuns = 1;

								// Place the bullet at the player and run bullet animation timer. 
								bullet.setPosition(player.getX() + 30, player.getY()-20, player.getDirection(), false);
							}

							//Stop the timer responsible for the jumping animation
							jumpAnimTimer.stop();

							//Play the timer responsible for moving the bullet
							bulletTimer.play();
						}
					}
				}
			});

			//Method to let the scene to listen for any key being released
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle (KeyEvent e)
				{

					//If the Up-key has been released
					if (e.getCode() == KeyCode.UP)
					{
						//Set pointUp boolean to false
						pointUp = false;

						//Set Extend Samus's gun boolean to false
						extend  = false;

					}

					//If the Right-key has been released
					if (e.getCode() == KeyCode.RIGHT)
					{
						//Set right boolean to false
						right = false;

						//Set Extend Samus's gun boolean to false
						extend  = false;

						//Set goRight boolean to false
						goRight = false;

						//If the player is not moving left or right
						if (left == false && right == false) {

							//Play the timer responsible for running the idle animation
							idleTimer.play();
						}

					}

					//If the left key has been released 
					if (e.getCode() == KeyCode.LEFT)
					{
						//Set left boolean to false
						left = false;

						//Set Extend Samus's gun boolean to false
						extend = false;

						//Set goLeft boolean to false
						goLeft = false;

						//If the player is not moving
						if (left == false && right == false) {

							//Play the timer responsible for running the idle timer
							idleTimer.play();
						}
					}

					//If the D key has been released
					if (e.getCode() == KeyCode.D)
					{
						//If the player is not moving
						if (left == false && right == false) {

							//Play the timer responsible for running the idle animation
							idleTimer.play();
						}
					}
				}
			});

			//Initializng the AnimationTimer Object
			timer = new AnimationTimer(){
				public void handle (long now)
				{
					if(levelbg == 2 || levelbg == 3)
					{
						time++;
					}

					//If levelbg is equals to 1
					if(levelbg == 2)
					{
						//For loop for moving the enemies
						for (int i = 0; i < 4; i++)
						{
							gEnemies[i].move();
							gEnemyRect[i].setX(gEnemies[i].getX() + 40);
							gEnemyRect[i].setY(gEnemies[i].getY());

							//the flying enemies can swoop down to attack the player
							//the attack method is constantly called but can only occur in certain cases
							fEnemies[i].move();
							fEnemies[i].attack(player.getX(), player.getY());
							fEnemyRect[i].setX(fEnemies[i].getX());
							fEnemyRect[i].setY(fEnemies[i].getY());


							// Check if we left the RIGHT side of the room. 
							if (gEnemies[i].getX() + gEnemies[i].getWidth() >= root.getWidth())
							{
								gEnemies[i].setDirection(gEnemies[i].WEST);

							}
							// Check if we LEFT the left side of the room. 
							else if (gEnemies[i].getX() <= 0)
							{
								gEnemies[i].setDirection(gEnemies[i].EAST);

							}
							// Check if a ground enemy collies with the player. 
							if ((gEnemyRect[i].getBoundsInParent().intersects(playerRect.getBoundsInParent()) ||fEnemyRect[i].getBoundsInParent().intersects(playerRect.getBoundsInParent())) && invincible == false)
							{
								//if the player colides with the enemy then the player dies and the game ends
								//an alert will the pop up and tell the player that the game is over and will quit the program 
								player.killPlayer();
								timer.stop();//Stop the Animation Timer 

								// Used to run the new alert. 
								Platform.runLater(new Runnable() {
									public void run()
									{
										death.play();
										mplayer.stop();
										mplayer2.play();
										Alert alert = new Alert(AlertType.ERROR);
										alert.setHeaderText(null);
										alert.setContentText("\nGAME OVER!");
										alert.setTitle("enemy Game");
										alert.showAndWait();
										System.exit(0);
									}
								});
							}

							// Check if the enemy left the RIGHT side of the room. 
							if (fEnemies[i].getX() + fEnemies[i].getWidth() >= root.getWidth())
							{
								fEnemies[i].setDirection(fEnemies[i].WEST);

							}
							// Check if the enemy left the left side of the room. 
							//if this is met it will flip the enemies direction
							else if (fEnemies[i].getX() <= 0)
							{
								fEnemies[i].setDirection(fEnemies[i].EAST);

							}
						}
					}
					//checks if the player has made it to the boss level
					else if(levelbg == 3)
					{
						//moves the boss left and right and up and down
						boss.move();
						bossRect.setX(boss.getX()+ 45);
						bossRect.setY(boss.getY()+ 100);

						//sets the bosses health bar on top of them and will stay there until the boss dies
						healthBar.setY(boss.getY()- 50);
						healthBar.setX(boss.getX()- 20);

						//prevents the boss from leaving the right side of the screen
						if (boss.getX() + boss.getWidth() >= root.getWidth())
						{
							boss.setDirection(1);
						}
						//prevents the boss from leaving the left side of the screen
						else if (boss.getX() <= 0)
						{
							boss.setDirection(0);
						}

						//a for loop that runs for every fireball in the fireballs arraylist
						//this for loop is responsible for moving the fireballs downwards and checking if it hit the player
						for (int i = 0; i < fireballs.size(); i++)
						{
							fireballs.get(i).move(10);

							fireballs.get(i).getImage();

							if (fireballs.get(i).getY() >= root.getHeight())
							{
								root.getChildren().remove(fireballs.get(i).getImage());

								//Remove fireball Object from fireball ArrayList
								fireballs.remove(i);
								firecounter--;


							}
							//If Player has collided with a fireball
							if(playerRect.getBoundsInParent().intersects(fireballs.get(i).getImage().getBoundsInParent())) 
							{ 
								//Stop the AnimationTimer
								timer.stop();

								//Remove Player Image from the pane
								root.getChildren().remove(player.getImage2());
								
								// Used to run the new alert. 
								Platform.runLater(new Runnable() {
									public void run()
									{
										death.play();
										mplayer.stop();
										mplayer2.play();
										Alert alert = new Alert(AlertType.ERROR);
										alert.setHeaderText(null);
										alert.setContentText("\nGAME OVER!");
										alert.setTitle("enemy Game");
										alert.showAndWait();
										System.exit(0);
									}
								});
								
								
							}
						}
					}
					//checks if the player has beat the boss and has made it to the end game screen
					else if(levelbg == 4)
					{
						//removes the player and bullet from the screen so it only displays a textarea with the highscores on screen
						root.getChildren().removeAll(player.getImage2(), bullet.getImage());
						try {
							endScreen();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					//If the player has collided with the door 
					if (player.getImage2().getBoundsInParent().intersects(door.getImage2().getBoundsInParent()) && doorBroken == true) {

						//If checkFade equals false 
						if (checkFade == false) {

							//Set checkFade = true
							checkFade = true;

							//Call the fade method
							fade();
						}

					}

					//If checkUnfade equals true 
					if (checkUnfade == true) {

						//Set checkUnfade to false
						checkUnfade = false;
						unfade();
					}

					if (door.getPosition() == -100 && moveDoor == true) {
						moveDoor = false;
						checkUnfade = true;
						doorNum = 2;
					}

					//If moveDoor boolean is true
					if(moveDoor == true) {

						//Move the first door right
						door.move();  

						//Move the second door left

						//Set the Player location in front of the door
						player.setX(10);

					}

					//If bullet collides with the first door
					if (bullet.getImage().getBoundsInParent().intersects(door.getImage2().getBoundsInParent()))
					{
						doorNum = 1;//Set doorNum to 1
						doorBroken = true;//Set doorBroken variable to true
						doorTimer.play();//Play the doorTimer 
					}

					//code for smooth movement
					if (goRight == true)
					{
						if(player.getX() + player.getWidth() <= root.getWidth() -40)
						{	
							player.moveRight();
						}
						moveTimer.play();
					}	

					if (goLeft == true)
					{
						if(player.getX() >=-60)
						{
							player.moveLeft();
						}
						moveTimer.play();

					}

					//checks if the player has killed all the enemies on the second level
					//will fade into the boss fight if all enemies are dead
					if(root.getChildren().size() == 6 && levelbg == 2)
					{
						checkFade = false;
						if (checkFade == false) {

							//Set checkFade = true
							checkFade = true;

							//Call the fade method
							fade();
							boss.setLocation();
						}
					}

					iviewBg.setImage(level.getImage(levelbg));
					playerRect.setX(player.getX() + 75);
					playerRect.setY(player.getY() + 19);
				}
			};
			timer.start();

			//keyframe responsible for the players 
			KeyFrame kfJump = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					//Set Jump boolean to true
					//creates a variable for the jump force so it can be easily adjusted
					jump = true;
					double upForce = -5.5;
					player.jump(upForce, runs);
					runs++;

					//checks to see if the player has landed or gone beneath their old yPos before jumping
					//if they have, their jump cycle has ended and the player can jump again
					if(player.getY()> oldY)
					{
						//resets all the variables
						player.getImage(-1, extend);
						jumpAnimTimer.stop();
						player.grounded = true;
						player.setY((int)oldY);
						jumpTimer.stop();
						jump = false;
					}
				}
			});

			jumpTimer = new Timeline(kfJump);
			jumpTimer.setCycleCount(Timeline.INDEFINITE);

			//KeyFrame for Walking Animation
			KeyFrame kfWalk = new KeyFrame(Duration.millis(70), new EventHandler<ActionEvent>() {

				public void handle(ActionEvent e) {

					//If the player is on the ground and is moving left or right
					if(player.grounded && (right || left))
					{

						//If the player is not pointing up
						if(pointUp == false) {
							player.getImage(-2, extend);//Get the poin
						}

						//If the player is not pointing up
						else
						{
							player.getImage(-5, false);

						}
					}

				}
			});

			// Initialize the Timeline object
			// Instantiate the TimeLine object, adding the KeyFrame. 
			moveTimer = new Timeline(kfWalk);

			// Have its cycleCount be set to Indefinitie.
			moveTimer.setCycleCount(Timeline.INDEFINITE);

			//idle animation for the player
			KeyFrame kfIdle = new KeyFrame(Duration.millis(130), new EventHandler<ActionEvent>() {

				public void handle(ActionEvent e) {
					player.getImage(-1, extend);
				}
			});

			// Initialize the Timeline object
			// Instantiate the TimeLine object, adding the KeyFrame. 
			idleTimer = new Timeline(kfIdle);

			// Have its cycleCount be set to Indefinitie.
			idleTimer.setCycleCount(Timeline.INDEFINITE);

			//KeyFrame for the player's jump animation
			KeyFrame kfJumpAnim = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					player.getImage(-3, extend);
				}
			});

			//Initialize Jump Animation Timer
			jumpAnimTimer = new Timeline(kfJumpAnim);

			//Set the Cycle Count of the jump Animation Timer to Indefinite
			jumpAnimTimer.setCycleCount(Timeline.INDEFINITE);

			//KeyFrame for the door animations
			KeyFrame kfDoor = new KeyFrame(Duration.millis(220), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					//creates the fading animation for entering the level for the first time
					if(doorNum == 1) {
						door.getImage();//Get the Image of the first door
					}


				}
			});

			doorTimer = new Timeline(kfDoor);
			doorTimer.setCycleCount(Timeline.INDEFINITE);


			//keyframe that is in charge of everything bullet related
			KeyFrame kfBullet = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					//changes the bullet to match the direction the character is facing and moves it in that direction
					bullet.setImage(player.getDirection(), bulletRuns);
					bullet.getImage();
					bullet.move(pointUp, bulletRuns);
					bulletRuns = 0;

					// If the bullet is off the screen, then stop the timer to stop the bullet. 
					if (bullet.isOffScreen(scene.getWidth()))
					{
						bulletTimer.stop();
					}

					//checks if the bullet hits the boss eye
					if (bullet.getImage().getBoundsInParent().intersects(bossRect.getBoundsInParent()))
					{
						//damage the boss and remove the bullet so they player can shoot again
						bossHP -= 10;	
						bullet.setX(-100);
						bullet.stopBullet();

						//updates the boss health bar
						healthBar.setWidth(bossHP);

						//checks if the boss has died
						if(bossHP <= 0)
						{
							//removes the boss from the screen and moves the game onto the next screen
							root.getChildren().removeAll(boss.getImage());
							levelbg++;
						}
					}


					for (int i = 0; i < gEnemies.length; i++)
					{
						// Only hit a live enemy. Dead enemies have a direction of -1. This prevents them from moving left or right once dead
						if (gEnemies[i].getDirection() != -1)
						{
							//checks if the bullet has collided with an enemy
							if (bullet.getImage().getBoundsInParent().intersects(gEnemyRect[i].getBoundsInParent()))
							{
								// If we hit a live enemy, change direction to -1 to say they are dead. 
								gEnemies[i].setDirection(-1);
								root.getChildren().remove(gEnemies[i].getImage());
								root.getChildren().remove(gEnemyRect[i]);

								// Stop the bullet and move it off screen so the player can shoot again
								bullet.setX(-100);
								gEnemies[i].setX(-150);								
								bullet.stopBullet();
							}	
						}

						//same as the code above but for the flying enemies
						if(fEnemies[i].getDirection() != -1)
						{
							if (bullet.getImage().getBoundsInParent().intersects(fEnemyRect[i].getBoundsInParent()))
							{
								fEnemies[i].setDirection(-1);
								root.getChildren().remove(fEnemies[i].getImage());
								root.getChildren().remove(fEnemyRect[i]);

								bullet.setX(-100);
								fEnemies[i].setX(-150);
								bullet.stopBullet();
							}	
						}
					}

				}
			});

			//Initializng bulletTimer Timeline object
			bulletTimer = new Timeline(kfBullet);

			//Set the cycle count for the bullet Timer
			bulletTimer.setCycleCount(Timeline.INDEFINITE);

			//keyframe in charge of the fireballs that come down during the boss fight
			//keyframe is ran on a variable, the variable changes so this keyframe is 
			//called more frequently once the boss enters "phase 2"
			KeyFrame kfFire = new KeyFrame(Duration.millis(phase), new EventHandler<ActionEvent>() 
			{
				public void handle(ActionEvent e) 
				{
					//checks if the boss is at half hp
					//if the boss is, then the fireballs will spawn faster to make the 
					//boss fight even harder
					if(bossHP <= 100)
					{
						phase = 350;
					}
					//makes sure that the fireballs only spawn in the boss fight
					if(levelbg == 3)
					{
						//creates a fireball that will be added to the screen
						firecounter++;
						fireballs.add(firecounter, new fireball());

						fireballs.get(firecounter).setLocation((int)root.getWidth(), (int)root.getHeight());

						root.getChildren().add(fireballs.get(firecounter).getImage());
					}
				}
			});
			fireballTimer = new Timeline(kfFire);//Initialize fireballTimer Object
			fireballTimer.setCycleCount(Timeline.INDEFINITE);//Set the Cycle Count of fireballTimer
			fireballTimer.play();//PlafireballllTimer

			//Initialize the fadeRect Object
			fadeRect = new Rectangle(0, 0, root.getWidth(), root.getHeight());

			//Set the fadeRect object's color to black
			fadeRect.setFill(Color.BLACK);

			//Set the opacity of the fadeRect object to 0 (transparent)
			fadeRect.setOpacity(0);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	//fade class found online
	//citation and credit given in the program details at the top of this class
	public void fade() {
		invincible = true;
		fade = new FadeTransition(Duration.seconds(1), fadeRect);
		fade.setFromValue(0);
		fade.setToValue(1);

		root.getChildren().add(fadeRect);
		fade.play();
		root.getChildren().remove(door.getImage2());
		root.getChildren().add(door.getImage2());

		fade.setOnFinished(e -> {
			moveDoor = true;
			levelbg++;

		});
	}

	//citation and credit given in the program details at the top of the class
	public void unfade() {
		// Create a FadeTransition object to animate the fadeRect object
		FadeTransition unfade = new FadeTransition(Duration.seconds(1), fadeRect);
		// Set the initial opacity of the fadeRect to 1
		unfade.setFromValue(1);
		// Set the final opacity of the fadeRect to 0
		unfade.setToValue(0);
		// Start the animation
		unfade.play();

		//set the location of all elements of gEnemies array to (943, 683)
		for(int i = 0; i < 4; i++)
		{
			gEnemies[i].setLocation(943, 683);
			fEnemies[i].setLocation(943, 683);
		}
		// Once the animation is finished, remove the fadeRect from the root container and set the invincible variable to false
		unfade.setOnFinished(e -> {
			root.getChildren().remove(fadeRect);
			invincible = false;
		});
	}

	//method responsible for checking which button the player has clicked on the main screen
	public void titleSelect(int btn)
	{

		//If the play button has been pressed
		if(btn == 0)
		{
			//Set the level to 1
			levelbg = 1;

			//Remove the Play button from the pane
			root.getChildren().removeAll(playBtn);

			//Add the player and bullet to the pane
			root.getChildren().addAll(player.getImage(0, extend), bullet.getImage());

			//For loop to add all the enemies to the pane
			for(int i = 0; i < 4; i++)
			{
				root.getChildren().addAll(gEnemies[i].getImage(), fEnemies[i].getImage());
				gEnemies[i].setLocation(1, 1);
				fEnemies[i].setLocation(1,1);
			}

			//Set the initial player x-position
			player.setX(0);

			//Set the initial player y-position
			player.setY(500);

			//Add the door to the pane
			root.getChildren().addAll(door.getImage());		
		}
	}

	//method responsible for the end screen displaying the players highscore
	public void endScreen() throws IOException
	{
		//stops the timer so this method is only called once
		timer.stop();

		//switches the song playing in the background
		mplayer.stop();
		mplayer2.play();

		//creates the textarea that displays the high scores
		TextArea txtOutput = new TextArea();
		txtOutput.setPrefSize(300, 300);
		txtOutput.appendText("				HIGH SCORES\n");
		txtOutput.setLayoutX(30);
		txtOutput.setLayoutY(150);
		root.getChildren().addAll(txtOutput);

		//calulates the players score by subtracting the time they took the beat the game from the base score of 20,000
		finalScore = score-time;
		if(finalScore < 0)
		{
			finalScore = 0;
		}

		//creates an array that is responsible for holding the 5 highest scores
		int scores[] = new int[5];
		int index = -1;

		//creates the textfile object that contains the high scores
		File textFile = new File("scoreboard.txt");

		if(!textFile.exists()) {
			textFile.createNewFile();
		}

		FileReader in = new FileReader(textFile);
		BufferedReader readFile = new BufferedReader(in);

		//reading the file and storing it into the scores array
		try
		{
			while((lineOfText = readFile.readLine()) != null)
			{
				index++;
				scores[index] = Integer.parseInt(lineOfText);
			}
			//close in reverse order
		}
		
		catch(FileNotFoundException e)
		{
			System.out.print("A");
		}
		catch(IOException e)
		{
			System.out.print("B");
		}
		readFile.close();
		in.close();

		//adds the players current score to the array
		scores[index] = finalScore;

		
		//sorts the scores array from lowest to highest
		for(int end = 1; end < scores.length; end++)
		{
			int item = scores[end];
			int i = end;
			// Searching to see where to insert the value
			while(i > 0 && item < scores[i-1])
			{
				scores[i] = scores[i-1]; // Moves the larger value over right.
				i--;
			}
			// Move the smaller value to the sorted left hand side.
			scores[i] = item;

		}
		FileWriter out = new FileWriter (textFile);
		BufferedWriter writeFile = new BufferedWriter(out);
		
		//writes the top 5 scores into a text file and onto the textarea
		try
		{
			for (int i = 4; i >= 0; i--)
			{
				writeFile.write(String.valueOf(scores[i]));
				writeFile.newLine();
				writeFile.flush();
				txtOutput.appendText(Integer.toString(scores[i]) + " pts\n");
			}
		}
		catch (IOException e)
		{
			System.out.println("Problem writing to file.");
			System.err.println("IOException: "+ e.getMessage());
		}

		writeFile.close();
		out.close();
	}
}