package br.com.maf.ms.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Board implements FieldObserver{
	private int lines;
	private int columns;
	private int amountOfMines;	
	
	private final List<Field> camps = new ArrayList<>();
	private final List<Consumer<Boolean>> observers = new ArrayList<>();
	
	
	////////// Observer //////////
	public void addObserver(Consumer<Boolean> obs) {
		observers.add(obs);
	}
	
	private void notifyObservers(boolean result) {
		observers.stream()
			.forEach(o -> o.accept(result));
	}
	
	@Override
	public void eventTriggered(Field f, FieldEvent e) {
		if (e == FieldEvent.EXPLODE) {
			showMinedFields();
			notifyObservers(false);
		} else if (reachObjective()) {
			notifyObservers(true);
		}
	}	
	//////////////////////////////

	public Board(int lines, int columns, int amountOfMines) {
		this.lines = lines;
		this.columns = columns;
		this.amountOfMines = amountOfMines;
		
		creatCamps();
		setNeighbours();
		setMineCamps();	
	}
	
	public void showMinedFields() {
		// Opens all mine fields after the player losing the game
		camps.stream()
		.filter(c -> c.isMineField())
		.forEach(c -> c.IgnoreBombAndOpen());
	}
	
	public boolean openCamp(int line, int column) {
			camps.parallelStream()
			.filter(c -> c.getLine() == line && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.openField());
			return true;
	}
	
	public void switchMark(int line, int column) {
			camps.parallelStream()
			.filter(c -> c.getLine() == line && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.switchMarked());
	}

	private void creatCamps() {
		for (int l = 0; l < lines; l++) {
			for(int c = 0; c < columns; c++) {
				Field field = new Field(l,c);
				field.addObserver(this);
				camps.add(field);
			}
		}	
	}
	
	private void setNeighbours() {
		for (Field c1: camps) {
			for(Field c2: camps) {
				c1.addNeighbour(c2);
			}
		}
	}

	private void setMineCamps() {
		 int minesSet = 0;
		 Predicate<Field> mined = c -> c.isMineField();
		 
		 do { 
			minesSet = (int) camps.stream().filter(mined).count();
			int random = (int) (Math.random() * camps.size());
			camps.get(random).setMine();	
		} 
		while (minesSet < amountOfMines);
	}
	
	public boolean reachObjective() {
		return camps.stream().allMatch(c -> c.reachObjective());
	}
	
	public void restartBoard() {
		camps.stream().forEach(c -> c.restart());
		setMineCamps();
	}
	
	////////////   Getters and setters   //////////////
	
	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}

	public List<Field> getCamps() {
		return camps;
	}


}
