package br.com.furb.tagarela.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CATEGORY.
 */
public class Category {

    private Integer red;
    private Integer green;
    private Integer blue;
    private String name;
    private Integer serverID;
    private Long id;

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(Integer red, Integer green, Integer blue, String name, Integer serverID, Long id) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
        this.serverID = serverID;
        this.id = id;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServerID() {
        return serverID;
    }

    public void setServerID(Integer serverID) {
        this.serverID = serverID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
