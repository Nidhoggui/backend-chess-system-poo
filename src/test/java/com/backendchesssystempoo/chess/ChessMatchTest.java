package com.backendchesssystempoo.chess;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.backendchesssystempoo.chess.pieces.King;

class ChessMatchTest {

	@Test
	void testCheckmate() {
		ChessMatch chessMatch = new ChessMatch();
		assertFalse(chessMatch.getCheckMate());
	}
	
	@Test
	void testKingPosition() {
		ChessMatch chessMatch = new ChessMatch();
		ChessPiece[][] pieces = chessMatch.getPieces();
		
		assertTrue(pieces[0][4] instanceof King);
	}
	
	@Test
	void testVacantPosition() {
		ChessMatch chessMatch = new ChessMatch();
		ChessPiece[][] pieces = chessMatch.getPieces();
		
		assertTrue(pieces[2][2] == null);
	}

}
