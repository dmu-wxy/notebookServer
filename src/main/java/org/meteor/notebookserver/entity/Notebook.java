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
    private Long id;
    private String title;
    private String abs;
    private Date lastChangeTime;
    private Date createTime;
    private String firstImageName;
    private Long userId;
    private int label;
}
