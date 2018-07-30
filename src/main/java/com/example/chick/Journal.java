package com.example.chick;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Journal {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id = 0;

    private String title;
    private String content;
    private Date date;

    Journal(){
    }

    public Journal(String title, String content){
        this.title = title;
        this.content = content;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String setContent(String content){
        this.content = content;
        return content;
    }

    public Date getDate(){
        return this.date;
    }
}
