package com.ART.shower_love.ui.donatereceive;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public String condition;
    public String recycle;
    public String use;
    String category, Gender, PostalCode, Address;
    String Phone, Name, email;

    public uploadinfo() {
    }

    public uploadinfo(String name, String url, String condition, String recycle, String use, String chatogory, String Gender, String PostalCode, String Address, String Phone, String Name, String email) {
        this.imageName = name;
        this.imageURL = url;
        this.condition = condition;
        this.recycle = recycle;
        this.use = use;
        this.category = chatogory;
        this.Gender = Gender;
        this.PostalCode = PostalCode;
        this.Address = Address;
        this.Phone = Phone;
        this.Name = Name;
        this.email = email;
    }
    public uploadinfo(String name, String url) {
        this.imageName = name;
        this.imageURL = url;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRecycle() {
        return recycle;
    }

    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


