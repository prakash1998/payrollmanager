package com.pra.payrollmanager.response.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseError {
  private Instant timestamp;
  private String display;
  private String message;
  private String trace;
}
