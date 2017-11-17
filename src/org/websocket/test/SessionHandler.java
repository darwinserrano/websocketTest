package org.websocket.test;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

public class SessionHandler {
	private static SessionHandler instance = null;
	private final Set<Session> sessions = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        sendToAllConnectedSessions("{\"msg\": \"Usuario se ha desconectado\"}");
    }

    public void addMessage(String message) {
        sendToAllConnectedSessions(message);
    }
    
    private void sendToAllConnectedSessions(String message) {
    	for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, String message) {
    	try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
    public synchronized static SessionHandler getSessionHandler() {
        if (instance == null) { instance = new SessionHandler(); }
        return instance;
    }
}
