package org.websocket.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/actions")
public class WebSocketServer {

	private SessionHandler sessionHandler = SessionHandler.getSessionHandler();
	
	
	@OnOpen
	public void onOpen(Session session) {
		sessionHandler.addSession(session);
		System.out.println("Nueva conexión");
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		sessionHandler.addMessage(message);
	}
	
	@OnError
	public void onError(Throwable error) {
		Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
	}
	
	@OnClose
	public void onClose(Session session) {
		sessionHandler.removeSession(session);
		System.out.println("Cerró la conexión");
	}
}
