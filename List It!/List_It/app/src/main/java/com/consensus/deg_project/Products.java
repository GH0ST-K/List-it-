package com.consensus.deg_project;

import java.util.Map;

public class Products {
    String userId;
    String productBarcode;
    String productCategory;
    String productlink;
    String productname;
    String productprice;
    public Map timeStamp;

    public Products(){

    }

    public Products(String productBarcode, String productCategory, String productlink, String productname, String productprice, Map timeStamp) {
        this.productBarcode = productBarcode;
        this.productCategory = productCategory;
        this.productlink = productlink;
        this.productname = productname;
        this.productprice = productprice;
        this.timeStamp = timeStamp;
    }


    public String getBarcode() {
        return productBarcode;
    }

    public String getproductCategory() {
        return productCategory;
    }

    public String getproductlink() {
        return productlink;
    }

    public String getproductname() {
        return productname;
    }

    public Map<String,String> getTimeStamp() { return timeStamp;}
}
