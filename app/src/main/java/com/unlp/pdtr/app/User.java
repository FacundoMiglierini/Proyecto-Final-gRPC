package com.unlp.pdtr.app;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import com.unlp.pdtr.app.UserServiceGrpc.UserServiceStub;
import com.unlp.pdtr.app.UserServiceOuterClass.UserRequest;

public class User {

    private static class Bot implements Runnable {

        private static final Map<String, String[]> roads = new HashMap<>();
        private static final Map<String, int[]> actions = new HashMap<>();
        private ManagedChannel channel;
        private StreamObserver<UserRequest> requestObserver;

        public Bot() {
            channel = ManagedChannelBuilder.forAddress("localhost", 8080)
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
            requestObserver = asyncStub.storeTrafficData(responseObserver);
        }

        public void run() {

            // Send multiple request messages asynchronously
            while (true) {

                int roadIndex = new Random().nextInt(roads.size());
                Object randomRoad = roads.entrySet().toArray()[roadIndex];
                Map.Entry<String, String[]> roadEntry = (Map.Entry<String, String[]>) randomRoad;
                String road = roadEntry.getKey();
                String region = roadEntry.getValue()[new Random().nextInt(roadEntry.getValue().length)];

                int actionIndex = new Random().nextInt(actions.size());
                Object randomAction = actions.entrySet().toArray()[actionIndex];
                Map.Entry<String, int[]> actionEntry = (Map.Entry<String, int[]>) randomAction;
                String measure = actionEntry.getKey();
                int min = actionEntry.getValue()[0];
                int max = actionEntry.getValue()[1];
                int value = new Random().nextInt(max - min + 1) + min;

                UserRequest request;

                Instant currentTimestamp = Instant.now();
                // format to UTC
                Instant UTCTimestamp = currentTimestamp.plus(3, ChronoUnit.HOURS);
                Timestamp currentTime = Timestamp.newBuilder()
                        .setSeconds(UTCTimestamp.getEpochSecond())
                        .setNanos(UTCTimestamp.getNano())
                        .build();

                request = UserRequest.newBuilder()
                    .setRoad(road)
                    .setRegion(region)
                    .setTime(currentTime)
                    .setMeasure(measure)
                    .setValue(value)
                    .build();

                requestObserver.onNext(request);

                //Pause for 1 second
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void registerShutdownHook() {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    // Signal the end of requests
                    requestObserver.onCompleted();
                    // Shutdown the channel after the call is complete
                    channel.shutdown();

                    System.out.println("Bot stopped.");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                }));
            }

    }

    static {
        String[] rp1 = {"Partido de La Plata: Tolosa", "Ringuelet", "City Bell"};
        String[] rp2 = {"Avellaneda", "Chascomús", "Lezama"};
        String[] rp4 = {"Quilmes", "Florencio Varela"};

        Bot.roads.put("RP1", rp1);
        Bot.roads.put("RP2", rp2);
        Bot.roads.put("RP4", rp4);

        int[] speed = {60, 130};
        int[] cars = {10, 200};
        int[] accidents = {1, 10};
        int[] parked = {1,30};
        int[] trucks = {10, 50};

        Bot.actions.put("speed", speed);
        Bot.actions.put("cars", cars);
        Bot.actions.put("accidents", accidents);
        Bot.actions.put("parked", parked);
        Bot.actions.put("trucks", trucks);
    }

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

        int numberOfBots = 0;

        if (args.length == 1) {
            try {
                numberOfBots = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println(e.getMessage().toString());
                System.exit(0);
            }
        } else {
            System.err.println("Debe ingresar un parámetro de tipo entero que indique el número de bots.");
            System.exit(0);
        }

        List<Bot> bots = new ArrayList<Bot>();
        for (int i = 0; i < numberOfBots; i++) {
            Bot bot = new Bot();
            bot.registerShutdownHook();
            bots.add(bot);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numberOfBots);
        for(Bot bot: bots){
            executor.submit(bot);
        }
    }
}
