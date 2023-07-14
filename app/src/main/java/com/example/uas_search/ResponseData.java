package com.example.uas_search;

import com.example.uas_search.ProductItem;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable {
    private List<ProductItem> products;
    private int total;
    private int skip;
    private int limit;

    // Getter methods for the fields

    public List<ProductItem> getProducts() {
        return products;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}

