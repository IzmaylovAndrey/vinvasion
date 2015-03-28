package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.IMoveable;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public abstract class Menu implements IDrawable, IUpdatable, IMoveable
{
	public void addMenuItem(MenuItem m)
	{
		
	}
	
	public abstract void addMenuItem(String text);
	// ��������� "�����������" (������) ����� ���������� ������ ����
	public abstract void insertSeparator();
	
	public void removeMenuItem(MenuItem m)
	{
		
	}

	// ������������, ������������ ����� ����
	public enum Style { MAIN_MENU, INGAME_MENU }
	

	// ��������� �������
	public void processEvent(MouseEvent ev)
	{
		
	}
	
	public void processEvent(KeyEvent ev)
	{
	
	}

	// ���������� ��� ������ ������������� �������� ���� item
	protected void onItemActivated(MenuItem item)
	{
		
	}
	// ���������� ��������� ��������� ����, ����� �� ������
	final void itemActivated(MenuItem invoker)
	{
		
	}

	// ��������� ����� ����
	public Menu getPrevious()
	{
		return null;
	}
	
	protected void setPrevious(Menu m)
	{
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
