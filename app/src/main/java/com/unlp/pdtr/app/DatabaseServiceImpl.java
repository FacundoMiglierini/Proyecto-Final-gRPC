package com.unlp.pdtr.app;

import java.time.Instant;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;
import com.unlp.pdtr.app.DatabaseServiceGrpc.DatabaseServiceImplBase;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest;
import com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse;

public class DatabaseServiceImpl extends DatabaseServiceImplBase
{
    private static Database database;

    public DatabaseServiceImpl() {
        database = new Database();
    }

    @Override
    public StreamObserver<DBRequest> storeInDatabase(StreamObserver<DBResponse> responseObserver) {
        return new StreamObserver<DBRequest>() {
            @Override
            public void onNext(DBRequest request) {
                Instant time = Instant.ofEpochSecond(request.getTime().getSeconds(), request.getTime().getNanos());
                database.writeData(request.getRoad(), request.getRegion(), request.getMeasure(), request.getValue(), time);
                System.out.println("Llega a DB");
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("No se pudo guardar en la BD.");
                System.err.println(t.toString());
            }

            @Override
            public void onCompleted() {
                //TODO remove it
                Instant currentTimestamp = Instant.now();
                Timestamp time = Timestamp.newBuilder()
                        .setSeconds(currentTimestamp.getEpochSecond())
                        .setNanos(currentTimestamp.getNano())
                        .build();

                DBResponse response = DBResponse.newBuilder().setTime(time).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                System.out.println("STORE IN DB OPERATION FINISHED");
                database.closeDatabase();
            }
        };

    }
}
