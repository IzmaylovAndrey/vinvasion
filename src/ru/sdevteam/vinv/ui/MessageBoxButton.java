package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.MessageBox.DialogResult;
import ru.sdevteam.vinv.ui.controls.Button;

public class MessageBoxButton extends Button
{
	private DialogResult dr;
	
	public MessageBoxButton(DialogResult result)
	{
		super();
		dr=result;
		switch(dr)
		{
		case OK:
			setText("������");
			break;
		case CANCEL:
			setText("������");
			break;
		case YES:
			setText("��-�");
			break;
		case NO:
			setText("���-�");
			break;
		}
	}
	
	public MessageBoxButton(String text, DialogResult result)
	{
		super(text);
		dr=result;
	}
	
	public MessageBoxButton(String text, DialogResult result, int x, int y, int width, int height)
	{
		super(text, x, y, width, height);
		dr=result;
	}
	
	
	public DialogResult getDialogResult()
	{
		return dr;
	}
}
