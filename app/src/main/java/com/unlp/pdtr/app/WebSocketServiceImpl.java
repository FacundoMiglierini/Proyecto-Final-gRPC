package com.unlp.pdtr.app;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import com.unlp.pdtr.app.WebSocketServiceGrpc.WebSocketServiceImplBase;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest;

public class WebSocketServiceImpl extends WebSocketServiceImplBase
{

    @Override
    public StreamObserver<WebSocketRequest> showContent(final StreamObserver<Empty> responseObserver) {

        return new StreamObserver<WebSocketRequest>() {
            @Override
            public void onNext(WebSocketRequest request) {
                //TODO show content on webpage
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Empty.getDefaultInstance());
                System.out.println("SHOW OPERATION FINISHED");
            }
        };
    }
}
