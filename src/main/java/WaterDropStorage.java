import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WaterDropStorage {

    private static final File FILE = new File("waterdrops.json");
    private static final ObjectMapper mapper = new ObjectMapper();

    // userId -> list of earned words
    private Map<String, List<String>> data = new HashMap<>();

    public WaterDropStorage() {
        load();
    }

    public void addWord(String userId, String word) {
        data.computeIfAbsent(userId, k -> new ArrayList<>()).add(word);
        save();
    }

    public List<String> getWords(String userId) {
        return data.getOrDefault(userId, Collections.emptyList());
    }

    private void load() {
        try {
            if (FILE.exists()) {
                data = mapper.readValue(FILE, new TypeReference<Map<String, List<String>>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FILE, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
