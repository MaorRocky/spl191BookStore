package bgu.spl.mics.application;

import bgu.spl.mics.application.Events.BookOrderEvent;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.util.LinkedList;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        String jsonInput = new TxtFile().txt;
        Gson gson = new Gson();
        Data data = new Gson().fromJson(jsonInput, Data.class);

        //Loading the inventory
        Inventory.getInstance().load(Data.initialInventory);

        //Loading the resources holder
        ResourcesHolder.getInstance().load(Data.initialResources[0].vehicles);

        //Creating the time service
        TimeService timer = Data.services.time;

        //Creating selling services
        SellingService[] selling = new SellingService[Data.services.selling];
        for (int i = 0; i < selling.length; i++) {
            selling[i] = new SellingService("SellingService " + i);
        }

        //Creating inventory services
        InventoryService[] inventories = new InventoryService[Data.services.inventoryService];
        for (int i = 0; i < inventories.length; i++) {
            inventories[i] = new InventoryService("InventoryService " + i);
        }

        //Creating logistics services
        LogisticsService[] logisticsServices = new LogisticsService[Data.services.logistics];
        for (int i = 0; i < logisticsServices.length; i++) {
            logisticsServices[i] = new LogisticsService("LogisticsService " + i);
        }

        //Creating resource services
        ResourceService[] resources = new ResourceService[Data.services.resourcesService];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new ResourceService("ResourceService " + i);
        }

        //Creating API services
        APIService[] apiServices = new APIService[Data.services.customers.length];
        for (int i = 0; i < apiServices.length; i++) {
            Customer tmpCustomer = new Customer(Data.services.customers[i].id,
                    Data.services.customers[i].name,
                    Data.services.customers[i].address,
                    Data.services.customers[i].distance,
                    Data.services.customers[i].creditCard);
            String tmpName = "APIService " + i;
            LinkedList<BookOrderEvent> list = new LinkedList<>();
            for (int j = 0; j < Data.services.customers[i].orderSchedule.length; j++) {
                list.add(new BookOrderEvent(Data.services.customers[i].orderSchedule[j]
                        .bookTitle, tmpCustomer, Data.services.customers[i].orderSchedule[j].tick));
            }
            apiServices[i] = new APIService(tmpName, tmpCustomer, list);
        }
    }

    public static class Data {
        public static BookInventoryInfo[] initialInventory;
        public static MyResources[] initialResources;
        public static MyServices services;

        public static class MyResources {
            public static DeliveryVehicle[] vehicles;
        }

        public static class MyServices {
            public TimeService time;
            public int selling;
            public int inventoryService;
            public int logistics;
            public int resourcesService;
            public JsonCustomer[] customers;

            public class JsonCustomer {
                public int id;
                public String name;
                public String address;
                public int distance;
                public CreditCard creditCard;
                public Order[] orderSchedule;

                public class Order {
                    public String bookTitle;
                    public int tick;
                }
            }
        }

    }
}
