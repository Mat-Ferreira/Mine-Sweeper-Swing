package br.com.maf.ms.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.maf.ms.logic.Field;
import br.com.maf.ms.logic.FieldEvent;
import br.com.maf.ms.logic.FieldObserver;

@SuppressWarnings("serial")
public class FieldButton extends JButton
	implements FieldObserver,MouseListener {
	
	private Field field;
	
	private final Color BG_DEFAULT = new Color (184,184,184); // Light grey
	private final Color BG_DEFAULT_DARK = new Color (153, 153, 153); // Darker grey 
	private final Color BG_MARKED = new Color (8,179,247); // Light blue
	private final Color BG_MINE_FIELD = new Color (189,66,68); // Red
	
	private final Color GREEN_TEXT = new Color (0,150,0);
	private final Color YELLOW_TEXT = new Color (221, 220, 5);
	private final Color RED_TEXT = new Color (239, 27, 57);
	
	public FieldButton(Field field) {
		this.field = field;
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_DEFAULT);
		addMouseListener(this);
		field.addObserver(this);
	}

	@Override
	public void eventTriggered(Field f, FieldEvent e) {
		// will handle the styling of the button for each event
		switch(e) {
		case OPEN:
			applyStyleOpen();
			break;
		case MARK:
			applyStyleMarked();
			break;
		case EXPLODE:
			applyStyleMineField();
			break;
		case MARK_OFF:
			applyStyleDefault();
		default:
			applyStyleDefault();
		}
		
	}

	private void applyStyleDefault() {
		setBackground(BG_DEFAULT);
	}

	private void applyStyleMineField() {
		setBackground(BG_MINE_FIELD);		
	}

	private void applyStyleMarked() {
		setBackground(BG_MARKED);
	}

	private void applyStyleOpen() {
		long mines = field.minesAround();
		setBackground(BG_DEFAULT_DARK);
		
		if (mines > 0) {
			setText(String.format("%d", mines));
			if (mines < 3) {
				setForeground(GREEN_TEXT);
			} else if (mines == 3) {
				setForeground(YELLOW_TEXT);
			} else if (mines > 3) {
				setForeground(RED_TEXT);
			}
		}
	}
	
	////////////////////// MOUSE EVENTS INTERFACE //////////////////////
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			field.openField();			
		} else if (SwingUtilities.isRightMouseButton(e)){
			field.switchMarked();
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
