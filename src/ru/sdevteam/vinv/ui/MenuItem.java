package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.controls.Button;

public abstract class MenuItem extends Button
{
	//TODO class
	// ������������
	private String name;
	private Menu ownMenu;
	private boolean selected;
	
	public MenuItem()
	{
		name="";
		ownMenu=null;
		selected=false;
	}
	
	public MenuItem(String text)
	{
		name=text;
		ownMenu=null;
		selected=false;
	}

	// ������
	public boolean isSelected()
	{
		return selected;
	}
	
	void select()
	{
		selected=true;
	}
	
	void deselect()
	{
		selected=false;
	}

	void setOwner(Menu menu)
	{
		ownMenu=menu;
	}
}
