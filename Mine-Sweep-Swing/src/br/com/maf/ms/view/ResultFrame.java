package br.com.maf.ms.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.maf.ms.logic.Board;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame{
	
	String msg;
	Board b;
	
	public ResultFrame(String msg, Board b) {
		this.msg = msg;
		this.b = b;
		
		// Grid for this dialog
		GridLayout grid = new GridLayout(2,1);
		grid.setHgap(20);
        grid.setVgap(-75);
		setLayout(grid);
		
		JPanel panel = new JPanel();
		JPanel panelBtn = new JPanel();
		JLabel resultPanel = new JLabel(msg);
		JButton playAgainBtn = new JButton("Play again");
		JButton quitGameBtn = new JButton("Quit");
		
		// Text result settings
		resultPanel.setHorizontalAlignment(SwingConstants.CENTER);
		resultPanel.setVerticalAlignment(SwingConstants.CENTER);
		resultPanel.setFont(new Font("Arial", Font.BOLD, 28));
		resultPanel.setForeground(
				"You won!".equalsIgnoreCase(msg) ? new Color(0,153,0) : new Color(255,0,0)
					);
		
		// Btn settings
		playAgainBtn.addActionListener(e -> {
			b.restartBoard();
			dispose();
			});
		
		quitGameBtn.addActionListener(e -> {
			System.exit(0);
		});
		
		playAgainBtn.setFocusable(false);
		quitGameBtn.setFocusable(false);
		
		// BTN panel settings
		panelBtn.setLayout(new FlowLayout());
		panelBtn.add(playAgainBtn);
		panelBtn.add(quitGameBtn);
		
		// Full panel settings
		panel.setLayout(new BorderLayout());
		panel.add(resultPanel, BorderLayout.CENTER);
		panel.add(panelBtn, BorderLayout.PAGE_END);
		
		add(panel);
		setUndecorated(true);
		// Final settings for the dialog
		setSize(300, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
