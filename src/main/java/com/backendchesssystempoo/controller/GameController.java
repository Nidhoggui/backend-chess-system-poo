package com.backendchesssystempoo.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendchesssystempoo.boardgame.BoardException;
import com.backendchesssystempoo.facade.ChessGame;
import com.backendchesssystempoo.facade.exceptions.RunningGameException;

@RestController
@RequestMapping(value = "/chess")
public class GameController {
	@Autowired
	private ChessGame chessGame;
	
	@PostMapping(value = "/newgame")
	public ResponseEntity<String> newGame() {
		try {
			return ResponseEntity.ok().build();
		} catch(RunningGameException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE.value()).body(e.getMessage());
		}
		
	}
	
	@PostMapping(value = "/move/{position}")
	public ResponseEntity<String> move(@PathVariable String position) throws InterruptedException, ExecutionException, TimeoutException {
		try {
			return ResponseEntity.accepted().body(chessGame.move(position));
		} catch(BoardException e) {
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
}
