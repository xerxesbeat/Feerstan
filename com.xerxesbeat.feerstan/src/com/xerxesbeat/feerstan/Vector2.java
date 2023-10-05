package com.xerxesbeat.feerstan;

public final class Vector2<T>
{
	private T X, Y;

	public Vector2 ( T x, T y )
	{
		X = x;
		Y = y;
	}

	public T getX ()
	{
		return X;
	}

	public T getY ()
	{
		return Y;
	}

	public Vector2<T> setX ( T x )
	{
		X = x;
		return this;
	}

	public Vector2<T> setY ( T y )
	{
		Y = y;
		return this;
	}

	public Vector2<T> get ()
	{
		return new Vector2<T> ( X, Y );
	}

	public Vector2<T> set ( T x, T y )
	{
		X = x;
		Y = y;
		return this;
	}
}
