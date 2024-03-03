package com.unlp.pdtr.app;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocket extends WebSocketClient {

  public MyWebSocket(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onClose(int arg0, String arg1, boolean arg2) {
    System.out.println("Connection closed by " + arg1);
    System.out.println("Connection closed by " + arg0);
    System.out.println("Connection closed by " + arg2);
  }

  @Override
  public void onError(Exception arg0) {
    System.out.println("Error: " + arg0.getMessage());
  }

  @Override
  public void onMessage(String arg0) {
    System.out.println("Received message: " + arg0);
  }

  @Override
  public void onOpen(ServerHandshake arg0) {
    System.out.println("Connection opened");
    send("42[\"subscribe\",{\"channel\":\"test\"}]");
    // send message to socket.io server
  }
}
