package training.websocketclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class WebsocketClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		WebSocketClient client = new SockJsClient(
				Arrays.asList(new WebSocketTransport(new StandardWebSocketClient())));
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSessionHandler sessionHandler = new MessageStompSessionHandler();
		Future<StompSession> future = stompClient.connect("ws://localhost:8080/websocket-endpoint", sessionHandler);
		StompSession session = future.get(10, TimeUnit.SECONDS);
		String line = null;
		do {
			line = new Scanner(System.in).nextLine();
			session.send("/app/messages", new MessageCommand(line));
		} while (!line.trim().equals(""));
	}
}
