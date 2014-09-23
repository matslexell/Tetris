package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui implements ActionListener, KeyListener {

	private PixlPanel mainPixlPanel;
	private PixlPanel nextPiece;
	private JLabel pointsLabel;
	private Tetris tetris;

	public Gui(String name, int row, int col) {
		
		
		JFrame pieceFrame = new JFrame("Next Piece");
		pieceFrame.setVisible(true);
		pieceFrame.setLocation(700, 200);
		pieceFrame.setBackground(Color.white);
		pieceFrame.addKeyListener(this);
		nextPiece = new PixlPanel(4, 6, true, 23, new Color(30, 30, 30),
				Color.black);
		
		JPanel piecePanel = new JPanel(new GridLayout(2,1));
		piecePanel.setBackground(new Color(200, 200, 200));
		piecePanel.add(nextPiece);
		pointsLabel = new JLabel("\t\t\t\t\t\tRows: 0");
		piecePanel.add(pointsLabel);
		pieceFrame.add(piecePanel);
		pieceFrame.pack();

		
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(400, 200);
		frame.setBackground(Color.white);
		frame.addKeyListener(this);

		mainPixlPanel = new PixlPanel(row, col, true, 23, new Color(30, 30, 30),
				Color.black);
		
		

		frame.add(mainPixlPanel);
		frame.pack();
	}

	public void setPoints(int points){
		pointsLabel.setText("\t\t\t\t\t\tRows: " + points);
	}
	
	public void setTetris(Tetris tetris) {
		this.tetris = tetris;
	}

	public void setPixl(int row, int col, Color c) {
		mainPixlPanel.setPixl(row, col, c);
	}

	public void resetPixl(int row, int col) {
		mainPixlPanel.resetPixl(row, col);
	}
	
	public void setNextPieceFrame(Piece piece, Color c){
		nextPiece.reset();
		Point[] point = piece.getPosition();
		
		for (Point p : point) {
			nextPiece.setPixl(p.y, p.x, c);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 37 && e.getKeyCode() <= 40)
			tetris.handleEvent(e.getKeyCode());
		if (e.getKeyChar() == 'p')
			tetris.pause();
		if (e.getKeyChar() == 's'){
			tetris.switchPiece();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
