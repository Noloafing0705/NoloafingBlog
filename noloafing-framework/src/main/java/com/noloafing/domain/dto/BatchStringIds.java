package com.noloafing.domain.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Data
public class BatchStringIds {
    List<String> ids;
}
