/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;


/**
 *
 * @author henny
 */
public class ItemOptionTO extends AbstractEntityTO{
    
    private String description;
    private String title;
    private ItemTO item;
    
    private Integer count;
    
    private static final long serialVersionUID = 639616302059355682L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemTO getItem() {
        return item;
    }

    public void setItem(ItemTO item) {
        this.item = item;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
