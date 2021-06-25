package model;

public class Config {

    private int rows;
    private String columns;

    public Config() {

    }

    public Config(int rows, String columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Config{" +
                "rows=" + rows +
                ", columns='" + columns + '\'' +
                '}';
    }
}
