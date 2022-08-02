package com.sparta.selectshop.models.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FolderRequestDto {
    private List<String> folderNames;
}
