package br.com.javap.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.javap.cm.modelo.Campo;
import br.com.javap.cm.modelo.CampoEvento;
import br.com.javap.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class FieldButton extends JButton 
		implements CampoObservador, MouseListener {
	
	private Campo campo;
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_CHECKED = new Color(8, 179, 247);
	private final Color BG_EXPLOSION = new Color(189, 66, 68);
	private final Color TXT_GREEN = new Color(0, 100, 0);
	
	public FieldButton(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		campo.registerObserver(this);
		addMouseListener(this);
	}

	@Override
	public void eventHappened(Campo campo, CampoEvento evento) {
		switch (evento) {
		case OPEN: 
			applyStyleOpen();
			break;
		case CHECK: 
			applyStyleCheck();
			break;
		case EXPLODE: 
			applyStyleExplode();
			break;
		default:
			applyStyleDefault();	
		
	}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
}

	private void applyStyleDefault() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}

	private void applyStyleExplode() {
		setBackground(BG_EXPLOSION);
		setForeground(Color.WHITE);
		setText("X");
		
	}

	private void applyStyleCheck() {
		setBackground(BG_CHECKED);
		setForeground(Color.BLACK);
		setText("C");
		
	}

	private void applyStyleOpen() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isBombed()) {
			setBackground(BG_EXPLOSION);
			return;
		}
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		switch (campo.neighborMine()) {
		case 1:
			setForeground(TXT_GREEN);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		String valor = !campo.safeNeighbor() ? campo.neighborMine() + "": "";
		setText(valor);
	}
	
	//interface dos eventos do mouse
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.open();
		} else {
			campo.updateCheck();
		}
		
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
}
