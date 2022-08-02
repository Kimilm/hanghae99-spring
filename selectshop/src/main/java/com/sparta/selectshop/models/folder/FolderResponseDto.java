package com.sparta.selectshop.models.folder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FolderResponseDto {
    private Long id;

    private Long userId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public FolderResponseDto(Folder folder) {
        this.id = folder.getId();
        this.userId = folder.getUser().getId();
        this.name = folder.getName();
        this.createdAt = folder.getCreatedAt();
        this.modifiedAt = folder.getModifiedAt();
    }
}
