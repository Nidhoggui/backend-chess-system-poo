package com.backendchesssystempoo.engine;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.backendchesssystempoo.chess.ChessMatch;
import com.backendchesssystempoo.chess.ChessPiece;
import com.backendchesssystempoo.chess.Color;
import com.backendchesssystempoo.chess.pieces.Bishop;
import com.backendchesssystempoo.chess.pieces.King;
import com.backendchesssystempoo.chess.pieces.Knight;
import com.backendchesssystempoo.chess.pieces.Pawn;
import com.backendchesssystempoo.chess.pieces.Queen;
import com.backendchesssystempoo.chess.pieces.Rook;

public class FenPosition {
	private ClientTest stockfish;
	private ChessMatch chessMatch;
	
	private String chessPosition;
	private String castling;
	private String enPassant;
	private String halfmoves;
	private String fullmoves;
	
	private final char color = 'b';
	
	public FenPosition(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
	}
	
	public String convertPositions() throws InterruptedException, ExecutionException, TimeoutException {
		String result = "";
		int aux = 0;
		ChessPiece chessPieces[][] = chessMatch.getPieces();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(chessPieces[i][j] == null) {
					aux++;
				} else {
					if(chessPieces[i][j] instanceof Bishop && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "B";
					}
					if(chessPieces[i][j] instanceof Bishop && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "b";
					}
					
					if(chessPieces[i][j] instanceof King && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "K";
					}
					if(chessPieces[i][j] instanceof King && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "k";
					}
					
					if(chessPieces[i][j] instanceof Knight && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "N";
					}
					if(chessPieces[i][j] instanceof Knight && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "n";
					}
					
					if(chessPieces[i][j] instanceof Pawn && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "P";
					}
					if(chessPieces[i][j] instanceof Pawn && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "p";
					}
					
					if(chessPieces[i][j] instanceof Queen && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "Q";
					}
					if(chessPieces[i][j] instanceof Queen && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "q";
					}
					
					if(chessPieces[i][j] instanceof Rook && chessPieces[i][j].getColor() == Color.WHITE) {
						result += aux + "R";
					}
					if(chessPieces[i][j] instanceof Rook && chessPieces[i][j].getColor() == Color.BLACK) {
						result += aux + "r";
					}
					aux = 0;
				}
			}
			result += aux;
			aux = 0;
			if(i != 7) {
				result += "/";
			}
		}
		result = result.replace("0", "");
		stockfish = new ClientTest();
		return stockfish.bestMove(result);
	}
}
