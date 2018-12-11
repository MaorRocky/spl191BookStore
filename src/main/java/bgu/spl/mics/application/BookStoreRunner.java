package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.services.TimeService;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;


/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {

        if (args.length == 6) {
            String inJsonPath = args[1];
            String outJsonCustomers = args[2];
            String outJsonBooks = args[3];
            String outJsonReceipts = args[4];
            String outJsonMoneyRegister = args[5];
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            try {
                JsonReader reader = new JsonReader(new FileReader(inJsonPath));
                JsonObject element = (JsonObject) parser.parse(reader);
                JsonElement responseWrapper = element.get("initialInventory");
                BookInventoryInfo[] data = gson.fromJson(responseWrapper, BookInventoryInfo[].class);
                JsonArray jojo = (JsonArray) element.get("initialResources");
                JsonObject mojo = (JsonObject) jojo.get(0);
                JsonElement moooo = mojo.get("vehicles");
                DeliveryVehicle[] data2 = gson.fromJson(moooo, DeliveryVehicle[].class);
                JsonObject services = (JsonObject) element.get("services");
                JsonElement timeSpeed = services.get("time");
                TimeService time = gson.fromJson(timeSpeed, TimeService.class);
                JsonElement sellingThreadAmount = services.get("selling");
                JsonElement inventoryThreadAmount = services.get("inventoryService");
                JsonElement logisticsThreadAmount = services.get("logistics");
                JsonElement resourceThreadAmount = services.get("resourcesService");
                JsonElement customers = services.get("customers");
                Customer[] data3 = gson.fromJson(customers, Customer[].class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("boob");
        } else {
            System.out.println("Not Enough Parameters, Terminating.");
        }
    }
}
