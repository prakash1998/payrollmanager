package com.pra.payrollmanager.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseError {
  private LocalDateTime timestamp;
  private String display;
  private String message;
  private String trace;
}
