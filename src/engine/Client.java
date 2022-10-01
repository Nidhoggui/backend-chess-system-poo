package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Client {
	private Process process = null;
	private BufferedReader reader = null;
	private OutputStreamWriter writer = null;
	
	public Client() { }
	
	public void start(String cmd) {
		var pb = new ProcessBuilder(cmd);
		try {
			this.process = pb.start();
			this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			this.writer = new OutputStreamWriter(process.getOutputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		if(this.process.isAlive()) {
			this.process.destroy();
		}
		try {
			reader.close();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public <T> T command(String cmd, Function<List<String>, T> commandProcessor, Predicate<String> breakCondition, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture<T> command = CompletableFuture.supplyAsync(() -> {
			final List<String> output = new ArrayList<>();
			try {
				writer.flush();
				writer.write(cmd + "\n");
				writer.write("isready\n");
				writer.flush();
				String line = "";
				while((line = reader.readLine()) != null) {
					if(line.contains("Unknown command")) {
						throw new RuntimeException(line);
					}
					if(line.contains("Unexpected token")) {
						throw new RuntimeException("Unexpected token: " + line);
					}
					output.add(line);
					if(breakCondition.test(line)) {
						break;
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			return commandProcessor.apply(output);
		});
		
		return command.get(timeout, TimeUnit.MILLISECONDS);
	}
	
}
