package digi.model;

import java.util.List;

public class Digimon {
    private String name;
    private List<String> levels;      // Lista de niveles disponibles
    private List<String> attributes;  // Lista de atributos disponibles

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getLevels() { return levels; }
    public void setLevels(List<String> levels) { this.levels = levels; }

    public List<String> getAttributes() { return attributes; }
    public void setAttributes(List<String> attributes) { this.attributes = attributes; }
}
