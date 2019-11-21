package com.skripsi.yogi.planner.Common;

import java.util.Date;

/**
 * Created by Yogi on 3/5/2018.
 */

public class EntityBase {
    protected int id;
    protected Date created_date;
    protected Date updated_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
}
