package com.unlp.pdtr.app;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket.Options;

import com.unlp.pdtr.app.WebSocketServiceGrpc.WebSocketServiceImplBase;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse;

public class WebSocketServiceImpl extends WebSocketServiceImplBase {
  private Socket socket;
  public WebSocketServiceImpl() throws URISyntaxException, InterruptedException {
    if (System.getenv("SOCKET_ENV") != null) {
      socket = IO.socket("ws://frontend:60200");
    } else {
      socket = IO.socket("ws://localhost:60200");
    }
    socket.connect();
    socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        System.out.println("Error");
        System.out.println(args[0].toString());
      }
    });
    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        System.out.println("Connected");
        socket.emit("message", "Hello from Java");
      }
    });
    System.out.println("Socket created");
  }

  @Override
  public StreamObserver<WebSocketRequest> showContent(final StreamObserver<WebSocketResponse> responseObserver) {

    return new StreamObserver<WebSocketRequest>() {
      @Override
      public void onNext(WebSocketRequest request) {
        System.out.println("Llega a WebSocketx");
        Gson gson = new Gson();
        String json = gson.toJson(request);
        socket.emit("message", json);
        System.out.println(request.toString());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println(t.toString());
      }

      @Override
      public void onCompleted() {
        //TODO setear timestamp de ultima entrada cargada
        Instant currentTimestamp = Instant.now();
        Timestamp time = Timestamp.newBuilder()
                         .setSeconds(currentTimestamp.getEpochSecond())
                         .setNanos(currentTimestamp.getNano())
                         .build();

        WebSocketResponse response = WebSocketResponse.newBuilder().setTime(time).build();
        responseObserver.onNext(response);
        System.out.println("SHOW OPERATION FINISHED");
      }
    };
  }
}
