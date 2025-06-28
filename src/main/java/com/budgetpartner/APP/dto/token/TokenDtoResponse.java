package com.budgetpartner.APP.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDtoResponse(
        @JsonProperty("accessToken")
        String accessToken,

        @JsonProperty("refreshToken")
        String refreshToken
) {
}