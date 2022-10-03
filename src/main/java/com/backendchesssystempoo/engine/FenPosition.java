package com.backendchesssystempoo.engine;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.backendchesssystempoo.chess.ChessMatch;
import com.backendchesssystempoo.chess.ChessPiece;
import com.backendchesssystempoo.chess.ChessPosition;
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
	
	public FenPosition(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
	}
	
	public String convertPositions() {
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
		//stockfish = new ClientTest();
		//return stockfish.bestMove(result);
		return result;
	}
	
	public String getCastling() {
		String result = "";
		String whiteKingSide = "";
		String whiteQueenSide = "";
		String blackKingSide = "";
		String blackQueenSide = "";
		ChessPiece chessPieces[][] = chessMatch.getPieces();
		
		if(chessPieces[7][4] instanceof King && chessPieces[7][4].getColor() == Color.WHITE) {
			if(chessPieces[7][7] instanceof Rook && chessPieces[7][7].getColor() == Color.WHITE) {
				if(chessPieces[7][7].getMoveCount() == 0) {
					whiteKingSide = "K";
				}
			}
			if(chessPieces[7][0] instanceof Rook && chessPieces[7][0].getColor() == Color.WHITE) {
				if(chessPieces[7][0].getMoveCount() == 0) {
					whiteQueenSide = "Q";
				}
			}
		}
		
		if(chessPieces[0][4] instanceof King && chessPieces[0][4].getColor() == Color.BLACK) {
			if(chessPieces[0][7] instanceof Rook && chessPieces[0][7].getColor() == Color.BLACK) {
				if(chessPieces[0][7].getMoveCount() == 0) {
					blackKingSide = "k";
				}
			}
			if(chessPieces[0][0] instanceof Rook && chessPieces[0][0].getColor() == Color.BLACK) {
				if(chessPieces[0][0].getMoveCount() == 0) {
					blackQueenSide = "q";
				}
			}
		}
		
		result = whiteKingSide + whiteQueenSide + blackKingSide + blackQueenSide;
		return result;
	}
	
	public String getEnPassant() {
		//ChessPiece chessPieces[][] = chessMatch.getPieces();
		ChessPiece enPassantVulnerable = chessMatch.getEnPassantVulnerable();
		
		ChessPosition aux;
		String result = "";
		if(enPassantVulnerable != null) {
			aux = enPassantVulnerable.getChessPosition();
			if(enPassantVulnerable.getColor() == Color.WHITE) {
				result = "" + aux.getColumn() + (aux.getRow() - 1);
			}else {
				result = "" + aux.getColumn() + (aux.getRow() + 1);
			}
		}
		
		return result;
	}
	
	public String getBestMove() throws InterruptedException, ExecutionException, TimeoutException {
		String result = "";
		
		result = this.convertPositions();
		
		if(this.getCastling().equals("")) {
			result += " b -";
		}else {
			result += " b " + this.getCastling();
		}
		
		if(this.getEnPassant().equals("")) {
			result += " -";
		}else {
			result += " " + this.getEnPassant();
		}
		
		stockfish = new ClientTest();
		System.out.println(result + "\n");
		return stockfish.bestMove(result);
	}
}
