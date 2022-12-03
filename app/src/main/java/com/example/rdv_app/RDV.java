package com.example.rdv_app;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Timer;

// Data structure for invoices

public class RDV {

    public int id;
    public String date;
    public String time;
    public String title;
    public String content;

    public RDV(int id, String date, String time, String title, String content){
        this.id=id;
        this.date=date;
        this.time=time;
        this.title=title;
        this.content=content;
    }

    @Override
    public String toString() {
        return "RDV{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
