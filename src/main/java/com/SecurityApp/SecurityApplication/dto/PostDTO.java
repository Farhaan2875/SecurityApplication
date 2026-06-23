package com.SecurityApp.SecurityApplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private Long postId;
    private String title;
    private String description;

}
