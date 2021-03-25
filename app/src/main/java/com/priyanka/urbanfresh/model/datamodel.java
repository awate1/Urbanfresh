package com.priyanka.urbanfresh.model;

public class datamodel {
    String image;
 //  String CategoryImage;
    String name;
public  datamodel(){

}

    public datamodel(String image, String name) {
        this.image = image;
        this.name = name;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
