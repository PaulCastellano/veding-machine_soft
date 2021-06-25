package data;

import utils.GsonHelper;
import model.Config;
import model.Data;
import model.Item;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Repository {

    private Data data;

    public Repository() {

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Config getConfig() {
        if (data == null)
            return null;
        return data.getConfig();
    }

    public void setConfig(Config config) {
        if (data != null)
            this.data.setConfig(config);
    }

    public ArrayList<Item> getItems() {
        if (data == null)
            return null;
        return data.getItems();
    }

    public void setItems(ArrayList<Item> items) {
        if (data != null)
            data.setItems(items);
    }

    public void addItem(Item item) {
        if (this.data == null) {
            ArrayList<Item> arrayList = new ArrayList<>();
            arrayList.add(item);
            this.data = new Data(new Config(1, "1"), arrayList);
        }
        this.data.addItem(item);
    }

    public Item getItemByIndex(Integer index) {
        if (data == null)
            return null;
        return this.data.getItemByIndex(index);
    }

    public void saveData() {
        if (data != null)
            GsonHelper.saveDataInJson(this.data);
    }

    public void setNewDataFromFile(String file) throws FileNotFoundException {
        try {
            Data data = (Data) GsonHelper.getDataByJson(file, Data.class);
            if (this.data == null) {
                this.data = data;
            } else {
                if (data.getConfig() != null)
                    this.data.setConfig(data.getConfig());
                for (Item item:
                     data.getItems()) {
                    if (this.getItems().contains(item)) {
                        this.getItems().get(this.getItems().indexOf(item)).addAmount(item.getAmount());
                        this.getItems().get(this.getItems().indexOf(item)).setPrice(item.getPrice());
                    } else {
                        this.getItems().add(item);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    public void getSavedData() throws FileNotFoundException {
        try {
            data = (Data) GsonHelper.getDataByJson(Utils.CONFIG_FILE, Data.class);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
}
