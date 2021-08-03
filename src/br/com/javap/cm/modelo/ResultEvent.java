package br.com.javap.cm.modelo;

public class ResultEvent {
	private final boolean win;


	public ResultEvent(boolean win) {
		this.win = win;
	}
	
	public boolean isWin() {
		return win;
	}
	
}
