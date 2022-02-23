package com.systemcraftsman.demo.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator=",", skipField =true)
public class BookInfo implements Serializable{

    private static final long serialVersionUID = -1L;


    @DataField(pos=1)
    private String isbn;

    @DataField(pos=2)
    private String name;

    @DataField(pos=3)
    private String author;

    @DataField(pos=4, pattern="yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date year;

    @DataField(pos=5)
    private String publisher;

    @DataField(pos=6)
    private String language;

    @DataField(pos=7)
    private String storeLocation;

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getYear() {
        return year;
    }
    public void setYear(Date year) {
        this.year = year;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getStoreLocation() {
        return storeLocation;
    }
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

}
