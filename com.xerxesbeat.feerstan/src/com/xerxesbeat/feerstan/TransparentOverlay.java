package com.xerxesbeat.feerstan;

import static com.xerxesbeat.feerstan.OSDetector.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TransparentOverlay extends Application
{
	private Canvas [] canvases;
	private GraphicsContext gc;
	private Stage overlayStage;

	private static BufferedImage [] bags;
	static {
		try
		{
			bags = new BufferedImage [] {
				ImageIO.read( new File ( "feerlyCoolbag.png" ) ),
				ImageIO.read( new File ( "feerlyOwObag.png" ) ),
				ImageIO.read( new File ( "feerlySmugbag.png" ) ),
				ImageIO.read( new File ( "feerlyWatbag.png" ) ),
			};
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	private int bagID;

	@Override
	public void start ( Stage primaryStage )
	{
		overlayStage = new Stage ();
		overlayStage.setTitle( "feerstan" );
		switch ( os )
		{
		case WIN:
		case MAC:
			overlayStage.initStyle( StageStyle.UNDECORATED );
			overlayStage.initStyle( StageStyle.UTILITY );
		case N_X:
		default:
			overlayStage.setAlwaysOnTop( true );
			overlayStage.initStyle( StageStyle.TRANSPARENT );
		}
		canvases = new Canvas [] {
				new Canvas ( 600, 450 ) {
					{
						this.setOpacity( 1 / 3d );
					}
				},
				new Canvas ( 600, 450 ) {
					{
						this.setOpacity( 2 / 3d );
					}
				},
				new Canvas ( 600, 450 ) {
					{
						this.setOpacity( 1 );
					}
				},
			};
		StackPane root = new StackPane ();
		for ( Canvas canvas : canvases )
			root.getChildren().add( canvas );
		root.setStyle( "-fx-background-color: rgba(0, 0, 0, 0);" ); // Transparent black background
		Scene scene;
		overlayStage.setScene( scene = new Scene ( root, Color.TRANSPARENT ) );
		scene.widthProperty().addListener( ( observable, oldV, newV ) -> {
			for ( Canvas canvas : canvases )
				canvas.setWidth( (double) newV );
		});
		scene.heightProperty().addListener( ( observable, oldV, newV ) -> {
			for ( Canvas canvas : canvases )
				canvas.setHeight( (double) newV );
		});
		overlayStage.show();
		Timeline timeline = new Timeline ( new KeyFrame ( Duration.millis( 50 ), event-> {
			draw( canvases[1] );
			feerlys( canvases[2] );
		}));
		timeline.setCycleCount( Timeline.INDEFINITE );
		timeline.play();
		shuffle();
	}
   
	public void draw ( Canvas canvas )
	{
		if ( canvas.getWidth() <= 0 || canvas.getHeight() <= 0 )
			return;
		gc = canvas.getGraphicsContext2D();
		gc.clearRect( 0, 0, canvas.getWidth(), canvas.getHeight() );
		WritableImage bg = overlayStage.getScene().snapshot( null );
		BufferedImage image = new BufferedImage ( (int) canvas.getWidth(), (int) canvas.getHeight(), BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = image.createGraphics();
		int w = (int) canvas.getWidth();
		int h = (int) canvas.getHeight();
		new Render ( SwingFXUtils.fromFXImage( bg, image ), g, w, h ) {
			public void render ()
			{
				background();
				crosshair( w / 2, h / 2 );
			}
		}.render();
		gc.drawImage( SwingFXUtils.toFXImage( image, bg ), 0, 0 );
	}

	public void feerlys ( Canvas canvas )
	{
		if ( canvas.getWidth() <= 0 || canvas.getHeight() <= 0 )
			return;
		gc = canvas.getGraphicsContext2D();
		gc.clearRect( 0, 0, canvas.getWidth(), canvas.getHeight() );
		WritableImage bg = overlayStage.getScene().snapshot( null );
		BufferedImage image = new BufferedImage ( (int) canvas.getWidth(), (int) canvas.getHeight(), BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = image.createGraphics();
		int w = (int) canvas.getWidth();
		int h = (int) canvas.getHeight();
		new Render ( SwingFXUtils.fromFXImage( bg, image ), g, w, h ) {
			public void render ()
			{
				feerlys( bags[bagID] );
			}
		}.render();
		gc.drawImage( SwingFXUtils.toFXImage( image, bg ), 0, 0 );
	}

	public void shuffle ()
	{
		bagID = (int) ( Math.random() * bags.length );
		Timer timer = new Timer ( false );
		timer.schedule( new TimerTask () {
			@Override
			public void run() {
				shuffle();
			}
		}, 1000 * (int) ( Math.random() * 60 ) + 20 );
	}

	public static void main ( String [] args )
	{
		launch( args );
	}
}