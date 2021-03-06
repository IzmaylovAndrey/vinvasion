package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Vector;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.ButtonSet;
import ru.sdevteam.vinv.ui.controls.FocusableControl;
import ru.sdevteam.vinv.ui.controls.IButtonPressedListener;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Fonts;
import ru.sdevteam.vinv.utils.TextRenderer;


public class MessageBox extends ButtonSet implements IButtonPressedListener
{
	public enum DialogResult { NONE, OK, CANCEL, YES, NO }
	
	// images
	private static BufferedImage large=ResourceManager.getBufferedImage("ui/msg_large");

	private String title, message;
	public String getTitle() { return title; }
	public void setTitle(String caption) { title=caption; }
	public String getMessage() { return message; }
	public void setMessage(String msg) { message=msg; }
	
	public boolean isShown()
	{
		return isVisible();
	}
	
	private DialogResult result;
	public MessageBox.DialogResult getDialogResult() { return result; }
	protected void setDialogResult(DialogResult result)
	{ this.result=result; if(result!=DialogResult.NONE) close(); }
	
	// ��������
	private FocusableControl background;
	

	public MessageBox(String title, String message)
	{
		super(LayoutType.HORIZONTAL);
		this.title=title;
		this.message=message;
		result=DialogResult.NONE;
		
		hide();
		
		background=new FocusableControl()
		{
			@Override
			public void paint(Graphics g)
			{
				g.setColor(Colors.red());
				//DebugInfo.addMessage(getX()+";"+getY()+", "+getWidth()+";"+getHeight());
				//g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			@Override
			public void update()
			{
			}
		};
		
		//addControl(background);
		
		listeners=new Vector<IDialogResultListener>();
	}

	
	@Override
	public void show()
	{
		super.show();
		
		// ��������������� �� ��� ��������� �������
		moveTo(getParent().getX(), getParent().getY());
		setSize(getParent().getWidth(), getParent().getHeight());
		
		DebugInfo.addMessage(getX()+30+" "+(getY()+getHeight()-30));
		
		// ��������
		background.setSize(getParent().getWidth(), getParent().getHeight());
		setStartPoint(getPictureX()+10, getPictureY()+large.getHeight()-25);
		setMargin(3);
		
		background.moveTo(getX(), getY());
		background.setSize(getParent().getWidth(), getParent().getHeight());
		if(buttons.size()==0) background.focus();
		else buttons.firstElement().focus();
	}
	
	public void close()
	{
		hide();
		this.unfocus();
		onDialogResult();
	}
	
	private Vector<IDialogResultListener> listeners;
	public void addDialogResultListener(IDialogResultListener item)
	{ listeners.add(item); }
	public void removeDialogResultListener(IDialogResultListener item)
	{ listeners.remove(item); }
	
	protected void onDialogResult()
	{
		for(IDialogResultListener l: listeners)
		{
			l.dialogResult(this, result);
		}
	}
	
	
	public void addButton(DialogResult result)
	{
		addButton(new MessageBoxButton(result));
	}
	public void addButton(DialogResult result, String text)
	{
		addButton(new MessageBoxButton(text, result));
	}
	@Override
	public void addButton(Button item)
	{
		try
		{ 
			MessageBoxButton b=(MessageBoxButton)item;
			b.addButtonPressedListener(this);
			super.addButton(item);
		}
		catch(ClassCastException ex)
		{ throw new InvalidParameterException("Cannot add not a MessageBoxButton instance"); }
	}
	@Override
	public void buttonPressed(Button sender)
	{
		setDialogResult(((MessageBoxButton)sender).getDialogResult());
	}
	
	
	protected int getPictureX() { return getX()+(background.getWidth()-large.getWidth())/2; }
	protected int getPictureY() { return getY()+(background.getHeight()-large.getHeight())/2; }
	public void paint(Graphics g)
	{		
		int x=getPictureX();
		int y=getPictureY();
		
		g.drawImage(large, x, y, null);
		
		x+=5; y+=5;
		g.setFont(Fonts.main(10));
		g.setColor(getForeground());
		g.drawString(title, x, y+getFontMetrics(g).getAscent());
		
		y+=15;
		g.setFont(getFont());
		//TextRenderer.drawMultiline(g, x, y, large.getWidth()-10, large.getHeight()-30, getMessage());
		
		
		paintChildren(g);
	}

	public void update()
	{
		super.update();
	}
}
