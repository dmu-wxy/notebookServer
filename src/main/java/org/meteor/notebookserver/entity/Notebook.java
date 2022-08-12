package org.meteor.notebookserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notebook implements Serializable {
    private int id;
    private String title;
    private String content;
    private Date lastChangeTime;
    private Date createTime;
}