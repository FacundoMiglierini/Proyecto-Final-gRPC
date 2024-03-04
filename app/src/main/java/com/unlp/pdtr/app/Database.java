package com.unlp.pdtr.app;

import java.time.Instant;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.domain.WritePrecision;

public class Database {

    private static InfluxDBClient influxDBClient;
    private static char[] token = "W5NF4e4k9i628yZXTSN0IIQlBkB7_JWHgfKD2O6vC4ajzGbONiaraVgVNpTtAwA5a_w5efZwit-V8mWwCYx7OQ==".toCharArray(); 
    private static String org = "unlp";
    private static String bucket = "traffic-bucket";

    public Database() {
        influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
    }


    public void closeDatabase() {
        influxDBClient.close();
    }

    public void writeData(String road, String region, String measure, int value, Instant time) {

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        switch(measure) {
            case "accidents":
                AccidentsData accidentsData = new AccidentsData();
                accidentsData.road = road;
                accidentsData.region = region;
                accidentsData.value = value;
                accidentsData.time = time;
                writeApi.writeMeasurement(WritePrecision.MS, accidentsData);
                break;
            case "cars":
                CarsData carsData = new CarsData();
                carsData.road = road;
                carsData.region = region;
                carsData.value = value;
                carsData.time = time;
                writeApi.writeMeasurement(WritePrecision.MS, carsData);
                break;
            case "parked":
                ParkedData parkedData = new ParkedData();
                parkedData.road = road;
                parkedData.region = region;
                parkedData.value = value;
                parkedData.time = time;
                writeApi.writeMeasurement(WritePrecision.MS, parkedData);
                break;
            case "speed":
                SpeedData speedData = new SpeedData();
                speedData.road = road;
                speedData.region = region;
                speedData.value = value;
                speedData.time = time;
                writeApi.writeMeasurement(WritePrecision.MS, speedData);
                break;
            case "trucks":
                TrucksData trucksData = new TrucksData();
                trucksData.road = road;
                trucksData.region = region;
                trucksData.value = value;
                trucksData.time = time;
                writeApi.writeMeasurement(WritePrecision.MS, trucksData);
                break;
            default:
        }
    }


    @Measurement(name = "accidents")
    private static class AccidentsData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "value")
        int value;

        @Column(name = "time", timestamp = true)
        Instant time;
    }

    @Measurement(name = "cars")
    private static class CarsData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "value")
        int value;

        @Column(name = "time", timestamp = true)
        Instant time;
    }

    @Measurement(name = "parked")
    private static class ParkedData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "value")
        int value;

        @Column(name = "time", timestamp = true)
        Instant time;
    }

    @Measurement(name = "speed")
    private static class SpeedData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "value")
        int value;

        @Column(name = "time", timestamp = true)
        Instant time;
    }

    @Measurement(name = "trucks")
    private static class TrucksData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "value")
        int value;

        @Column(name = "time", timestamp = true)
        Instant time;
    }
}
