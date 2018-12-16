package bgu.spl.mics.application;

import bgu.spl.mics.application.Events.BookOrderEvent;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = new JsonObject();

        try {
            obj = parser.parse(new FileReader(args[0])).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String customers_HashMap = args[1];
        String books_HashMap = args[2];
        String list_of_order_receipts = args[3];
        String MoneyRegisterPrint = args[4];


        //-------------------------- initialInventory --------------------------

        JsonArray initialInventoryArray = obj.getAsJsonArray("initialInventory");
        BookInventoryInfo[] bookInventoryInfo = gson.fromJson(initialInventoryArray, BookInventoryInfo[].class);
        Inventory.getInstance().load(bookInventoryInfo);
        /*loaded bookInventoryInfo*/

        //-------------------------- initialResources --------------------------

        JsonArray initialResources = obj.getAsJsonArray("initialResources");
        JsonObject jsonObject = initialResources.get(0).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("vehicles");
        DeliveryVehicle[] deliveryVehicles = gson.fromJson(jsonArray, DeliveryVehicle[].class);
        ResourcesHolder.getInstance().load(deliveryVehicles);
        /*loaded deliveryVehicles*/

        //-------------------------- Services Object --------------------------
        JsonObject jsonServicesObj = obj.getAsJsonObject("services");

        //-------------------------- inventory service --------------------------
        JsonPrimitive jsonPrimitive = jsonServicesObj.getAsJsonPrimitive("inventoryService");
        int inventoryNumber = jsonPrimitive.getAsInt();
        Thread[] inventoryServices = new Thread[inventoryNumber];
        for (int i = 0; i < inventoryServices.length; i++) {
            String name = "inventoryService " + i;
            InventoryService toAdd = new InventoryService(name);
            inventoryServices[i] = new Thread(toAdd);
        }

        //--------------------- selling ---------------------
        JsonPrimitive sellingNum = jsonServicesObj.getAsJsonPrimitive("selling");
        int sellingNumber = sellingNum.getAsInt();
        Thread[] sellingServices = new Thread[sellingNumber];
        for (int i = 0; i < sellingNumber; i++) {
            String name = "sellingService " + i;
            SellingService toAdd = new SellingService(name);
            sellingServices[i] = new Thread(toAdd);
        }

        //--------------------- time ---------------------
        JsonObject jsonTimeObject = jsonServicesObj.getAsJsonObject("time");
        time time = gson.fromJson(jsonTimeObject, time.class);
        TimeService timeService = new TimeService(time);
        Thread timer = new Thread(timeService);

        //--------------------- logistics ---------------------
        JsonPrimitive logistics = jsonServicesObj.getAsJsonPrimitive("logistics");
        int logisticsNumber = logistics.getAsInt();
        Thread[] logisticsServices = new Thread[logisticsNumber];
        for (int i = 0; i < logisticsServices.length; i++) {
            String name = "logisticService " + i;
            LogisticsService toAdd = new LogisticsService(name);
            logisticsServices[i] = new Thread(toAdd);
        }

        //--------------------- resource service ---------------------
        JsonPrimitive resourcesService = jsonServicesObj.getAsJsonPrimitive("resourcesService");
        int resourceServiceNum = resourcesService.getAsInt();
        Thread[] resourcesServices = new Thread[resourceServiceNum];
        for (int j = 0; j < resourceServiceNum; j++) {
            String name = "resourcesService " + j;
            ResourceService toAdd = new ResourceService(name);
            resourcesServices[j] = new Thread(toAdd);
        }

        //--------------------- customers ---------------------
        JsonArray customers = jsonServicesObj.getAsJsonArray("customers");
        Customer[] customersArr = gson.fromJson(customers, Customer[].class);
        Thread[] APIServices = new Thread[customersArr.length];
        for (int k = 0; k < APIServices.length; k++) {
            String name = customersArr[k].getName();
            LinkedList<BookOrderEvent> list = new LinkedList<>();
            for (orderSchedule order : customersArr[k].getOrderSchedule()) {
                BookOrderEvent toAdd = new BookOrderEvent(order.getBookTitle(), customersArr[k], order.getTick());
                list.add(toAdd);
            }
            APIService toAdd = new APIService(name, customersArr[k], list);
            APIServices[k] = new Thread(toAdd);
        }

        int numOfServices = APIServices.length + resourceServiceNum + logisticsNumber + sellingNumber + inventoryNumber;

        //-------------Running APIServices--------------
        for (int i = 0; i < APIServices.length; i++) {
            APIServices[i].start();
        }

        //------------Running resourcesServices--------
        for (int i = 0; i < resourcesServices.length; i++) {
            resourcesServices[i].start();
        }

        //------------Running logisticsServices------
        for (int i = 0; i < logisticsServices.length; i++) {
            logisticsServices[i].start();
        }

        //-------------Running sellingServices-----------
        for (int i = 0; i < sellingServices.length; i++) {
            sellingServices[i].start();
        }

        //------------Running inventorServices-------
        for (int i = 0; i < inventoryServices.length; i++) {
            inventoryServices[i].start();
        }

        //-----------Running timeService----------
        while (RunningCounter.getInstance().getNumberRunningThreads() < numOfServices) ;
        timer.start();
        while (RunningCounter.getInstance().getNumberRunningThreads() > 0) ;
        /*printBookStore();*/



        /*---------------------printing customers_HashMap to output file-----------------------*/
        printCustomers(customers_HashMap, customersArr);
        /*---------------------printing books_HashMap to output file-----------------------*/
        Inventory.getInstance().printInventoryToFile(books_HashMap);
        /*---------------------printing list_of_order_receipts to output file----------------------------------*/
        MoneyRegister.getInstance().printOrderReceipts(list_of_order_receipts);
        /*---------------------printing money register to output file----------------------------------*/
        printMoneyRegister(MoneyRegisterPrint, MoneyRegister.getInstance());

        System.exit(0);
    }

    /*public static void printBookStore() {
        Inventory.getInstance().testPrintInventory();
        ResourcesHolder.getInstance().testforResources();
        MoneyRegister.getInstance().testPrintReceipts();
    }*/


    private static HashMap<Integer, Customer> hashMapCustomers(Customer[] customersArray) {
        HashMap<Integer, Customer> CustomersHashMap = new HashMap<>();
        for (Customer customer : customersArray) {
            CustomersHashMap.put(customer.getId(), customer);
        }
        return CustomersHashMap;
    }

    private static void printCustomers(String filename, Customer[] customersArray) {
        HashMap<Integer, Customer> CustomersHashMap = hashMapCustomers(customersArray);
        PrintSerializeToFile printer = new PrintSerializeToFile(filename);
        printer.printSerializedHashMap(CustomersHashMap);
    }


    private static void printMoneyRegister(String filename, MoneyRegister moneyRegister) {
        PrintSerializeToFile printer = new PrintSerializeToFile(filename);
        printer.printSerializedObject(moneyRegister);
    }
}


