package tetris;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.Semaphore;

import tetris.Piece.L;
import tetris.Piece.LMirror;
import tetris.Piece.Plank;
import tetris.Piece.S;
import tetris.Piece.SMirror;
import tetris.Piece.Square;
import tetris.Piece.T;

/**
 * cd /Users/matslexell/Documents/Mats/IT/Programmeri/workspace/EgnaProgram/bin/
 * java tetris/Tetris
 * 
 * @author matslexell
 * 
 */

public class Tetris {
	
	//Standard size is (18, 10)
	private int mtx[][] = new int[18][10];
	private final boolean GUI = true;

	private Semaphore sem = new Semaphore(1, true);
	private Gui gui;
	private Piece piece;
	private Piece next;
	private int points; 
	private long speed = 700;
	
	private Piece[] pieces = new Piece[7];
	private Color[] color = new Color[pieces.length];
	private boolean end = false;
	private boolean pause = false;

	public Tetris() {
		
		gui = new Gui("Tetris", mtx.length, mtx[0].length);
		gui.setTetris(this);

		
		pieces[0] = new Square();
		color[0] = Color.green;
		pieces[1] = new Plank();
		color[1] = Color.orange;
		pieces[2] = new T();
		color[2] = Color.yellow;
		pieces[3] = new L();
		color[3] = Color.blue;
		pieces[4] = new LMirror();
		color[4] = Color.cyan;
		pieces[5] = new S();
		color[5] = Color.red;
		pieces[6] = new SMirror();
		color[6] = Color.pink;

		
		next = pieces[(int) (Math.random() * pieces.length)];

		newPiece();
		setPiece(piece);
		updateGui();

		for (;!end;) {
			sleep();
			if (!end) {
				handleEvent(Piece.DOWN);
			}
		}
		System.out.println("GAME OVER!");
	}
		
	private void killLevels() {
		int newMtx[][] = new int[mtx.length][mtx[0].length];
		boolean[] copy = new boolean[mtx.length];
		boolean kill = false;
		outer: for (int row = 0; row < mtx.length; row++) {
			for (int col = 0; col < mtx[0].length; col++) {
				if (mtx[row][col] == 0) {
					copy[row] = true;
					continue outer;
				}
			}
			kill = true;
		}

		if (kill) {
			int count = 0;
			for (int row = mtx.length - 1; row >= 0; row--) {
				if (copy[row]) {
					for (int col = 0; col < mtx[0].length; col++) {
						newMtx[row + count][col] = mtx[row][col];

					}
				} else

					count++;
			}
			points += count;
			mtx = newMtx;
		}
	}

	public void sleep() {
		long sleep = (long) (speed*Math.pow(0.9, points)); 
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void updateGui() {
		if(GUI){
			gui.setPoints(points);
			for (int row = 0; row < mtx.length; row++) {
				for (int col = 0; col < mtx[0].length; col++) {
					if (mtx[row][col] == 0)
						gui.resetPixl(row, col);
					else {
						gui.setPixl(row, col, color[mtx[row][col]-1]);
					}

				}
			}
		}else{
			StringBuffer str = new StringBuffer();
			str.append("\n\n\n\n\n\n\n\n\n\n\n\n\n");
			StringBuffer top = new StringBuffer("\t");
			for (int i = 0; i < mtx[0].length; i++) {
				top.append(" -");
			}
			str.append(top);
			str.append("\n");
			for (int row = 0; row < mtx.length; row++) {
				str.append("\t|");
				for (int col = 0; col < mtx[0].length; col++) {
					if (mtx[row][col] == 0)
						str.append("  ");
					else
						str.append("*" + " ");

				}
				str.append("|\n");
			}
			str.append(top);
			System.out.println(str);
		}
	}

	public void handleEvent(int keyCode) {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!end && !pause) {
			resetPiece(piece);
			Piece p = piece.move(keyCode);
			if (interfere(p)) {
				if (keyCode == Piece.DOWN) {
					setPiece(piece);
					killLevels();
					newPiece();
				}
				setPiece(piece);
			} else {
				setPiece(p);
				piece = p;
			}
			updateGui();
		}
		sem.release();
	}

	private void newPiece() {
		piece = next;
		piece.setLocation(mtx[0].length / 2 - 1, 0);
		if (interfere(piece))
			end = true;

		next = pieces[(int) (Math.random() * pieces.length)];
		next.setLocation(2, 1);
		gui.setNextPieceFrame(next, color[next.getId()-1]);
	}

	public boolean interfere(Piece piece) {
		Point[] point = piece.getPosition();
		for (Point p : point) {
			if (!(p.y >= 0 && p.y < mtx.length && p.x >= 0 && p.x < mtx[0].length))
				return true;
			if (mtx[p.y][p.x] != 0)
				return true;
		}
		return false;
	}

	public void setPiece(Piece piece) {
		setCoordinate(piece, piece.getId());
	}

	public void resetPiece(Piece piece) {
		setCoordinate(piece, 0);
	}

	private void setCoordinate(Piece piece, int element) {
		Point[] p = piece.getPosition();
		for (Point point : p) {
			// TODO check restraints some other place
			mtx[point.y][point.x] = element;
		}
	}

	public void pause() {
		pause = !pause;
	}
	
	public void switchPiece() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Piece old = piece;
		resetPiece(old);
		Point p = old.getOrigo();
		newPiece();
		piece.setLocation(p.x,p.y);
		if(interfere(piece))
			piece = old;
		setPiece(piece);
		sem.release();
	}
	
	public static void main(String[] args) {
		new Tetris();
	}




}
