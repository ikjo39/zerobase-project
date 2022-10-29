package com.example.zerobaseproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private int id;
    private double xCor;
    private double yCor;
    private String createdAt;
}
