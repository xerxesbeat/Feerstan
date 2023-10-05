package com.xerxesbeat.feerstan;

public class Viewport2D
{
	private Render render;
	private double x, y, w, h;

	public Viewport2D ( Render render )
	{
		this.render = render;
		this.x = 0;
		this.y = 0;
		this.w = render.getWidth();
		this.h = render.getHeight();
	}

	public Vector2<Double> translate ( int x, int y )
	{
		return new Vector2<Double> ( this.x + this.w * x / (double) render.getWidth(), this.y + this.h * y / (double) render.getHeight() );
	}

	public Vector2<Integer> translate ( double x, double y )
	{
		return new Vector2<Integer> ( (int) ( ( x - this.x ) / w * render.getWidth() ), (int) ( ( y - this.y ) / h * render.getHeight() ) );
	}
}
