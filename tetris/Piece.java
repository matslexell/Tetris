package tetris;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

/**
 * UNFINISHED get bottom points
 * 
 * @author matslexell
 * 
 */

public abstract class Piece {
	final static int LEFT = 37;
	final static int FLIP = 38;
	final static int RIGHT = 39;
	final static int DOWN = 40;

	final static int SQUARE = 1;
	final static int PLANK = 2;
	final static int T = 3;
	final static int L = 4;
	final static int LMIRROR = 5;
	final static int S = 6;
	final static int SMIRROR = 7;

	protected Point o;
	private int flip = 0;

	public Piece() {
		o = new Point();
	}

	public Piece(Point o, int flip) {
		this.o = o;
		this.flip = flip;
	}

	public void setLocation(int x, int y) {
		o.x = x;
		o.y = y;
	}

	public Piece clone() {
		try {
			Piece p = getClass().getDeclaredConstructor(Point.class, int.class)
					.newInstance(o.getLocation(), flip);
			return p;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Piece move(int keyCode) {
		Piece p = clone();

		switch (keyCode) {
		case LEFT:
			p.o.translate(-1, 0);
			break;
		case FLIP: // up
			p.flip++;
			break;
		case RIGHT: // right
			p.o.translate(1, 0);
			break;
		case DOWN: // down
			p.o.translate(0, 1);
		}

		return p;
	}
	
	public Point getOrigo(){
		return o.getLocation();
	}

	public abstract int getId();

	public abstract Point[] getPosition();

	public String toString() {
		return o.toString();
	}

	public static class Square extends Piece {

		public Square() {
		}
		
		public Square(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = 0;
			return new Point[] { new Point(o), new Point(o.x + 1, o.y),
					new Point(o.x, o.y + 1), new Point(o.x + 1, o.y + 1) };
		}

		@Override
		public int getId() {
			return 1;
		}

	}

	public static class Plank extends Piece {

		public Plank() {
		}

		public Plank(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 2;
			if (super.flip == 0)
				return new Point[] { new Point(o.x - 1, o.y), new Point(o),
						new Point(o.x + 1, o.y), new Point(o.x + 2, o.y) };
			return new Point[] { new Point(o.x, o.y - 1), new Point(o),
					new Point(o.x, o.y + 1), new Point(o.x, o.y + 2) };

		}

		@Override
		public int getId() {
			return 2;
		}

	}

	public static class T extends Piece {

		public T() {
		}

		public T(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 4;
			switch (super.flip) {
			case 0:
				return new Point[] { new Point(o.x - 1, o.y), new Point(o),
						new Point(o.x + 1, o.y), new Point(o.x, o.y + 1) };

			case 1:
				return new Point[] { new Point(o.x - 1, o.y), new Point(o),
						new Point(o.x, o.y - 1), new Point(o.x, o.y + 1) };

			case 2:
				return new Point[] { new Point(o.x - 1, o.y), new Point(o),
						new Point(o.x, o.y - 1), new Point(o.x + 1, o.y) };

			}
			return new Point[] { new Point(o.x + 1, o.y), new Point(o),
					new Point(o.x, o.y - 1), new Point(o.x, o.y + 1) };
		}

		@Override
		public int getId() {
			return 3;
		}

	}

	public static class L extends Piece {

		public L() {
		}

		public L(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 4;
			switch (super.flip) {
			case 0:
				return new Point[] { new Point(o), new Point(o.x - 1, o.y),
						new Point(o.x - 1, o.y + 1), new Point(o.x + 1, o.y) };

			case 1:
				return new Point[] { new Point(o), new Point(o.x, o.y - 1),
						new Point(o.x - 1, o.y - 1), new Point(o.x, o.y + 1) };

			case 2:
				return new Point[] { new Point(o), new Point(o.x - 1, o.y),
						new Point(o.x + 1, o.y), new Point(o.x + 1, o.y - 1) };

			}
			return new Point[] { new Point(o), new Point(o.x, o.y - 1),
					new Point(o.x, o.y + 1), new Point(o.x + 1, o.y + 1) };
		}

		@Override
		public int getId() {
			return 4;
		}

	}

	public static class LMirror extends Piece {

		public LMirror() {
		}

		public LMirror(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 4;
			switch (super.flip) {
			case 0:
				return new Point[] { new Point(o), new Point(o.x - 1, o.y),
						new Point(o.x + 1, o.y), new Point(o.x + 1, o.y + 1) };

			case 1:
				return new Point[] { new Point(o), new Point(o.x, o.y - 1),
						new Point(o.x, o.y + 1), new Point(o.x - 1, o.y + 1) };

			case 2:
				return new Point[] { new Point(o), new Point(o.x - 1, o.y),
						new Point(o.x + 1, o.y), new Point(o.x - 1, o.y - 1) };

			}
			return new Point[] { new Point(o), new Point(o.x, o.y - 1),
					new Point(o.x, o.y + 1), new Point(o.x + 1, o.y - 1) };
		}

		@Override
		public int getId() {
			return 5;
		}

	}

	public static class S extends Piece {

		public S() {
		}

		public S(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 2;
			if (super.flip == 0)
				return new Point[] { new Point(o), new Point(o.x + 1, o.y),
						new Point(o.x, o.y + 1), new Point(o.x - 1, o.y + 1) };

			return new Point[] { new Point(o), new Point(o.x, o.y - 1),
					new Point(o.x + 1, o.y), new Point(o.x + 1, o.y + 1) };
		}

		@Override
		public int getId() {
			return 6;
		}

	}

	public static class SMirror extends Piece {

		public SMirror() {
		}

		public SMirror(Point o, int flip) {
			super(o, flip);
			
		}

		@Override
		public Point[] getPosition() {
			super.flip = super.flip % 2;
			if (super.flip == 0)
				return new Point[] { new Point(o), new Point(o.x - 1, o.y),
						new Point(o.x, o.y + 1), new Point(o.x + 1, o.y + 1) };

			return new Point[] { new Point(o), new Point(o.x, o.y - 1),
					new Point(o.x - 1, o.y), new Point(o.x - 1, o.y + 1) };
		}

		@Override
		public int getId() {
			return 7;
		}
	}

}
