package br.com.maf.ms.view;

import javax.swing.JFrame;

import br.com.maf.ms.logic.Board;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	public MainFrame() {
		
		Board tab = new Board(16,30,50);
		BoardFrame bf = new BoardFrame(tab);
		
		setTitle("Mine Sweeper");
		setSize(690,438);
		
		add(bf);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
