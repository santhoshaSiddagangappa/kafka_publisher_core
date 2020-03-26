package com.santu.kafka.daoImp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.santu.kafka.dao.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parser {

    private static Map<Integer, List<String>> deviceData = new HashMap<>();

    public void parse(String[] data) {

        int deviceId = new Integer(data[0]);
        List<String> deviceList = new ArrayList<>();
        ;
        if (deviceData.containsKey(deviceId)) {
            deviceList = deviceData.get(deviceId);
        }
        if (deviceList == null || deviceList.size() == 0) {

        }
        String out = "";
        try {
            JsonObject object = new JsonObject();
            object.addProperty("deviceId", deviceId);
            if (data[1] != null && !data[1].isEmpty()) {
                object.addProperty("dataTime", java.sql.Timestamp.valueOf(data[1]).toString());
            }


            JsonArray jsonArray = new JsonArray();

            if (data[2] != null && !data[2].isEmpty()) {
                JsonObject current_a = new JsonObject();
                current_a.addProperty("dataName", "CurrentA");
                current_a.addProperty("dataValue", new Float(data[2].trim()));
                jsonArray.add(current_a);
            }

            if (data[3] != null && !data[3].isEmpty()) {
                JsonObject current_b = new JsonObject();
                current_b.addProperty("dataName", "CurrentB");
                current_b.addProperty("dataValue", new Float(data[3]));
                jsonArray.add(current_b);
            }

            if (data[4] != null && !data[4].isEmpty()) {
                JsonObject current_c = new JsonObject();
                current_c.addProperty("dataName", "CurrentC");
                current_c.addProperty("dataValue", new Float(data[4]));
                jsonArray.add(current_c);

            }

            if (data[5] != null && !data[5].isEmpty()) {
                JsonObject acive_power = new JsonObject();
                acive_power.addProperty("dataName", "ActivePower");
                acive_power.addProperty("dataValue", new Float(data[5]));
                jsonArray.add(acive_power);
            }

            if (data[6] != null && !data[6].isEmpty()) {
                JsonObject appearent_power = new JsonObject();
                appearent_power.addProperty("dataName", "AppearentPower");
                appearent_power.addProperty("dataValue", new Float(data[6]));
                jsonArray.add(appearent_power);
            }

            if (data[7] != null && !data[7].isEmpty()) {
                JsonObject reactive_power = new JsonObject();
                reactive_power.addProperty("dataName", "ReactivePower");
                reactive_power.addProperty("dataValue", new Float(data[7]));
                jsonArray.add(reactive_power);
            }


            if (data[8] != null && !data[8].isEmpty()) {
                JsonObject voltage_a = new JsonObject();
                voltage_a.addProperty("dataName", "VoltageA");
                voltage_a.addProperty("dataValue", new Float(data[8]));
                jsonArray.add(voltage_a);
            }


            if (data[9] != null && !data[9].isEmpty()) {
                JsonObject voltage_b = new JsonObject();
                voltage_b.addProperty("dataName", "VoltageB");
                voltage_b.addProperty("dataValue", new Float(data[9]));
                jsonArray.add(voltage_b);
            }


            if (data[10] != null && !data[10].isEmpty()) {
                JsonObject voltage_c = new JsonObject();
                voltage_c.addProperty("dataName", "VoltageC");
                voltage_c.addProperty("dataValue", new Float(data[10]));
                jsonArray.add(voltage_c);
            }

            object.add("dataList", jsonArray);
            deviceList.add(object.toString());
            deviceData.put(deviceId, deviceList);
        } catch (Exception e) {
            System.out.println("Error occured while parsing to JSON message");
        }
    }

    public Map<Integer, List<String>> getData() {
        return deviceData;
    }

}
