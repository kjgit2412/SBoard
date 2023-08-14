package org.koreadit.controllers.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreadit.models.board.AddService;
import org.koreadit.models.board.BoardData;
import org.koreadit.models.board.ViewService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class SBoardController {

    private final AddService addService;
    private final ViewService viewService;

    // ------------  게시글 목록 조회
    @GetMapping("/boards")
    public String boards(Model model) {

        List<BoardData> boardDatas = viewService.getBoardAll();
        model.addAttribute("boards", boardDatas);

        return "board/boards";
    }

    // ------------  게시글 등록하기 ( 내부적으로는 addBoard 루틴적용됨 )
    @GetMapping("/writeBoard")
    public String write(@ModelAttribute BoardField data) {

        return "board/writeBoard";
    }

    // ------------  게시글 등록 / 수정하기
/*
    @GetMapping("/addBoard")
    public String addBoard(@Valid BoardField boardField, Errors errors) {
        if (errors.hasErrors()) {
            return "board/writeBoard";
        }
        addService.addBoard(boardField);
      //  return "board/addBoard";
        return "redirect:/board/boards/" + boardField.getId();
    }
*/

    @PostMapping("/addBoard")
    public String addBoardPs(@Valid BoardField boardField, Errors errors) {

        if (errors.hasErrors()) {
            return "board/writeBoard";
        }

        addService.addBoard(boardField);

        return "redirect:/board/viewBoard/" + boardField.getId();
    }

    // ------------  게시글 상세보기
    @GetMapping("/viewBoard/{id}")
    public String viewBoard(@PathVariable long id, Model model) {

        BoardData boardData = viewService.getBoard(id);
        model.addAttribute("data", boardData);

        return "board/viewBoard";
    }

    // ------------  게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        viewService.deleteBoard(id);

        return "redirect:/board/boards";
    }


    // ------------  게시글 수정

    @GetMapping("/updateBoard/{id}")
    public String updateBoard(@PathVariable long id, Model model) {

        BoardData boardData = viewService.getBoard(id);
        model.addAttribute("data", boardData);

        return "board/updateBoard";
    }

    @GetMapping("/update")
    public String update(BoardField data) {

  //      System.out.println("수정");
        addService.update(data);

        return "redirect:/board/boards";
    }

}
