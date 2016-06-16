package net.tinybrick.intellitags.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangji on 2016/6/16.
 */
public class File {
    String id;
    String name;
    Date creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
