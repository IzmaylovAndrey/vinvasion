package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.Sprite;

public class BugSprite extends Sprite
{	
	private Bug instanse;
	
	public BugSprite(Bug b)
	{
		super(ResourceManager.getBufferedImage("bugs/test_bug"), 128, 128);
		instanse=b;
		setCollisionRectangle(32, 32, 64, 64);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		// TODO: ��������� ������ � ������������ � instanse
		// test bug has 8 rotation images
		
		//int frame=Vector2F.getDiscreteRotation(8, instanse.getRotation());
		//frame+=2; // ��� ��������� ����� � ������� ������ �� ��������
		//if(frame>=8) frame-=8;
		//gotoFrame(frame);
	}
}
