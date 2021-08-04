package br.com.javap.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.javap.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TabPanel extends JPanel{
	
	public TabPanel(Tabuleiro tab) {
		setLayout(new GridLayout(tab.getRows(), tab.getColumns()));
		
		tab.forEachField(c -> add(new FieldButton(c)));
		
		tab.registerObserver(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.isWin()) {
					JOptionPane.showMessageDialog(this, "Ganhou");
				} else {
					JOptionPane.showMessageDialog(this, "Perdeu :(");
				}
				tab.restart();
			});
			
		});
	}

}
