package ru.sdevteam.vinv.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.imageio.ImageIO;



public class ResourceManager 
{
	
	private ResourceManager() {}
	
	class Resource
	{
		String name;
		Object data;
	
		public Resource(String key, Object a_data)
		{
			name = key;
			data = a_data;
		}
	}
	
	static Vector<Resource> imges, sounds, fonts;
	static BufferedImage splash=null;
	static boolean ready = false;
	static int countOfFiles=1;
	static int countOfReadyFiles = 0;
	
	static String path = "/ru/sdevteam/vinv/res/";
	
	static void addImages(String[] names, String additionalPath)
	{
		for (String name: names)
		{
			imges.add(new ResourceManager().new Resource(additionalPath + name, BI(path+additionalPath+name+".png")));
			countOfReadyFiles++;
		}
	}
	
	
	static Font loadFont(String name)
	{
		File f;
		try
		{
			f=new File(new ResourceManager().getClass().getResource(path+"fonts/"+name).toURI());
			return Font.createFont(Font.TRUETYPE_FONT, f);
		}
		catch(URISyntaxException e)
		{
			System.out.println("Something is wrong with font loading...");
		}
		catch(FontFormatException e)
		{
			System.out.println("Wrong font format found!");
		}
		catch(IOException e)
		{
			System.out.println("General IO error...");
		}
		return null;
	}
	
	public static Image getSplash() { return splash; }
	
	public static Font getMainFont()
	{
		return (Font)fonts.get(0).data;
	}
	
	public static void init()
	{
		String[] bullets = {"test_bullet"};
		String[] towers = { "test_tower" };
		String[] decos = {};
		String[] bugs = { "test_bug" };
		String[] fonts = { "PressStart2P.ttf" };
		String[] tiles = { "test" };
		String[] explosions = { "regular", "slime", "big_slime" };
		countOfFiles = 	bullets.length +	towers.length +	decos.length + 
						bugs.length +		fonts.length +	tiles.length +
						explosions.length;
		
		splash = BI(path + "splash.png");
		
		imges=new Vector<Resource>();
		ResourceManager.fonts=new Vector<Resource>();
		sounds=new Vector<Resource>();
		
		for(String name: fonts)
		{
			ResourceManager.fonts.add(new ResourceManager().new Resource(name, loadFont(name)));
			countOfReadyFiles++;
		}
		
		addImages(towers, "towers/");
		addImages(bugs, "bugs/");
		addImages(bullets, "bullets/");
		addImages(tiles, "tiles/");
		addImages(explosions, "explosions/");
		
		ready = true;
	}
	
	
	public static boolean isReady()
	{
		return ready;
	}
	public static float getProgress()
	{
		return (float)(countOfReadyFiles/countOfFiles);
	}
	
	public static Toolkit getT()
	{
		return Toolkit.getDefaultToolkit();
	}
	
	private static Image I(String key)
	{
		Image i=getT().createImage(new ResourceManager().getClass().getResource(key));
		return i;
	}
	
	private static BufferedImage BI(String key)
	{
		try
		{
			return ImageIO.read(new ResourceManager().getClass().getResource(key));
		}
		catch(IOException e)
		{
			System.out.println("While loading "+key);
			return null;
		}
	}
	
	public static Image getImage(String key)
	{
		for(Resource r: imges)
		{
			if (r.name.equals(key))
			{
				return (Image)r.data;
			}
		}
		return null;
	}
	
	public static BufferedImage getBufferedImage(String key)
	{
		for(Resource r: imges)
		{
			if (r.name.equals(key))
			{
				return (BufferedImage)r.data;
			}
		}
		return null;
	}
}
