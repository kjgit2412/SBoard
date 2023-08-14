package org.koreadit.controllers.board;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardField {

    private long id;

    @NotBlank
    private String poster;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

}
