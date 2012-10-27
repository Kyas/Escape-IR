package fr.escape.game;

public interface Sender {
	public void send(int message);
	public void register(Receiver receiver);
}
