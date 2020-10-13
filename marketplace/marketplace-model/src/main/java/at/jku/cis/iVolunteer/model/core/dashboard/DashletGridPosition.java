package at.jku.cis.iVolunteer.model.core.dashboard;

public abstract class DashletGridPosition {

	private int x;
	private int y;
	private int rows;
	private int cols;
	private int minItemRows;
	private int minItemCols;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getMinItemRows() {
		return minItemRows;
	}

	public void setMinItemRows(int minItemRows) {
		this.minItemRows = minItemRows;
	}

	public int getMinItemCols() {
		return minItemCols;
	}

	public void setMinItemCols(int minItemCols) {
		this.minItemCols = minItemCols;
	}
}
