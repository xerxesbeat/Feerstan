package com.xerxesbeat.feerstan;

public class OSDetector
{
	public static enum OS {
		WIN,
		MAC,
		N_X,
		NUL
	};

	public static final OS os;

    static
    {
        String name = System.getProperty("os.name").toLowerCase();
        if ( name.contains( "win" ) )
        	os = OS.WIN;
        else if ( name.contains( "mac" ) )
        	os = OS.MAC;
        else if ( name.contains("nix") || name.contains("nux") ) /*|| name.contains("mac") <- cute, chatgpt... */
        	os = OS.N_X;
        else
        	os = OS.NUL;
    }
}