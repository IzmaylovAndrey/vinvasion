package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.logics.LevelController;
import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.Control;
import ru.sdevteam.vinv.ui.controls.FocusableControl;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Fonts;

public class GameScreen extends Screen
{
	private LevelController levelCtrl;
	private float viewportX;
	private float viewportY;
	private float viewportWidth;
	private float viewportHeight;
	private int scaleFactor;
	
	// images
	private static BufferedImage 
	panel=ResourceManager.getBufferedImage("ui/panel"),
	res_r=ResourceManager.getBufferedImage("ui/res_resources"),
	res_h=ResourceManager.getBufferedImage("ui/res_humans"),
	res_p=ResourceManager.getBufferedImage("ui/res_power");
	
	
	public GameScreen(int levelNum, GameCanvas canvas)
	{
		this.levelCtrl=new LevelController(this, Level.createLevel(levelNum));
		scaleFactor=1;
		viewportX=0;
		viewportY=0;
		viewportHeight=canvas.getCanvasHeight()/scaleFactor;
		viewportWidth=canvas.getCanvasWidth()/scaleFactor;
		
		setFont(Fonts.main(10));
		
		// ��������� �������
		//setSize(canvas.getWidth(), canvas.getHeight());
		setSize((int)viewportWidth, (int)viewportHeight);
		
		addControl(this.new LevelWrapperControl(getWidth(), getHeight(), this));
		
		Button pauseBtn=this.new SwitchableButton(getWidth()-31, getHeight()-31, 
						"ui/pause_r", "ui/pause_h", "ui/pause_p");
		addControl(pauseBtn);
	}
	
	public int getScaleFactor()
	{
		return scaleFactor;
	}
	
	public float getViewportX()
	{
		return viewportX;
	}
	
	public float getViewportY()
	{
		return viewportY;
	}
	
	public float getViewPortWidth()
	{
		return viewportWidth;
	}
	
	public float getViewPortHeight()
	{
		return viewportHeight;
	}
	
	public void onVictory()
	{
		
	}
	public void onDefeat()
	{
		
	}
	
	@Override
	synchronized public void paint(Graphics g) 
	{
		//
		// ��������� ������
		//
		((Graphics2D)g).scale(scaleFactor, scaleFactor);
		((Graphics2D)g).translate(-viewportX, -viewportY);
		levelCtrl.paint(g);
		((Graphics2D)g).translate(viewportX, viewportY);
		((Graphics2D)g).scale(1F/scaleFactor, 1F/scaleFactor);
		
		//
		// ��������� ����������
		//
		//((Graphics2D)g).scale(2F, 2F);
		int penx=0, peny=getHeight();
		g.setFont(getFont());
		
		//������
		peny-=panel.getHeight();
		while(penx<getWidth())
		{
			g.drawImage(panel, penx, peny, null);
			penx+=panel.getWidth();
		}
		penx=4; peny+=3; g.setColor(Colors.white());
		
		g.drawImage(res_r, penx, peny, null); 
		g.drawString(levelCtrl.getPlayer().getResources()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_r.getWidth()+10;
		
		g.drawImage(res_p, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getBasePower()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_p.getWidth()+10;
		
		g.drawImage(res_h, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getHumansCount()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_h.getWidth()+10;
		
		paintChildren(g);
		
		//((Graphics2D)g).scale(0.5F, 0.5F);
		// endof ���������
	}

	@Override
	synchronized public void update() 
	{
		super.update();
		
		levelCtrl.update();
	}
	
	
	private class LevelWrapperControl extends FocusableControl
	{
		GameScreen parent;
		boolean dragging;
		float oldx, oldy;
		
		public LevelWrapperControl(int w, int h, GameScreen parent)
		{
			moveTo(0, 0);
			setSize(w, h);
			this.parent=parent;
			dragging=false;
			oldx=parent.viewportX; oldy=parent.viewportY;
		}
		
		@Override
		protected void onMouseDragStart(MouseEvent ev)
		{
			super.onMouseDragStart(ev);
			
			if(ev.getActualState().isLeftDown())
			{
				dragging=true;
				oldx=parent.viewportX; oldy=parent.viewportY;
				DebugInfo.addMessage("mds");
			}
		}
		@Override
		protected void onMouseDragging(MouseEvent ev)
		{
			super.onMouseDragging(ev);
			
			if(dragging)
			{
				viewportX=oldx-(ev.getMouseX()-this.getMouseDragStartX())/scaleFactor;
				viewportY=oldy-(ev.getMouseY()-this.getMouseDragStartY())/scaleFactor;
				
				DebugInfo.addMessage("vp "+parent.viewportX+"; "+parent.viewportY);
			}
		}
		@Override
		protected void onMouseDragEnd(MouseEvent ev, Control dragStarter)
		{
			super.onMouseDragEnd(ev, dragStarter);
			dragging=false;
		}
		@Override
		protected void onMouseDragDroppedOutside(MouseEvent ev,	Control dropTarget)
		{
			super.onMouseDragDroppedOutside(ev, dropTarget);
			dragging=false;
		}
		
		@Override
		protected void onMouseScroll(MouseEvent ev)
		{
			super.onMouseScroll(ev);
			
			// <0 - �����������, >0 - ���������
			
			int oldScf=scaleFactor;
			
			if(ev.getDelta()<0) 
			{
				if(scaleFactor<80) 
				{
					scaleFactor*=2;
					viewportWidth/=2;
					viewportHeight/=2;
					
					
				}
			}
			else 
				if(ev.getDelta()>0)
				{
					if(scaleFactor>2) 
					{
						scaleFactor/=2;
						viewportWidth*=2;
						viewportHeight*=2;
					}
				}
			
			if(ev.getDelta()!=0)
			{
				float k;
				if(ev.getDelta()<0) k=0.5F;
				else k=2F;
				
				// TODO: correct the formula
				//viewportX+=(1-k)*(ev.getMouseX()-viewportX);
				//viewportY+=(1-k)*(ev.getMouseY()-viewportY);
				/*float dx=(ev.getMouseX()*(1-k));
				float dy=(ev.getMouseY()*(1-k));
				viewportX+=dx;
				viewportY+=dy;*/
				//k=scaleFactor/oldScf;
				viewportX+=ev.getMouseX()/(1-k)/scaleFactor;
				viewportY+=ev.getMouseY()/(1-k)/scaleFactor;
				
				//int dx=(int)(x*(zoom/newZoom-1)/zoom);
				//int dy=(int)(y*(zoom/newZoom-1)/zoom);
				
				System.out.println("New viewport: ("+viewportX+"; "+viewportY+").(" +
				viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor);
				
				DebugInfo.addMessage("("+viewportX+"; "+viewportY+").(" +
						viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor +
						"mpos: ("+ev.getMouseX()+"; "+ev.getMouseY()+")");
			}

			//if(viewportX<0) viewportX=0;
			//if(viewportY<0) viewportY=0;
			// TODO: ��������� ��� ������ ������� ������
		}
		
		@Override
		public void update()
		{
			// nothing
		}

		@Override
		public void paint(Graphics g)
		{
			// nothing
			g.drawRect(getX()+5, getY()+5, getWidth()/4-10, getHeight()/4-10);
		}
	}
	
	private class SwitchableButton extends Button
	{
		BufferedImage r, h, p;
		public SwitchableButton(int x, int y, String rname, String hname, String pname)
		{
			moveTo(x, y);
			r=ResourceManager.getBufferedImage(rname);
			h=ResourceManager.getBufferedImage(hname);
			p=ResourceManager.getBufferedImage(pname);
			
			setSize(r.getWidth(), r.getHeight());
		}
		
		@Override
		public void paint(Graphics g)
		{
			if(isFocused())
			{
				g.drawImage(p, getX(), getY(), null);
				return;
			}
			if(isHovered())
			{
				g.drawImage(h, getX(), getY(), null);
				return;
			}
			g.drawImage(r, getX(), getY(), null);
		}
		
		@Override
		protected void onPressed()
		{
			super.onPressed();
			if(this.isFocused())
				this.unfocus();
			else 
				this.focus();
		}
	}
}
