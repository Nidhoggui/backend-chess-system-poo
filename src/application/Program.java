package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		//testando
		
		//Position pos = new Position(3,5);
		//System.out.println(pos);
		
		//Board board = new Board(8,8);
		
		Scanner sc = new Scanner(System.in);
		String s;
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) 
		{
			try
			{
				//UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
			
				System.out.print("Source: ");
				s = sc.next();
				ChessPosition source = UI.readChessPosition(s);
				System.out.println();
			
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.print("Target: ");
				s = sc.next();
				ChessPosition target = UI.readChessPosition(s);
			
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null)
				{
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null)
				{
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
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
