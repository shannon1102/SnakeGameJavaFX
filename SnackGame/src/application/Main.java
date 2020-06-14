package application;
	
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javax.xml.stream.EventFilter;

import com.sun.media.jfxmedia.events.NewFrameEvent;


public class Main extends Application {
	static final int heightNode=20;
	static final int widthNode=20;
	static final int hWindow =600;
	static final int wWindow =600;
	static int foodX;
	static int foodY;
	static int score=0;
	private static int speed=5;
	static List<Corner> snake= new ArrayList<>();
    static Random rand = new Random();
    private static boolean gameOver =false;
	enum Dir{
		up,down,left,right;
	}
	static Dir direction= Dir.right;
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			final Canvas canvas = new Canvas(wWindow,hWindow);
			GraphicsContext gc = canvas.getGraphicsContext2D();	 
			root.getChildren().add(canvas);
			//canvas.getN
			snake.add(new Corner(wWindow/2,hWindow/2));
			snake.add(new Corner(wWindow/2-20,hWindow/2));
			snake.add(new Corner(wWindow/2-40,hWindow/2));
//			//snake.add(new Corner(wWindow/2+40,hWindow/2));
			new AnimationTimer() {
				long  lastTick=0;
				@Override
				public void handle(long now) {
					if(gameOver) {
						return;
						// TODO Auto-generated method stub
					}
					if(lastTick==0){
						lastTick= now;
						tick(gc);						
						return;
					}
					if(now-lastTick >1000000000/speed) {
						lastTick=now;
						tick(gc);
						//direction= Dir.up;
					    System.out.println("?????????");
					}
				}
				
			}.start();
			
			
			newfood(gc);
			gc.setFill(Color.BLUE);
			for (Corner c : snake) {
				gc.fillRect(c.getX(),c.getY(), widthNode-1, heightNode-1);
			}
			System.out.println("anh yeu em");
			Scene scene = new Scene(root,wWindow,hWindow,Color.GRAY);
			scene.addEventFilter(KeyEvent.KEY_PRESSED,keyEvent->{
				if(keyEvent.getCode()==KeyCode.W) {
					direction= Dir.up;
					System.out.println("Up");
					
				}
				if(keyEvent.getCode()==KeyCode.A) {
					direction= Dir.left;
					System.out.println("Left");
				}
				if(keyEvent.getCode()==KeyCode.S) {
					direction= Dir.down;
					System.out.println("Down");
				}
				if(keyEvent.getCode()==KeyCode.D) {
					direction= Dir.right;
					System.out.println("Right");
				}
				
				
			});
			
			//eatFood
	
			//scene.getStylesheets().add(getClass().getResource("applicaton.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Snack Game");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void tick(GraphicsContext gc) {
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, wWindow, hWindow);
		gc.setFill(Color.WHITE);
		//score
		gc.setFill(Color.VIOLET);
		gc.setFont(new Font("", 20));
		gc.fillText("Score: ", 10, 30);
		gc.fillText(Integer.toString(score), 80, 30);
		
		//newfood(gc);
		
		gc.fillOval(foodX*20,20*foodY,19,19);
		gc.setFill(Color.BLUE);
		for(int i=snake.size()-1;i>=1;i--) {
			snake.get(i).setX(snake.get(i-1).x);
			snake.get(i).setY(snake.get(i-1).y);		
		}
		switch (direction) {
		case up:
			snake.get(0).y=snake.get(0).y-20;
			//System.out.println("van");
			break;
		case down:
			snake.get(0).y=snake.get(0).y+20;
			System.out.println("van");
			break;
		case left:
			snake.get(0).x=snake.get(0).x-20;
			System.out.println("vandeptrai");
			break;
		case right:
			snake.get(0).x=snake.get(0).x+20;
			//System.out.println("van");
			break;
		
		}
		//gc.setFill(Color.GRAY);
		
		for (Corner c : snake) {
			gc.fillRect(c.getX(),c.getY(), widthNode-1, heightNode-1);
		}
		//System.out.println(foodX +"-"+foodY);
		//System.out.println(snake.get(0).x + "-"+ snake.get(0).y);
		if(snake.get(0).x==foodX*20 && snake.get(0).y==foodY*20) {
			snake.add(new Corner(-1*20, -1*20));
			score++;
			speed++;
			newfood(gc);
		}
		//self destroy
		for(int i=snake.size()-1;i>=1;i--) {
			if(snake.get(0).x==snake.get(i).x && snake.get(0).y==snake.get(i).y ) {
				gc.setFill(Color.RED);
				gc.setFont(new Font("", 50));
				gc.fillText("Game Over", hWindow/2-130, wWindow/2);
				gameOver=true;
				
			}
			
		}
		if(snake.get(0).x>=wWindow || snake.get(0).x <=0 || snake.get(0).y>=hWindow || snake.get(0).y<=-20) {
			gc.setFill(Color.RED);
			gc.setFont(new Font("", 50));
			gc.fillText("Game Over", hWindow/2-130, wWindow/2);
			gameOver=true;
		}
		
	}
	public static void newfood(GraphicsContext gc){
		int check=1;
		while (true) {
			foodX=rand.nextInt(31);
			foodY=rand.nextInt(31);
			for(int i=snake.size()-1;i>=0;i--) {
				if(foodX*20 == snake.get(i).x && foodY*20==snake.get(i).y) {
				 check=0;					
				}
				
			}
			if (check==1) {
				break;	
			}
		}
		
		gc.fillOval(foodX*20,20*foodY,19,19);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
