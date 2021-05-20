package com.gregperlinli.bean;

import java.math.BigDecimal;

/**
 * @author gregperlinli
 */
public class Book {
    private int id;
    private String isbn;
    private String name;
    private String category;
    private int remainNum;
    // private int price;
    private String author;
    private BigDecimal price;

    public Book(int id, String isbn, String name, String category, int remainNum, String author, BigDecimal price) {
        super();
        this.id = id;
        this.isbn = isbn;
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
        return /*"Book{" +*/
                "id=" + id +
                ",\t isbn='" + isbn + '\'' +
                ",\t name='" + name + '\'' +
                ",\t category='" + category + '\'' +
                ",\t remainNum=" + remainNum +
                ",\t author='" + author + '\'' +
                ",\t price=" + "¥" + price/* +
                '}'*/;
    }
}
