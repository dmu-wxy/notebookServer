package org.meteor.notebookserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextPojoImp implements Serializable {
    private Long userId;
    private Long id;
    private String headlineContent;
    private String headlineSpan;
    private String textSpan;
    private String textContent;
    private String  time;
    private String imgSrc;
    private int tag;
}
