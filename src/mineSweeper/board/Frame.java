package mineSweeper.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Frame extends JFrame implements MouseListener {

	private int row = 30;
	private int col = 16;
	private int flagCount = 0;
	private boolean firstClick = true;
	private boolean dead = false;
	private int Mines = 50;
	private int firstx = 0;
	private int firsty = 0;

	private Button[][] buttons = new Button[row][col];
	private int[][] values = new int[row][col];

	public Frame(String s) {

		setTitle(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 0);
		setSize(getCol() * 42, getRow() * 28);

		JMenuBar bar = new JMenuBar();
		JMenu tools = new JMenu("Tools");
		JMenuItem reset = new JMenuItem("New Game");
		JMenuItem minescount = new JMenuItem("Mines");

		bar.add(tools);
		tools.add(reset);
		tools.add(minescount);
		add(bar, BorderLayout.NORTH);

		JPanel p = new JPanel();
		add(p);
		p.setLayout(new GridLayout(getRow(), getCol()));

		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getCol(); y++) {
				getButtons()[x][y] = new Button();
				p.add(getButtons()[x][y]);
				buttons[x][y].addMouseListener(this);
//				buttons[x][y].setIcon(new ImageIcon(
//						"file:///D:/minesweepererererererer/minesweeper/src/minesweeper/board/src/gfx/bomb.png"));
			}
		}

		this.setVisible(true);

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				generateMines();
				setFlagCount(0);
				setFirstClick(true);
				for (int x = 0; x < getRow(); x++) {
					for (int y = 0; y < getCol(); y++) {
						getValues()[x][y] = 0;
						getButtons()[x][y].setEnabled(true);
						getButtons()[x][y].setHasBomb(false);
						getButtons()[x][y].setStat(Status.Closed);
						getButtons()[x][y].setBackground(null);
						getButtons()[x][y].setText("");
						setDead(false);
					}
				}
//				this.validate();
			}

		});

		minescount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String minesCount = JOptionPane
						.showInputDialog("Enter Mines count numbers only and no more than 100 u dumb fuck! -_-", getMines()+"");

				int input = 0;
				try {
					input = Integer.parseInt(minesCount);
					System.out.println("int tmam");
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println("dumb dip shit!");
				}
				if (input != 0 && input < 100) {
					setMines(input);
				} else {
//					JFrame l = new JFrame();
//					l.setBounds(500, 500, 300, 200);
//					JTextArea x = new JTextArea("tsd2 2nta teet tab mfesh le3ba!");
//					l.add(x);
//					l.setVisible(true);
//					this.dispose();
				}

			}
		});

	}

	// methods
	public void generateMines() {
		// reset
		for (int x = 0; x < getMines();) {
			int rndx = (int) (Math.random() * getRow());
			int rndy = (int) (Math.random() * getCol());
			if (!getButtons()[rndx][rndy].isHasBomb()
					&& getButtons()[rndx][rndy] != getButtons()[getFirstx()][getFirsty()]) {
				x++;
				getValues()[rndx][rndy] = 10;
				getButtons()[rndx][rndy].setHasBomb(true);
			}
		}

		// Neighbors check
		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getCol(); y++) {

				if (getValues()[x][y] != 10) {
					int neighbourcount = 0;
					if (x > 0 && y > 0 && getValues()[x - 1][y - 1] == 10) {// up left
						neighbourcount++;
					}
					if (y > 0 && getValues()[x][y - 1] == 10) {// up
						neighbourcount++;
					}
					if (x < getValues().length - 1 && y > 0 && getValues()[x + 1][y - 1] == 10) {// up right
						neighbourcount++;
					}
					if (x > 0 && y < getValues()[0].length - 1 && getValues()[x - 1][y + 1] == 10) {// down left
						neighbourcount++;
					}
					if (y < getValues()[0].length - 1 && getValues()[x][y + 1] == 10) {// down
						neighbourcount++;
					}
					if (x < getValues().length - 1 && y < getValues()[0].length - 1
							&& getValues()[x + 1][y + 1] == 10) {// down right
						neighbourcount++;
					}
					if (x > 0 && getValues()[x - 1][y] == 10) {// left
						neighbourcount++;
					}
					if (x < getValues().length - 1 && getValues()[x + 1][y] == 10) {// right
						neighbourcount++;
					}

					getValues()[x][y] = neighbourcount;
				}
			}
		}

	}

	private void open(int x, int y) {
		if (getButtons()[x][y].getStat() == Status.Closed) {
			if (!getButtons()[x][y].isHasBomb()) {
				getButtons()[x][y].setText(getValues()[x][y] + "");
//		buttons[x ][y ].setFont((new Font("Arial", Font.BOLD, 20)));
				if (getValues()[x][y] == 0) {
					Zero(x, y);
					getButtons()[x][y].setText("");
				}
				getButtons()[x][y].setEnabled(false);
				getButtons()[x][y].setStat(Status.Opened);;
			} else {
				Dead();
			}
		}
	}

	public void Zero(int x, int y) {

		if (getButtons()[x][y].isEnabled() == true) {
			getButtons()[x][y].setEnabled(false);
			if (x > 0 && y > 0) {// up left
				open(x - 1, y - 1);
			}
			if (y > 0) {// up
				open(x, y - 1);
			}
			if (x < getValues().length - 1 && y > 0) {// up right
				open(x + 1, y - 1);
			}
			if (x > 0 && y < getValues()[0].length - 1) {// down left
				open(x - 1, y + 1);
			}
			if (y < getValues()[0].length - 1) {// down
				open(x, y + 1);
			}
			if (x < getValues().length - 1 && y < getValues()[0].length - 1) {// down right
				open(x + 1, y + 1);
			}
			if (x > 0) {// left
				open(x - 1, y);
			}
			if (x < getValues().length - 1) {// right
				open(x + 1, y);
			}
		}

	}

	private void openaround(int x, int y) {

		int count = 0;
		if (x > 0 && y > 0) {// up left
			if (getButtons()[x - 1][y - 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (y > 0) {// up
			if (getButtons()[x][y - 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (x < getValues().length - 1 && y > 0) {// up right
			if (getButtons()[x + 1][y - 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (x > 0 && y < getValues()[0].length - 1) {// down left
			if (getButtons()[x - 1][y + 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (y < getValues()[0].length - 1) {// down
			if (getButtons()[x][y + 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (x < getValues().length - 1 && y < getValues()[0].length - 1) {// down right
			if (getButtons()[x + 1][y + 1].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (x > 0) {// left
			if (getButtons()[x - 1][y].getStat() == Status.Flagged) {
				count++;
			}
		}
		if (x < getValues().length - 1) {// right
			if (getButtons()[x + 1][y].getStat() == Status.Flagged) {
				count++;
			}
		}

		if (count == getValues()[x][y]) {
			if (x > 0 && y > 0) {// up left
				open(x - 1, y - 1);
			}
			if (y > 0) {// up
				open(x, y - 1);
			}
			if (x < values.length - 1 && y > 0) {// up right
				open(x + 1, y - 1);
			}
			if (x > 0 && y < values[0].length - 1) {// down left
				open(x - 1, y + 1);
			}
			if (y < values[0].length - 1) {// down
				open(x, y + 1);
			}
			if (x < values.length - 1 && y < values[0].length - 1) {// down right
				open(x + 1, y + 1);
			}
			if (x > 0) {// left
				open(x - 1, y);
			}
			if (x < values.length - 1) {// right
				open(x + 1, y);
			}
		
		}

	}

	public void Dead() {
		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getCol(); y++) {
				if (getButtons()[x][y].isHasBomb()) {
					if (getButtons()[x][y].getStat() != Status.Flagged) {
						getButtons()[x][y].setBackground(Color.RED);
						getButtons()[x][y].setEnabled(false);
					} else {
						getButtons()[x][y].setBackground(Color.GREEN);
					}
				}

			}
		}
		setDead(true);
	}

	public void checkWin() {
		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getCol(); y++) {
				if (getButtons()[x][y].getStat() != Status.Opened && !getButtons()[x][y].isHasBomb()) {
					System.out.println("lesa");
					return;
				}
			}
		}
		System.out.println("tmm");
//		JTextPane x= new JTextPane();
//		x.setVisible(true);
//		f.add(x);
//		f.repaint();
//		x.setText("AYWA B2AAAAAAA GOOD JOB 2OOM SHOOF 2LY WARAK YANOOB!");
//		JLabel x= new JLabel("allahhhhhhhhhhhhhhhhhhhhhhhh 3lek ya fa5r el 3rab!");
//		x.setVisible(true);
//		f.add(x,BorderLayout.CENTER);
//		f.repaint();
		JFrame l = new JFrame();
		l.setBounds(20, 20, 800, 800);
//		JButton buto = new JButton(
//				new ImageIcon("file:///D:/minesweepererererererer/minesweeper/src/minesweeper/board/src/gfx/bomb.png"));
//		buto.validate();
//		buto.setVisible(true);
//		l.add(buto);
//		JTextArea x = new JTextArea("YABN EL LA3EBA! BRAFO! :)");
//		l.add(x);
		l.setVisible(true);
		Dead();

//		for (int x1 = 0; x1 < row; x1++) {
//			for (int y = 0; y < col; y++) {
//				getButtons()[x1][y].setEnabled(false);
//			}
//		}

	}

	// getters/setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getMines() {
		return Mines;
	}

	public void setMines(int mines) {
		Mines = mines;
	}

	public int getFirstx() {
		return firstx;
	}

	public void setFirstx(int firstx) {
		this.firstx = firstx;
	}

	public int getFirsty() {
		return firsty;
	}

	public void setFirsty(int firsty) {
		this.firsty = firsty;
	}

	public Button[][] getButtons() {
		return buttons;
	}

	public void setButtons(Button[][] buttons) {
		this.buttons = buttons;
	}

	public int[][] getValues() {
		return values;
	}

	public void setValues(int[][] values) {
		this.values = values;
	}

	public int getFlagCount() {
		return flagCount;
	}

	public void setFlagCount(int flagCount) {
		this.flagCount = flagCount;
	}

	public boolean isFirstClick() {
		return firstClick;
	}

	public void setFirstClick(boolean firstClick) {
		this.firstClick = firstClick;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	// mouselistner
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isFirstClick()) {
			if (!isDead()) {
				for (int x = 0; x < getRow(); x++) {
					for (int y = 0; y < getCol(); y++) {

						if (e.getSource().equals(getButtons()[x][y])) {

							if (getButtons()[x][y].getStat() != Status.Opened) {
								if (e.getButton() == MouseEvent.BUTTON1
										&& getButtons()[x][y].getStat() != Status.Flagged) {
									open(x, y);
								}

								if (e.getButton() == MouseEvent.BUTTON3) {// if right click put flag or remove it

									if (getButtons()[x][y].getStat() != Status.Flagged) {
										if (getButtons()[x][y].isEnabled()) {

											if (getFlagCount() != getMines()) {
												getButtons()[x][y].setBackground(Color.BLUE);
												getButtons()[x][y].setEnabled(false);
												getButtons()[x][y].setStat(Status.Flagged);
												setFlagCount(getFlagCount()+1);
											}
										}
									} else {
										getButtons()[x][y].setBackground(null);
										getButtons()[x][y].setEnabled(true);
										getButtons()[x][y].setStat(Status.Closed);
										setFlagCount(getFlagCount()-1);
									}
								}
								checkWin();
							} else if (e.getClickCount() == 2) {
								openaround(x, y);
								System.out.println("doubled");
							}

						}

					}
				}
			}
		} else {
			for (int x = 0; x < getRow(); x++) {
				for (int y = 0; y < getCol(); y++) {

					if (e.getSource().equals(getButtons()[x][y])) {
						setFirstx(x);
						setFirsty(y);
						setFirstClick(false);
						generateMines();
						open(getFirstx(), getFirsty());
						validate();
						System.out.println("first click Safe!");
						x = getRow(); // to break the for loops (super saver wkeda)! :D
						y = getCol();
					}
				}
			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
