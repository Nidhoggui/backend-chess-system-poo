package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import engine.FenPosition;

public class Program {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		//testando
		
		//Position pos = new Position(3,5);
		//System.out.println(pos);
		
		//Board board = new Board(8,8);
		
		Scanner sc = new Scanner(System.in);
		String s;
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		FenPosition converter;
		
		String sourceAlgebraic;
		String targetAlgebraic;
		int aux = 0;
		
		while(!chessMatch.getCheckMate()) 
		{
			try
			{
				aux++;
				//UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				if(aux % 2 == 0) {
					converter = new FenPosition(chessMatch);
					System.out.println(converter.convertPositions());
					s = converter.convertPositions();
				} else {
					s = sc.next();
				}
			
				System.out.print("Source: ");
				sourceAlgebraic = s.substring(0, 2);
				targetAlgebraic = s.substring(2, 4);
				ChessPosition source = UI.readChessPosition(sourceAlgebraic);
				System.out.println();
			
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.print("Target: \n");
				//s = sc.next();
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
				sc.nextLine();
			}
			catch (InputMismatchException e){
				System.out.print(e.getMessage());
				sc.nextLine();
			}
		}
		UI.printMatch(chessMatch, captured);
	}

}
