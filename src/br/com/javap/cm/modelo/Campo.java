package br.com.javap.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	private final int row;
	private final int column;
	
	private boolean mine = false;
	private boolean open = false;
	private boolean checked = false;
	
	
	private List<Campo> neighbors = new ArrayList<>();
	private List<CampoObservador> observers = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.row = linha;
		this.column = coluna;
	}
	
	public void registerObserver(CampoObservador obs) {
		observers.add(obs);
	}
	
	private void notificateObservers(CampoEvento evento) {
			observers.stream()
			.forEach(o -> o.eventHappened(this, evento));
	}
	
	boolean addNeighbor(Campo neighbor) {
		boolean differentRow = row != neighbor.row;
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentColumn && differentRow;
		
		int deltaRow = Math.abs(row - neighbor.row); 
		int deltaColumn = Math.abs(column - neighbor.column); 
		int deltaGeneral = deltaRow + deltaColumn;
		
		if (deltaGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		}else if(deltaGeneral ==2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		}else {
			return false;
		}
	}
	
	public void updateCheck() {
		if (!open) {
			checked = !checked;
			
			if(checked) {
				notificateObservers(CampoEvento.CHECK);
			} else {
				notificateObservers(CampoEvento.UNCHECK);
			}
		}
	}
	
	public boolean open() {
		if(!open && !checked) {
			
			
			if(mine) {
				notificateObservers(CampoEvento.EXPLODE);
				return true;
			}
			
			setOpen(true);
			
			if(safeNeighbor()) {
				neighbors.forEach(n -> n.open());
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean safeNeighbor() {
		return neighbors.stream().noneMatch(n -> n.mine);
	}
	public boolean isBombed() {
		return mine;
	}
	
	void bomb() {
		mine = true;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	
	void setOpen(boolean open) {
		this.open = open;
		
		if (open) {
			notificateObservers(CampoEvento.OPEN);
		}
		
	}

	public boolean isOpened() {
		return open;
	}
	public boolean isClosed() {
		return !isOpened();
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	boolean reachedGoal() {
		boolean discovered = !mine && open;
		boolean protect = mine && checked;
		return discovered || protect;
	}
	
	public int neighborMine() {
		return (int) neighbors.stream().filter(n -> n.mine).count();
	}
	void restart() {
		open = false;
		mine = false;
		checked = false;
	}
	
}
