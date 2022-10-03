package com.backendchesssystempoo.facade;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.backendchesssystempoo.application.UI;
import com.backendchesssystempoo.boardgame.BoardException;
import com.backendchesssystempoo.chess.ChessException;
import com.backendchesssystempoo.chess.ChessMatch;
import com.backendchesssystempoo.chess.ChessPiece;
import com.backendchesssystempoo.chess.ChessPosition;
import com.backendchesssystempoo.chess.Color;
import com.backendchesssystempoo.engine.FenPosition;
import com.backendchesssystempoo.facade.exceptions.RunningGameException;

@Service
public class ChessGame {
	ChessMatch chessMatch;
	List<ChessPiece> captured;
	FenPosition converter;
	
	public void newGame() {
		if(chessMatch == null) {
			chessMatch = new ChessMatch();
			captured = new ArrayList<>();
		} else {
			throw new RunningGameException("already running game");
		}
	}
	
	private String bot() throws InterruptedException, ExecutionException, TimeoutException {
		String s = null;
		
		
		String sourceAlgebraic;
		String targetAlgebraic;
		
		//if(!chessMatch.getCheckMate()) 
		//{
			try
			{
				//UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				converter = new FenPosition(chessMatch);
				System.out.println(converter.getBestMove());
				s = converter.getBestMove();
			
				System.out.print("Source: ");
				sourceAlgebraic = s.substring(0, 2);
				targetAlgebraic = s.substring(2, 4);
				ChessPosition source = UI.readChessPosition(sourceAlgebraic);
				System.out.println();
			
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.print("Target: \n");
				ChessPosition target = UI.readChessPosition(targetAlgebraic);
			
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null)
				{
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null)
				{
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = s.substring(4, 5);
					type = type.toUpperCase();
					System.out.println(type);
					chessMatch.replacePromotedPiece(type);
				}
			}catch (ChessException e){
				System.out.print(e.getMessage());
			}
			catch (InputMismatchException e){
				System.out.print(e.getMessage());
			}
			catch (BoardException e) {
				System.out.println(e.getMessage());
			}
		//}
		UI.printMatch(chessMatch, captured);
		return s;
	}
	
	private void player(String position) throws InterruptedException, ExecutionException, TimeoutException {
		String s = null;
		
		
		String sourceAlgebraic;
		String targetAlgebraic;
		
		//if(!chessMatch.getCheckMate()) 
		//{
			try
			{
				//UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				s = position;
			
				System.out.print("Source: ");
				sourceAlgebraic = s.substring(0, 2);
				targetAlgebraic = s.substring(2, 4);
				ChessPosition source = UI.readChessPosition(sourceAlgebraic);
				System.out.println();
			
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.print("Target: \n");
				ChessPosition target = UI.readChessPosition(targetAlgebraic);
			
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null)
				{
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null)
				{
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = s.substring(4, 5);
					type = type.toUpperCase();
					System.out.println(type);
					chessMatch.replacePromotedPiece(type);
				}
			}catch (ChessException e){
				System.out.print(e.getMessage());
			}
			catch (InputMismatchException e){
				System.out.print(e.getMessage());
			}
			catch (BoardException e) {
				System.out.println(e.getMessage());
			}
		//}
		UI.printMatch(chessMatch, captured);
	}

	public String move(String position) throws InterruptedException, ExecutionException, TimeoutException {
		
		if(!chessMatch.getCheckMate()) 
		{
			player(position);
		} else {
			chessMatch = null;
			throw new BoardException(chessMatch.getCurrentPlayer() + " wins!");
		}
		
		if(!chessMatch.getCheckMate()) {
			return bot();
		} else {
			chessMatch = null;
			throw new BoardException(chessMatch.getCurrentPlayer() + " wins!");
		}
	}

}
