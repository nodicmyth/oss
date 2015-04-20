package com.data.dw.oss.dimension.dto;

import java.io.Serializable;

/**
 * Created by huangshiqian on 15/4/9.
 */
public class Column implements Serializable {
    private String name;
    private Object value;

    public Column(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;

        Column column = (Column) o;

        if (!name.equals(column.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean isBlank() {
        return name == null || value == null;
     }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
