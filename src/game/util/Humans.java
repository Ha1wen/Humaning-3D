package util;

import classes.Human;
import classes.Rarity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Humans {
    static final private Map<String, ArrayList<Human>> humansMap = computeJson("res/Humans.json");

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static private Map<String, ArrayList<Human>> computeJson(String path) {
        Map<String, ArrayList<Human>> map = new HashMap<>();

        String json;

        try {
            json = new String(Files.readAllBytes(Paths.get(path)));
        } catch(IOException e) {
            return null;
        }

        Map<String, Map<String, Map<String, Object>>> humansMapObject = new JSONParser(json).parse();

        for (String rarityName: humansMapObject.keySet()) {
            Map<String, Map<String, Object>> humansObject = humansMapObject.get(rarityName);
            ArrayList<Human> humansArray = new ArrayList();

            for (String name: humansObject.keySet()) {
                Map<String, Object> humanObject = humansObject.get(name);
                String description = (String) humanObject.get("desc");
                Rarity rarity = Rarities.getRarity(rarityName);

                Human human = new Human(name, description, rarity);
                
                humansArray.add(human);
            }

            map.put(rarityName, humansArray);
        }

        return map;
    }

    static public Map<String, ArrayList<Human>> getMap() {
        return humansMap;
    }

    static public ArrayList<Human> getArray(String rarity) {
        return humansMap.get(rarity);
    }

    static public Human getRandomHuman(double chance) {
        Rarity rarity = Rarities.getArray().get(0);
        for (Rarity checkRarity : Rarities.getArray()) {
            if ((double)1 /checkRarity.getChance() > chance) {
                rarity = checkRarity;
            }
        }
        
        ArrayList<Human> humansArray = getArray(rarity.getName()); 
        Human randomHuman = humansArray.get((int)(Math.random() * humansArray.size()));

        return randomHuman;
    }

    static public Human getRandomHuman(int luck) {
        double chance = Math.random()/luck;
        return getRandomHuman(chance);
    }

    static public Human getRandomHuman() {
        return getRandomHuman(0);
    }

    static public void printHumans () {
        for (Rarity rarity : Rarities.getArray()) {
            System.out.println("\n"+rarity.getName());
            for (Human human: Humans.getArray(rarity.getName())) {
                System.out.println("\t"+human.getName());
            }
        }
    }
}