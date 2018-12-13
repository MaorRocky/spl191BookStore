package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.InventoryService;
import bgu.spl.mics.application.services.TimeService;
import com.google.gson.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        InputStream inputStream = BookStoreRunner.class.getClassLoader().getResourceAsStream("input.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();

        //-------------------------- initialInventory --------------------------

        JsonArray initialInventoryArray = rootObject.getAsJsonArray("initialInventory");
        BookInventoryInfo[] bookInventoryInfo = gson.fromJson(initialInventoryArray, BookInventoryInfo[].class);
        Inventory.getInstance().load(bookInventoryInfo);
        /*loaded bookInventoryInfo*/


        //-------------------------- initialResources --------------------------

        JsonArray initialResources = rootObject.getAsJsonArray("initialResources");
        JsonObject jsonObject = initialResources.get(0).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("vehicles");
        DeliveryVehicle[] deliveryVehicles = gson.fromJson(jsonArray, DeliveryVehicle[].class);
        ResourcesHolder.getInstance().load(deliveryVehicles);
        /*loaded deliveryVehicles*/

        //-------------------------- Services Object --------------------------
        JsonObject jsonServicesObj = rootObject.getAsJsonObject("services");

        //-------------------------- inventory service --------------------------
        JsonPrimitive jsonPrimitive = jsonServicesObj.getAsJsonPrimitive("inventoryService");

        //--------------------- selling ---------------------
        JsonPrimitive sellingNum = jsonServicesObj.getAsJsonPrimitive("selling");
        int sellingNumber = sellingNum.getAsInt();


        //--------------------- time ---------------------
        JsonObject jsonTimeObject = jsonServicesObj.getAsJsonObject("time");
        TimeService timeService = gson.fromJson(jsonTimeObject, TimeService.class);

        //--------------------- logistics ---------------------
        JsonPrimitive logistics = jsonServicesObj.getAsJsonPrimitive("logistics");
        int logisticsNumber = logistics.getAsInt();


        //--------------------- resource service ---------------------
        JsonPrimitive resourcesService = jsonServicesObj.getAsJsonPrimitive("resourcesService");
        int resourceServiceNum = resourcesService.getAsInt();

        //--------------------- customers ---------------------
        JsonArray customers = jsonServicesObj.getAsJsonArray("customers");
        Customer[] customersArr = gson.fromJson(customers, Customer[].class);


    }
}
