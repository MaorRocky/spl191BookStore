package bgu.spl.mics.application;


import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        JsonParser parser = new JsonParser();
        try {
            jsonObject = parser.parse(new FileReader(args[0])).getAsJsonObject();
        } catch (IOException e) {
        }
        JsonArray jsonArray = jsonObject.get("initialInventory").getAsJsonArray();
        BookInventoryInfo[] intitalLoad = new BookInventoryInfo[];
        for (JsonElement element : jsonArray) {
            int i = 0;
            String bookTitle = element.getAsJsonObject().get("bookTitle").getAsString();
            int amount = element.getAsJsonObject().get("amount").getAsInt();
            int price = element.getAsJsonObject().get("price").getAsInt();
            BookInventoryInfo tempBookinfo = new BookInventoryInfo(bookTitle, amount, price);
            intitalLoad[i] = tempBookinfo;
            i++;

        }
    }
}
