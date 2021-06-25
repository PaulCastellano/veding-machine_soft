package model;

import java.util.ArrayList;

public class Data {
    private Config config;
    private ArrayList<Item> items;

    public Data() {
        config = new Config();
        items = new ArrayList<>();
    }

    public Data(Config config) {
        this.config = config;
        items = new ArrayList<>();
    }

    public Data(Config config, ArrayList<Item> items) {
        this.config = config;
        this.items = items;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (this.items == null)
            this.items = new ArrayList<>();
        this.items.add(item);
    }

    public Item getItemByIndex(Integer index) {
        if (this.items == null || index > this.items.size() - 1)
            return null;
        return this.items.get(index);
    }

    @Override
    public String toString() {
        return "Data{" +
                "config=" + config +
                ", items=" + items +
                "}";
    }
}
