package bgu.spl.mics.application;


import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Inventory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.FileReader;
import java.io.IOException;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try {
            jsonObject = jsonParser.parse(new FileReader(args[0])).getAsJsonObject();
        } catch (IOException e) {
        }
        JsonArray jsonInitialInventoryArray = jsonObject.get("initialInventory").getAsJsonArray();
        BookInventoryInfo[] initialLoad = new BookInventoryInfo[jsonInitialInventoryArray.size()];
        for (JsonElement element : jsonInitialInventoryArray) {
            int i = 0;
            String bookTitle = element.getAsJsonObject().get("bookTitle").getAsString();
            int amount = element.getAsJsonObject().get("amount").getAsInt();
            int price = element.getAsJsonObject().get("price").getAsInt();
            BookInventoryInfo tempBookInfo = new BookInventoryInfo(bookTitle, amount, price);
            initialLoad[i] = tempBookInfo;
            i++;
        }
        Inventory.getInstance().load(initialLoad);
    }
}




