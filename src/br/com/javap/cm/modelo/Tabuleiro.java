package br.com.javap.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{
	private final int rows;
	private final int columns;
	private final int mines;
	
	private final List<Campo> fields = new ArrayList<>();
	private List<Consumer<ResultEvent>> observers = new ArrayList<>();
	

	public Tabuleiro(int rows, int columns, int mines) {

		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void forEachField(Consumer<Campo> funcao) {
		fields.forEach(funcao);
	}
	
	public void registerObserver(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}
	public void notificateObserver(boolean result) {
		observers.stream()
			.forEach(o -> o.accept(new ResultEvent(result)));
	}
	
	public void open(int row, int column) {
			fields.parallelStream()
			.filter(c -> c.getRow() == row && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.open());
	}
	
	
	public void updateCheck(int row, int column) {
		fields.parallelStream()
		.filter(c -> c.getRow() == row && c.getColumn() == column)
		.findFirst()
		.ifPresent(c -> c.updateCheck());;
	}
	

	private void gerarCampos() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Campo campo = new Campo(i, j);
				campo.registerObserver(this);
				fields.add(campo);
			}
		}
		
	}
	private void associarVizinhos() {
		for (Campo f1 : fields) {
			for (Campo f2 : fields) {
				f1.addNeighbor(f2);
			}
		}	
			
	}
	private void sortearMinas() {
			long bombMines = 0;
			Predicate<Campo> bombed = b -> b.isBombed();
			do {
				int random = (int) (Math.random() * fields.size());
				fields.get(random).bomb();
				bombMines = fields.stream().filter(bombed).count();				
			} while (bombMines < mines);
			
	}
	
	public boolean reachedGoal() {
		return fields.stream().allMatch(c -> c.reachedGoal());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		sortearMinas();
	}

	@Override
	public void eventHappened(Campo campo, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODE) {
			showMines();
			notificateObserver(false);
		} else if(reachedGoal()){
			System.out.println("Ganhou");
			notificateObserver(true);
		}
	}
	
	private void showMines() {
		fields.stream()
		.filter(c -> c.isBombed())
		.filter(c -> !c.isChecked())
		.forEach(c -> c.setOpen(true));
	}
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	
	
}
