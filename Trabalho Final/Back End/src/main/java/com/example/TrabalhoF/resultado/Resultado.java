package com.example.TrabalhoF.resultado;

import com.example.TrabalhoF.rest.Document;

import java.util.List;

public class Resultado {

    List<Document> list;
    long total;

    public Resultado(List<Document> list, long total) {
        this.list = list;
        this.total = total;
    }

    public List<Document> getList() {
        return list;
    }

    public void setList(List<Document> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
