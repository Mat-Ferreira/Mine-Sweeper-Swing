package br.com.maf.ms.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.maf.ms.logic.Board;

@SuppressWarnings("serial")
public class BoardFrame extends JPanel{
	
	private final Board tab;

	public BoardFrame(Board tab) {
		this.tab = tab;
		setLayout(new GridLayout(tab.getLines(), tab.getColumns()));
		setFields();
		tab.addObserver(e -> {
			//TODO show result to user
			if (e) {
				new ResultFrame("You won!", tab);
			} else {
				new ResultFrame("You lost!", tab);
			};
		});
	}
	
	private void setFields() {
		tab.getCamps().stream().forEach(f -> {
			add(new FieldButton(f)); 
		});
	}
}
