package com.noloafing.domain.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ChangeStatusDto {
    private Long id;
    private String status;
}
