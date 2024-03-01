package com.unlp.pdtr.app;

import java.time.Instant;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;
import com.unlp.pdtr.app.DatabaseServiceGrpc.DatabaseServiceImplBase;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse;

public class DatabaseServiceImpl extends DatabaseServiceImplBase
{
    @Override
    public StreamObserver<DBRequest> storeInDatabase(StreamObserver<DBResponse> responseObserver) {
        return new StreamObserver<DBRequest>() {
            @Override
            public void onNext(DBRequest request) {
                //TODO store in DB
                System.out.println(request.toString());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("No se pudo guardar en la BD.");
                System.err.println(t.toString());
            }

            @Override
            public void onCompleted() {
                //TODO setear timestamp de ultima entrada cargada
                Instant currentTimestamp = Instant.now();
                Timestamp time = Timestamp.newBuilder()
                        .setSeconds(currentTimestamp.getEpochSecond())
                        .setNanos(currentTimestamp.getNano())
                        .build();

                DBResponse response = DBResponse.newBuilder().setTime(time).build();
                responseObserver.onNext(response);
                System.out.println("STORE IN DB OPERATION FINISHED");
            }
        };

    }
}
