// The point class. Acts as a remote to each block in the playArea.

public class point {

	int x;
	int y;
	int dPos;
	int rPos;
	int lPos;

	public point(int x, int y, int dp, int rp, int lp) {

		this.x = x;
		this.y = y;
		this.dPos = dp;
		this.rPos = rp;
		this.lPos = lp;

	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
