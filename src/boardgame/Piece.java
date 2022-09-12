package boardgame;

public class Piece {
	
	//I need to explain this "protected" status
	protected Position position;
	private Board board;
	
	public Piece(Board board)
	{
		this.board = board;
		position = null;
	}

	//the board is not accessible in top layers
	//the board is for intern use of this layer
	protected Board getBoard() {
		return board;
	}
	
	//without set, the user can't change the board
	
}
