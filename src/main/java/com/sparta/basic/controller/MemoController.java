package com.sparta.basic.controller;

import com.sparta.basic.domain.memo.Memo;
import com.sparta.basic.domain.memo.MemoRequestDto;
import com.sparta.basic.repository.MemoRepository;
import com.sparta.basic.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        //최근 24시간 데이터만 가져오기
        //LocalDateTime start = LocalDateTime.now().minusDays(1);
        //LocalDateTime end = LocalDateTime.now();
        //return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(start, end);
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        return memoRepository.save(memo);
    }

    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto);
    }

    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }
}
