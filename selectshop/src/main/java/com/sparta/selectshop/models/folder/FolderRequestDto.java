package com.sparta.selectshop.models.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderRequestDto {
    private List<String> folderNames;
}
