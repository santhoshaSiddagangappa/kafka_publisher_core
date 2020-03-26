package com.santu.kafka;

import com.santu.kafka.dao.Reader;
import com.santu.kafka.daoImp.CSVReader;

public class KafkaPublisherApplication {

    public static void main(String[] args) {
        System.out.println("Test");

        // Reader is an interface class with read method
        //here instantiating CSVReader
        Reader reader = new CSVReader();

        //start reading CSV filedata
        reader.read();
    }

}
