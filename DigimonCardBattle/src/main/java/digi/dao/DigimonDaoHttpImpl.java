package digi.dao;

import digi.model.Digimon;
import com.google.gson.*;

import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DigimonDaoHttpImpl implements DigimonDao {
    private static final String BASE = "https://digi-api.com/api/v1/digimon/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public Digimon getByName(String name) {
        try {
            String url = BASE + name;
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                System.out.println("API returned: " + resp.statusCode());
                return null;
            }

            JsonObject obj = JsonParser.parseString(resp.body()).getAsJsonObject();
            Digimon d = new Digimon();
            d.setName(obj.has("name") ? obj.get("name").getAsString() : name);

            // levels
            List<String> levels = new ArrayList<>();
            if (obj.has("levels") && obj.get("levels").isJsonArray()) {
                JsonArray lvlArr = obj.getAsJsonArray("levels");
                for (JsonElement e : lvlArr) {
                    if (e.isJsonObject() && e.getAsJsonObject().has("level")) {
                        levels.add(e.getAsJsonObject().get("level").getAsString());
                    } else if (e.isJsonPrimitive()) {
                        levels.add(e.getAsString());
                    }
                }
            }
            d.setLevels(levels);

            // attributes
            List<String> attrs = new ArrayList<>();
            if (obj.has("attributes") && obj.get("attributes").isJsonArray()) {
                JsonArray aArr = obj.getAsJsonArray("attributes");
                for (JsonElement e : aArr) {
                    if (e.isJsonObject() && e.getAsJsonObject().has("attribute")) {
                        attrs.add(e.getAsJsonObject().get("attribute").getAsString());
                    } else if (e.isJsonPrimitive()) {
                        attrs.add(e.getAsString());
                    }
                }
            }
            d.setAttributes(attrs);

            // skills
            List<String> skills = new ArrayList<>();
            if (obj.has("skills") && obj.get("skills").isJsonArray()) {
                JsonArray sArr = obj.getAsJsonArray("skills");
                for (JsonElement e : sArr) {
                    if (e.isJsonObject() && e.getAsJsonObject().has("skill")) {
                        skills.add(e.getAsJsonObject().get("skill").getAsString());
                    }
                }
            }
            d.setSkills(skills);

            // images: try to get first image href
            if (obj.has("images") && obj.get("images").isJsonArray()) {
                JsonArray iArr = obj.getAsJsonArray("images");
                for (JsonElement e : iArr) {
                    if (e.isJsonObject() && e.getAsJsonObject().has("href")) {
                        d.setImage(e.getAsJsonObject().get("href").getAsString());
                        break;
                    }
                }
            }

            return d;
        } catch (Exception ex) {
            System.out.println("Error fetching Digimon: " + ex.getMessage());
            return null;
        }
    }
}