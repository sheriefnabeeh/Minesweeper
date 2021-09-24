package mineSweeper.board;

import javax.swing.JButton;

public class Button extends JButton {

	private boolean hasBomb;
	private Status stat;

	Button() {
		hasBomb = false;
		stat = Status.Closed;
	}

	public boolean isHasBomb() {
		return hasBomb;
	}

	public void setHasBomb(boolean hasBomb) {
		this.hasBomb = hasBomb;
	}

	public Status getStat() {
		return stat;
	}

	public void setStat(Status stat) {
		this.stat = stat;
	}

	
	
}
