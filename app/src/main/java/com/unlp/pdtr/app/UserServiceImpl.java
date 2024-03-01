package com.unlp.pdtr.app;

import io.grpc.stub.StreamObserver;
import com.google.protobuf.Empty;
import com.unlp.pdtr.app.UserServiceGrpc.UserServiceImplBase;
import com.unlp.pdtr.app.UserServiceOuterClass.UserRequest;


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
    @Override
    public StreamObserver<UserRequest> storeTrafficData(final StreamObserver<Empty> responseObserver) {

        return new StreamObserver<UserRequest>() {
            @Override
            public void onNext(UserRequest request) {
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
