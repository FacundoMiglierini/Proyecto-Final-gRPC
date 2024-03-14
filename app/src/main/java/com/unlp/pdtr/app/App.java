package com.unlp.pdtr.app;

import io.grpc.Server;
import io.grpc.ServerBuilder;

//TODO
// conflicts with many bots
// slow writing process on db

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
