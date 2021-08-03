package br.com.javap.cm.visao;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.javap.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TabPanel extends JPanel{
	
	public TabPanel(Tabuleiro tab) {
		setLayout(new GridLayout(tab.getRows(), tab.getColumns()));
		
		tab.forEachField(c -> add(new FieldButton(c)));
		
		tab.registerObserver(e -> {
			//TODO mostrar resultado para o user
		});
	}

}
