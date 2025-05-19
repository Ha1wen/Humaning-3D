package util;

import classes.Rarity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rarities {
    private static final Map<String, Rarity> raritiesMap = computeJson("res/Rarities.json");
    private static final ArrayList<Rarity> raritiesArray = computeArray(raritiesMap);

    static private Map<String, Rarity> computeJson(String path) {
        Map<String, Rarity> map = new HashMap<>();

        String json;

        try {
            json = new String(Files.readAllBytes(Paths.get(path)));
        } catch(IOException e) {
            return null;
        }

        Map<String, Map<String, Object>> raritiesMapObject = new JSONParser(json).parse();

        for (String name: raritiesMapObject.keySet()) {
            Map<String, Object> rarityObject = raritiesMapObject.get(name);

            String color = (String)rarityObject.get("color");
            int chance = (int)(Number)rarityObject.get("chance");
            int price = (int)(Number)rarityObject.get("price");

            Rarity rarity = new Rarity(name, color, chance, price);

            map.put(name, rarity);
        }

        return map;
    }

    static private ArrayList<Rarity> computeArray(Map<String, Rarity> rarities) {
        ArrayList<Rarity> array = new ArrayList<>();
        for (String rarityName : rarities.keySet()) {
            Rarity rarity = rarities.get(rarityName);
            array.add(rarity);
        }

        int n = array.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array.get(j).getChance() > array.get(j + 1).getChance()) {
                    Rarity temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }

        return array;
    }

    static public ArrayList<Rarity> getArray() {
        return raritiesArray;
    }

    static public Rarity getRarity(String name) {
        return raritiesMap.get(name);
    }

    static public int getPrice(String name) {
        return getRarity(name).getPrice();
    }

    static public int getChance(String name) {
        return getRarity(name).getChance();
    }
}
