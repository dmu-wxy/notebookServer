package org.meteor.notebookserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileNote {
    private int num = 0;
    private String fileName;
    private String fileTotal;
    private int fileId;
}
