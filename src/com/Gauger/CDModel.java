package com.Gauger;

import java.io.Serializable;

public class CDModel implements Serializable
{
    public int id;
    public String title;
    public String author;
    public String section;
    public int x;
    public int y;
    public int barcode;
    public String description;
    public boolean onLoan;

    public CDModel(
            int id,
            String title,
            String author,
            String section,
            int x,
            int y,
            int barcode,
            String description,
            boolean onLoan)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.section = section;
        this.x = x;
        this.y = y;
        this.barcode = barcode;
        this.description = description;
        this.onLoan = onLoan;
    }

    public static CDModel CreateTestCD()
    {
        return new CDModel(0, "test", "author", "A", 1, 2, 123, "description", true);
    }
}

