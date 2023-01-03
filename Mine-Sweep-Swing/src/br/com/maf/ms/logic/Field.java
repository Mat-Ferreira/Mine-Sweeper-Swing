package br.com.maf.ms.logic;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private boolean mineField = false;
	private boolean open = false;
	private boolean marked = false;
	
	private List<Field> neighbours = new ArrayList<>();
	private List<FieldObserver> observers = new ArrayList<>();
	
	private final int line;
	private final int column;
	
	Field(int linha, int coluna) {
		column = coluna;
		line = linha;
	}
	
	////////// Observer //////////
	
	public void addObserver(FieldObserver o) {
		observers.add(o);
	}
	
	private void notifyObservers(FieldEvent e) {
		observers.parallelStream()
			.forEach(o -> o.eventTriggered(this, e));
	}
	
	//////////////////////////////
	
	void restart() {
		mineField = false;
		marked = false;
		open = false;
		notifyObservers(FieldEvent.RESTART);
	}
	
	public boolean openField() {
		if(!open && !marked) {
			if (mineField) {
				notifyObservers(FieldEvent.EXPLODE);
				return true;
			}
			// Will make an event trigger
			setIsOpen(true);
			
			if (safeNeighbours()) {
				neighbours.forEach(c -> c.openField());
			}
			return true;
		}
		return false;
	}
	
	public void switchMarked() {
		if(!open) {
			marked = !marked;
			
			if(marked) {
				notifyObservers(FieldEvent.MARK);
			} else {
				notifyObservers(FieldEvent.MARK_OFF);
			}
		}
	}
	
	boolean IgnoreBombAndOpen() {
		this.setMarked(false);
		if(!open) {
			open = true;
			notifyObservers(FieldEvent.CONTROLED_EXPLODE);
		}
		return open;
	}
	
	boolean addNeighbour(Field vizinho) {
		
		// Board limitations
		boolean lineWithinBoard = vizinho.line <= 99 && vizinho.line >= 0;
		boolean columnWithinBoard = vizinho.column <= 99 && vizinho.column >= 0;
		boolean campWithinBoard = lineWithinBoard && columnWithinBoard;
		
		// Check neighbour location
		boolean isDiferentLine = line != vizinho.line;
		boolean isDiferentColumn = column != vizinho.column;
		boolean isDiagonal = isDiferentLine && isDiferentColumn;
		
		// Compare the locations
		int deltaLine = Math.abs(line - vizinho.line);
		int deltaColumn = Math.abs(column - vizinho.column);
		int deltaGeral = deltaLine + deltaColumn;
		
		
		if (campWithinBoard) {
			// If neighbour is over, under, left or right side of this Field
			if (deltaGeral == 1 && !isDiagonal) {
				this.neighbours.add(vizinho);
				return true;
			}
			// If neighbour is on a diagonal of this Field
			if (deltaGeral == 2 && isDiagonal) {
				this.neighbours.add(vizinho);
				return true;
			}
		}
		return false;
	}

	boolean reachObjective() {
		boolean revealed = !mineField && open;
		boolean fieldProtected = mineField && marked;
		
		return revealed || fieldProtected;
	}

	boolean safeNeighbours() {
		return neighbours.stream().noneMatch(c -> c.mineField);
	}
	
	void setMine() {
		// Not a setter!
		mineField = true;
	}
		
	////////////   Getters and setters   //////////////
	public boolean isMarked() {
		return marked;
	}
	
	private void setMarked(boolean b) {
		this.marked = b;
	}

	
	public boolean isMineField() {
		return mineField;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setIsOpen(boolean option) {
		open = option;
		
		if (open) {
			notifyObservers(FieldEvent.OPEN);
		}
	}
	
	public long minesAround() {
		
		if (!isMineField()) {
		return neighbours.stream()
			.filter(v -> v.mineField)
			.count();
		}
		
		return 0;
	}

}
