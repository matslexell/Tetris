package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PixlPanel extends JPanel {
	private JPanel p[][];
	private Color c[][];

	public PixlPanel(int row, int col, boolean border,int size,  Color... color) {
		
		setLayout(new GridLayout(row, col));
		p = new JPanel[row][col];
		c = new Color[row][col];
		int toggle = 0;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				add(p[i][j] = new JPanel());
				p[i][j].setBackground(color[toggle]);
				c[i][j] = color[toggle];
				p[i][j].setPreferredSize(new Dimension(size, size));
				if (border)
					p[i][j].setBorder(BorderFactory
							.createLineBorder(Color.darkGray));
				
				toggle = (toggle+1)%color.length;
			}
			toggle = (toggle+1)%color.length;
		}
	}

	public void reset(){
		for (int row = 0; row < p.length; row++) {
			for (int col = 0; col < p[0].length; col++) {
				p[row][col].setBackground(c[row][col]);
				
			}
		}
	}
	
	public void setPixl(int row, int col, Color c) {
		p[row][col].setBackground(c);
	}
	
	public void resetPixl(int row, int col){
		p[row][col].setBackground(c[row][col]);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Name");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
//		frame.setPreferredSize(new Dimension(23*10, 23*20));
		frame.setLocation(400, 200);
		frame.setBackground(Color.white);

		PixlPanel p = new PixlPanel(20, 10, true,23, new Color(30, 30, 30), Color.black);
		frame.add(p);
		
		p.setPixl(0, 0, Color.cyan);
		p.resetPixl(0, 0);


		frame.pack();
	}
}
