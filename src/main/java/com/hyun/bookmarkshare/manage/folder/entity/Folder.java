package com.hyun.bookmarkshare.manage.folder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Folder {

    Long FOLDER_SEQ;
    Integer FOLDER_PARENT;
    String FOLDER_ORDER;
    String FOLDER_NAME;
    String FOLDER_CAPTION;
    String FOLDER_SCOPE;
    String USER_SEQ;

    @Override
    public String toString() {
        return "Folder{" +
                "FOLDER_SEQ='" + FOLDER_SEQ + '\'' +
                ", FOLDER_PARENT='" + FOLDER_PARENT + '\'' +
                ", FOLDER_ORDER='" + FOLDER_ORDER + '\'' +
                ", FOLDER_NAME='" + FOLDER_NAME + '\'' +
                ", FOLDER_CAPTION='" + FOLDER_CAPTION + '\'' +
                ", FOLDER_SCOPE='" + FOLDER_SCOPE + '\'' +
                ", USER_SEQ='" + USER_SEQ + '\'' +
                '}';
    }
}
