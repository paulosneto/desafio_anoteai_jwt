package com.anoteai.pedidos.anoteai.dto;

import com.anoteai.pedidos.anoteai.domain.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
