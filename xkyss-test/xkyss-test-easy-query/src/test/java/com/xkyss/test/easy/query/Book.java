package com.xkyss.test.easy.query;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;

@Table("t_book_test")
public class Book {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String edition;
    private String price;
    private String storeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
