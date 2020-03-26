package com.santu.kafka.daoImp;

import com.opencsv.CSVReaderBuilder;
import com.santu.kafka.dao.Parser;
import com.santu.kafka.dao.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CSVReader implements Reader {

    private static final String SAMPLE_CSV_FILE_PATH = "C:\\Users\\santhosh\\IdeaProjects\\SampleData_.csv";

    public void read() {
        // Parser is an interface of Parser type
        //here instantiating JSONParser type
        Parser parser = new JSONParser();
        try (
                //reading data from csv file
                java.io.Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                // .withSkipLines is skip csv header info
                com.opencsv.CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                // parse method to extract info to JSON string
                parser.parse(nextRecord);
            }
            Map<Integer, List<String>> data = ((JSONParser) parser).getData();

            //Once read & Parsing is done, We start with processing of data.
            processData(data);
        } catch (IOException ex) {
            System.out.println("Error occured while reading the data");
            ex.printStackTrace();
        }
    }

    private void processData(Map<Integer, List<String>> data) {
        //check if map is empty or not
        if (!data.isEmpty()){
            //creating fixed number kafka publisher object equal to number of devide Id
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(data.size()); // we can change this sizze
            data.forEach((k, v) -> {
                // initializing KafkaPublisher with devideId and data to pubish
                KafkaPublisher kafkaPush = new KafkaPublisher("DeviceId "+ k, v);
                executor.execute(kafkaPush);
            });
            executor.shutdown();
        }

    }
}
