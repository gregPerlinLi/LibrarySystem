package com.gregperlinli.bean;

/**
 * @author gregperlinli
 */
public class Book {
    private int id;
    private String isbn;
    private String name;
    private String category;
    private int remainNum;
    private int price;
    private String author;

    public Book(int id, String isbn, String name, String category, int remainNum, int price, String author) {
        super();
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.category = category;
        this.remainNum = remainNum;
        this.price = price;
        this.author = author;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
