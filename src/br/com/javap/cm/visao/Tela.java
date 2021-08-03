package br.com.javap.cm.visao;

import javax.swing.JFrame;

import br.com.javap.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class Tela extends JFrame {
	
	public Tela() {
		Tabuleiro tab = new Tabuleiro(16, 30, 50);
		
		add(new TabPanel(tab));
		
		setTitle("Mine Sweeper");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new Tela();
	}
}
