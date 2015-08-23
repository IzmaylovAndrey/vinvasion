package ru.sdevteam.vinv.utils;

import java.awt.Color;
import java.awt.Graphics;
import ru.sdevteam.vinv.ui.controls.HorizontalAlignment;
import ru.sdevteam.vinv.ui.controls.VerticalAlignment;

public class TextRenderer
{
	// ������������ ������ text ���, ����� � ����� ������� ���� ��������� � (x;y)
	public static void drawString(Graphics g, int x, int y, String text)
	{
		g.setColor(Color.black);
		g.drawString(text, x, y);
	}
	// ������������ ������ text ������������ ����� (x;y) � �������� �������������
	// ������������ �� ��������� - LEFT, TOP
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		
	}
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, String text);
	public static void drawString(Graphics g, int x, int y, VerticalAlignment v, String text);
	// ������������ ������������� ����� � �������� �������������� � �������� �������������
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		
	}
	// ������������ �� ��������� �� ��
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, String text);
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, VerticalAlignment v, String text);
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, String text);

}
