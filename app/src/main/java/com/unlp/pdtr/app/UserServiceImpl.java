package com.unlp.pdtr.app;

import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Empty;
import com.unlp.pdtr.app.DatabaseServiceGrpc.DatabaseServiceStub;
import com.unlp.pdtr.app.UserServiceGrpc.UserServiceImplBase;
import com.unlp.pdtr.app.UserServiceOuterClass.UserRequest;
import com.unlp.pdtr.app.WebSocketServiceGrpc.WebSocketServiceStub;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest;
import com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse;


public class UserServiceImpl extends UserServiceImplBase
{

    /*
     * Recibir data de trafico:
     *      - Recibir un conjunto de datos de request
     * Enviar la data recibida:
     *      - Generar request con data recibida a services de DB y WebSocket
     *      - De forma asincrónica enviar los datos en el request
     *      - Esperar por confirmación de recepcion
     *
     */


    private DatabaseServiceStub databaseStub;
    private WebSocketServiceStub webSocketStub;


    public UserServiceImpl() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        
        // Generate the database stub
        databaseStub = DatabaseServiceGrpc.newStub(channel);

        // Generate the websocket stub
        webSocketStub = WebSocketServiceGrpc.newStub(channel);
    }

    @Override
    public StreamObserver<UserRequest> storeTrafficData(final StreamObserver<Empty> responseObserver) {

        return new StreamObserver<UserRequest>() {
            @Override
            public void onNext(UserRequest request) {
                DBRequest databaseRequest;
                WebSocketRequest webSocketRequest;

                // Create a StreamObserver to handle the response for DB Request
                // TODO check timestamp for resending data stored in buffer
                StreamObserver<DBResponse> databaseResponseObserver = new StreamObserver<DBResponse>() {
                    @Override
                    public void onNext(DBResponse response) {
                        // Process the empty response from the server
                        System.out.println("MESSAGE RECEIVED FROM DATABASE");
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Handle errors
                    }

                    @Override
                    public void onCompleted() {
                        // Handle completion of the call
                    }
                };

                // Create a StreamObserver to handle the response for WebSocket Request
                // TODO check timestamp for resending data stored in buffer
                StreamObserver<WebSocketResponse> webSocketResponseObserver = new StreamObserver<WebSocketResponse>() {
                    @Override
                    public void onNext(WebSocketResponse response) {
                        System.out.println("MESSAGE RECEIVED FROM WEBSOCKET");
                        // Process the empty response from the server
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Handle errors
                    }

                    @Override
                    public void onCompleted() {
                        // Handle completion of the call
                    }
                };

                // Make the asynchronous client streaming RPC call 
                StreamObserver<DBRequest> databaseRequestObserver = databaseStub.storeInDatabase(databaseResponseObserver);
                StreamObserver<WebSocketRequest> webSocketRequestObserver = webSocketStub.showContent(webSocketResponseObserver);

                databaseRequest = DBRequest.newBuilder()
                    .setRoad(request.getRoad())
                    .setRegion(request.getRegion())
                    .setTime(request.getTime())
                    .setComment(request.getComment())
                    .build();

                webSocketRequest = WebSocketRequest.newBuilder()
                    .setRoad(request.getRoad())
                    .setRegion(request.getRegion())
                    .setTime(request.getTime())
                    .setComment(request.getComment())
                    .build();

                databaseRequestObserver.onNext(databaseRequest);
                webSocketRequestObserver.onNext(webSocketRequest);
                
                // TODO store data into a buffer

                System.out.println(request.toString());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Empty.getDefaultInstance());
                System.out.println("STORE OPERATION FINISHED");
            }
        };
    }
}
