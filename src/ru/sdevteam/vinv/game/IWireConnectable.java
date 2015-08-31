package ru.sdevteam.vinv.game;


public interface IWireConnectable
{
	public boolean isCharged(); // ���������, ���� �� ���������� � �����
	public boolean isConsumer(); // �������� �� ������ ������������ (true ��� �����)
	public boolean isConductor(); // �������� �� ������ ����������� (true ��� ������)
	public boolean isGenerator(); // �������� �� ������ ����������� (true ��� ����)

	public void onCircuitChanged();
}
