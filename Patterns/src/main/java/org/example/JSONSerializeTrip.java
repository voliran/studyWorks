package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONSerializeTrip {

    public static void main(String[] args)  {

        ObjectMapper mapper = new ObjectMapper();

        Trip trip = new Trip.Builder()
                .destination("Moscow")
                .duration(10)
                .build();

        try  {
            mapper.writeValue(new File("trip.json"), trip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Trip newTrip = mapper.readValue(new File("trip.json"), Trip.class);
            System.out.println("Куда: " + newTrip.getDestination() + " путь займет: " + newTrip.getDuration());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
