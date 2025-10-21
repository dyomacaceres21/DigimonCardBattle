package digi.service;

import digi.dao.DigimonDao;
import digi.dao.DigimonDaoHttpImpl;
import digi.model.Digimon;

import java.util.List;

public class DigimonService {
    private final DigimonDao dao = new DigimonDaoHttpImpl();

    public Digimon getByName(String name) {
        return dao.getByName(name.replaceAll(" ", "%20"));
    }

    /**
     * Map levels array from API to numeric level (1..7) applying special rules.
     */
    public int mapLevelsToNumeric(List<String> levels, String digimonName) {
        if (digimonName != null && digimonName.equalsIgnoreCase("Magnamon")) {
            return 6; // manual exception: Magnamon -> Mega
        }

        if (levels == null || levels.isEmpty()) return 3; // default Rookie

        // Normalize to simple names
        // Prefer any level that is not Armor/Hybrid if both present
        if (levels.size() > 1) {
            for (String l : levels) {
                String ln = l.toLowerCase();
                if (!ln.contains("armor") && !ln.contains("hybrid")) {
                    return levelToNumber(ln);
                }
            }
            // If all are armor/hybrid prefer Hybrid
            for (String l : levels) {
                if (l.toLowerCase().contains("hybrid")) return levelToNumber("hybrid");
            }
            // otherwise armor
            return levelToNumber(levels.get(0).toLowerCase());
        }

        return levelToNumber(levels.get(0).toLowerCase());
    }

    private int levelToNumber(String levelStr) {
        if (levelStr == null) return 3;
        String s = levelStr.toLowerCase();
        if (s.contains("baby i") || s.contains("fresh") || s.contains("baby i") || s.contains("fresh")) return 1;
        if (s.contains("baby ii") || s.contains("in training") || s.contains("baby ii") || s.contains("in-training")) return 2;
        if (s.contains("rookie")) return 3;
        if (s.contains("champion") || s.contains("adult")) return 4;
        if (s.contains("ultimate") || s.contains("perfect")) return 5;
        if (s.contains("mega")) return 6;
        // Ultra/fusion/super/other -> 7
        if (s.contains("ultra") || s.contains("fusion") || s.contains("armor") || s.contains("hybrid") || s.contains("super")) return 7;
        // fallback
        return 3;
    }

    /**
     * Obtain a representative attribute for the digimon (Vaccine/Virus/Data/Neutral)
     */
    public String pickAttribute(List<String> attributes) {
        if (attributes == null || attributes.isEmpty()) return "Neutral";
        // Prefer Vaccine/Virus/Data if present
        for (String a : attributes) {
            String an = a.toLowerCase();
            if (an.contains("vaccine")) return "Vaccine";
            if (an.contains("virus")) return "Virus";
            if (an.contains("data")) return "Data";
        }
        return "Neutral";
    }
}