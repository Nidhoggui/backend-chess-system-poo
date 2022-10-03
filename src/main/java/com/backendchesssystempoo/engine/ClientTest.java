package com.backendchesssystempoo.engine;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class ClientTest {
	
	public ClientTest() { }

	public String bestMove(String fen) throws InterruptedException, ExecutionException, TimeoutException {
		var client = new Client();
		var position = fen + " 0 40";
		
		client.start("stockfish");
		
		client.command("uci", Function.identity(), (s) -> s.startsWith("uciok"), 2000l);
		
		client.command("position fen " + position, Function.identity(), s -> s.startsWith("readyok"), 2000l);
		
		String bestMove = client.command(
				"go movetime 3000",
				lines -> lines.stream().filter(s->s.startsWith("bestmove")).findFirst().get(),
				line -> line.startsWith("bestmove"),
				5000l)
				.split(" ")[1];
		
		client.close();
		
		return bestMove;
	}

}

