package com.unlp.pdtr.app;

import java.time.Instant;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import com.unlp.pdtr.app.WebSocketServiceGrpc.WebSocketServiceImplBase;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse;

public class WebSocketServiceImpl extends WebSocketServiceImplBase
{

    @Override
    public StreamObserver<WebSocketRequest> showContent(final StreamObserver<WebSocketResponse> responseObserver) {

        return new StreamObserver<WebSocketRequest>() {
            @Override
            public void onNext(WebSocketRequest request) {
                //TODO show in webpage
                System.out.println("Llega a WebSocket");
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
