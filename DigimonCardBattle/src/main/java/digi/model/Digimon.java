package digi.model;

import java.util.List;

public class Digimon {
    private String name;
    private List<String> levels; // as returned by API
    private List<String> attributes; // attributes list from API
    private List<String> skills;
    private String image; // url

    public Digimon() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getLevels() { return levels; }
    public void setLevels(List<String> levels) { this.levels = levels; }

    public List<String> getAttributes() { return attributes; }
    public void setAttributes(List<String> attributes) { this.attributes = attributes; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        String lv = (levels != null && !levels.isEmpty()) ? String.join("/", levels) : "?";
        return name + " (" + lv + ")";
    }
}