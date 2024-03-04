package com.unlp.pdtr.app;

import java.time.Instant;
import java.util.List;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.QueryApi;

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

    public void writeTrafficData(String road, String region, String comment, Instant time) {

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        TrafficData data = new TrafficData();
        data.road = road;
        data.region = region;
        data.comment = comment;
        data.time = time;

        writeApi.writeMeasurement(WritePrecision.MS, data);
    }

    //Unused
    public List<TrafficData> getTrafficDataFromRoad(String road) {

        String flux = String.format( "from(bucket:\"%s\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"traffic\") |> filter(fn: (r) => r[\"road\"] == \"%s\") |> sort() |> yield(name: \"sort\")", bucket, road);

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<TrafficData> records = queryApi.query(flux, TrafficData.class);

        return records;
    }

    @Measurement(name = "traffic")
    private static class TrafficData {
        @Column(name = "road", tag = true)
        String road;

        @Column(name = "region")
        String region;

        @Column(name = "comment")
        String comment;

        @Column(name = "time", timestamp = true)
        Instant time;
    }
}
