package ru.sdevteam.vinv.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.Screen;
import ru.sdevteam.vinv.ui.LoadingScreen;
import ru.sdevteam.vinv.ui.TestScreen;
import ru.sdevteam.vinv.utils.DebugInfo;

public class GameCanvas extends Canvas implements IDrawable, IUpdatable
{
	Screen screen;
	private Image buffer;
	Graphics buffg;
	private int width, height;
	
	private Font f;
	
	public GameCanvas(int w, int h)
	{
		width = w;
		height = h;
		//buffer = createImage(width, height);
		buffer=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		buffg=buffer.getGraphics();
		f=null;
	}
	
	public void start()
	{
		this.screen = new LoadingScreen(this);
		//this.screen=new TestScreen();
		//this.screen.setSize(this.width, this.height);
	}
	
	public void update()
	{
		if(this.screen != null) this.screen.update();
	}
	
	@Override
	public void update(Graphics g) 
	{
		paint(g);
	}
	
	public void paint (Graphics item)
	{
		if(this.screen == null) return;
		buffg.setColor(Color.black);
		buffg.fillRect(0, 0, getWidth(), getHeight());
		this.screen.paint(buffg);
		
		
		if(f==null)
		{
			try
			{
				f=ResourceManager.getMainFont().deriveFont(10F);
			}
			catch(NullPointerException ex)
			{
				f=null;
			}
		}
		else
		{
			buffg.setColor(Color.white);
			buffg.setFont(f);
			buffg.drawString("FPS: "+DebugInfo.getPaintFPS()+" | "+DebugInfo.getUpdateFPS(), 2, 20);
			
			buffg.drawString(DebugInfo.getMessage(0), 2, 35);
			buffg.drawString(DebugInfo.getMessage(1), 2, 50);
			buffg.drawString(DebugInfo.getMessage(2), 2, 65);
			buffg.drawString(DebugInfo.getMessage(3), 2, 80);
		}
		
		item.drawImage(buffer, 0, 0, null);
	}

	public void setActiveScreen (Screen item)
	{
		this.screen = item;
	}

}
