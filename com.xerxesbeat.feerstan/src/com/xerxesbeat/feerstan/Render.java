package com.xerxesbeat.feerstan;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Render
{
	@SuppressWarnings("unused")
	private BufferedImage bg;
	private Graphics2D g;
	private int w, h;

	@SuppressWarnings("unused")
	private Viewport2D view;

	public Render ( BufferedImage bg, Graphics2D g, int w, int h )
	{
		this.bg = bg;
		this.g = g;
		this.w = w;
		this.h = h;
		view = new Viewport2D ( this );
	}

	public abstract void render ();

	public void background ()
	{
		g.setColor( Color.black );
    	g.fillRect( 0, 0, w, h );
	}

	public void crosshair ( int x, int y )
	{
    	g.setColor( Color.green );
    	g.drawLine( x, 0, x, Integer.MAX_VALUE );
    	g.setColor( Color.red );
    	g.drawLine( 0, y, Integer.MAX_VALUE, y );
    	g.setColor( Color.yellow );
    	g.fillRect( x, y, 1, 1 );
	}

	public void feerlys ( Image image )
	{
		g.drawImage( image, ( w / 2 ) - ( image.getWidth( null ) / 2 ), ( h / 2 ) - ( image.getHeight( null ) / 2 ), null );
	}

	public int getWidth ()
	{
		return w;
	}

	public int getHeight ()
	{
		return h;
	}
}
