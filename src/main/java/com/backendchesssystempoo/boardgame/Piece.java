package com.backendchesssystempoo.boardgame;

public abstract class Piece {
	
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
	
	public abstract boolean[][] possibleMoves();
	
	// hook method -> make a "hook" with the subclass
	public boolean possibleMove(Position position)
	{
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove()
	{
		boolean[][] mat = possibleMoves();
		
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]==true)
				{
					return true;
				}
			}
		}
		return false;
	}
	
}
