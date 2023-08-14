package org.koreadit.controllers.board;

import lombok.Data;

@Data
public class BoardField {

    private long id;
    private String poster;
    private String subject;
    private String content;

}
