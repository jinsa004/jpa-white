package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/board/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {
        return new ResponseDto<>(1, "성공", boardService.findById(id));
    }

    @PostMapping("/board")
    public ResponseDto<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인을 진행해주세요.");
        }
        boardSaveReqDto.setSessionUser(sessionUser);
        BoardSaveRespDto boardSaveRespDto = boardService.save(boardSaveReqDto); // 서비스에는 단 하나의 객체만 전달한다.(규칙)
        return new ResponseDto<>(1, "성공", boardSaveRespDto);
    }

    @GetMapping("/board")
    public List<BoardAllRespDto> findAll() {
        return boardService.findAll();
    }

    @PutMapping("/board/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인을 진행해주세요.");
        }
        boardUpdateReqDto.setId(id);
        return new ResponseDto<>(1, "성공", boardService.update(boardUpdateReqDto));
    }

    @DeleteMapping("/board/{id}")
    public ResponseDto<?> deleteById(@PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인을 진행해주세요.");
        }
        boardService.deleteById(id);
        return new ResponseDto<>(1, "성공", null);
    }

}
