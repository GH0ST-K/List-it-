package com.consensus.deg_project;

public class product {
    private String productBarcode;
    private String productname;
    private String productCategory;
    private String productlink;
    private String productprice;

    public product(){


    }
    public product(String productBarcode,String productname,String productCategory,String productlink,String productprice){
        this.productBarcode = productBarcode;
        this.productname = productname;
        this.productCategory = productCategory;
        this.productlink = productlink;
        this.productprice = productprice;
    }

    public String getProductBarcode(){return productBarcode;}

    public String getProductname(){return productname;}

    public String getProductCategory(){return productCategory;}

    public String getProductlink(){return productlink;}

    public String getProductprice(){return productprice;}

}
