package com.sparta.basic.domain.memo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemoRequestDto {
    private final String userName;
    private final String contents;
}
