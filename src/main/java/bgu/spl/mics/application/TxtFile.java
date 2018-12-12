package bgu.spl.mics.application;

public class TxtFile {
    public String txt = "{\n" +
            "  \"initialInventory\": [\n" +
            "  {\"bookTitle\": \"Harry Poter\", \"amount\": 10, \"price\": 90},\n" +
            "  {\"bookTitle\": \"The Hunger Games\", \"amount\": 90, \"price\": 102}\n" +
            "  ],\n" +
            "  \"initialResources\":[\n" +
            "   {\"vehicles\":[\n" +
            "     {\"license\":123483, \"speed\": 2},\n" +
            "     {\"license\":999994, \"speed\": 4}\n" +
            "     ]\n" +
            "     }\n" +
            "    ],\n" +
            "  \"services\":{\n" +
            "    \"time\": {\n" +
            "      \"speed\": 1000,\n" +
            "      \"duration\": 24\n" +
            "    },\n" +
            "    \"selling\": 6,\n" +
            "    \"inventoryService\": 3,\n" +
            "    \"logistics\": 4,\n" +
            "    \"resourcesService\": 2,\n" +
            "    \"customers\": [\n" +
            "     {\n" +
            "        \"id\": 123456789,\n" +
            "        \"name\": \"Bruria\",\n" +
            "        \"address\": \"NewYork 123\",\n" +
            "        \"distance\":33,\n" +
            "        \"CreditCard\":{\"number\":67890,\"amount\":88},\n" +
            "        \"orderSchedule\": [\n" +
            "            {\"bookTitle\": \"Harry Poter\", \"tick\": 3},\n" +
            "            {\"bookTitle\": \"The Hunger Games\", \"tick\": 3}\n" +
            "          ]\n" +
            "      },\n" +
            "    {\n" +
            "        \"id\": 234567891,\n" +
            "        \"name\": \"Shraga\",\n" +
            "        \"address\": \"BeerSheva 3333\",\n" +
            "        \"distance\":12,\n" +
            "         \"CreditCard\":{\"number\":453536,\"amount\":220},\n" +
            "    \"orderSchedule\": [\n" +
            "        {\"bookTitle\": \"The Hunger Games\", \"tick\": 12}\n" +
            "    ]\n" +
            "    }\n" +
            "  ]\n" +
            "  }\n" +
            "  \n" +
            "  \n" +
            "    \n" +
            "}";
}
