package com.unlp.pdtr.app;

import java.time.Instant;
import java.util.Random;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import com.unlp.pdtr.app.UserServiceGrpc.UserServiceStub;
import com.unlp.pdtr.app.UserServiceOuterClass.UserRequest;

public class User {
    public static void main(String[] args) {

        /*
         * Generar data de trafico:
         *      - Dado un conjunto de opciones (estado de trafico, congestion, velocidad promedio), elegir una opción de forma aleatoria
         *      - Generar objeto request con la opción elegida
         *      - Agregarlo a buffer con data para stream de envío
         * Enviarla a UserService:
         *      - De forma asincrónica enviar los datos en el request
         *
         */

        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        
        // Generate the client stub
        UserServiceStub asyncStub = UserServiceGrpc.newStub(channel);

        // Create a StreamObserver to handle the response
        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty response) {
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

        // Make the asynchronous client streaming RPC call with an empty response
        StreamObserver<UserRequest> requestObserver = asyncStub.storeTrafficData(responseObserver);

        // Send multiple request messages asynchronously
        while (true) {
            int value = new Random().nextInt(3) + 1;
            UserRequest request;

            String road = "Ruta 2";
            String region = "Lezama";
            Instant currentTimestamp = Instant.now();
            Timestamp currentTime = Timestamp.newBuilder()
                    .setSeconds(currentTimestamp.getEpochSecond())
                    .setNanos(currentTimestamp.getNano())
                    .build();

            switch (value) {
                case 1:
                    request = UserRequest.newBuilder()
                        .setRoad(road)
                        .setRegion(region)
                        .setTime(currentTime)
                        .setComment("Trafico congestionado")
                        .build();
                    break;
                case 2:
                    request = UserRequest.newBuilder()
                        .setRoad(road)
                        .setRegion(region)
                        .setTime(currentTime)
                        .setComment("Velocidad promedio: 60 km/h")
                        .build();
                    break;
                case 3:
                    request = UserRequest.newBuilder()
                        .setRoad(road)
                        .setRegion(region)
                        .setTime(currentTime)
                        .setComment("Estado: accidente")
                        .build();
                    break;
                default:
                    request = UserRequest.newBuilder()
                        .setRoad(road)
                        .setRegion(region)
                        .setTime(currentTime)
                        .setComment("Velocidad promedio: 120 km/h")
                        .build();
                    break;
            }
            requestObserver.onNext(request);

            //Pause for 1 second
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Signal the end of requests
        // requestObserver.onCompleted();

        // Shutdown the channel after the call is complete
        // channel.shutdown();
    }
}
