package com.gregperlinli.bean;

import java.math.BigDecimal;

/**
 * @author gregperlinli
 */
public class Book {
    private int id;
    private String isbm;
    private String name;
    private String category;
    private int remainNum;
    // private int price;
    private String author;
    private BigDecimal price;

    public Book(int id, String isbm, String name, String category, int remainNum, String author, BigDecimal price) {
        super();
        this.id = id;
        this.isbm = isbm;
        this.name = name;
        this.category = category;
        this.remainNum = remainNum;
        this.author = author;
        this.price = price;
    }

    public Book() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbm() {
        return isbm;
    }

    public void setIsbm(String isbm) {
        this.isbm = isbm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbm + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", remainNum=" + remainNum +
                ", author='" + author + '\'' +
                ", price=" + "¥" + price +
                '}';
    }
}
