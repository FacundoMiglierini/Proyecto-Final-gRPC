package com.unlp.pdtr.app;

import io.grpc.Server;
import io.grpc.ServerBuilder;

//TODO
//env for API token
//Choose a new distributed, timeseries DB

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Server server = ServerBuilder.forPort(8080)
                .addService(new UserServiceImpl())
                .addService(new DatabaseServiceImpl())
                .addService(new WebSocketServiceImpl())
                .build();
        
        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}
