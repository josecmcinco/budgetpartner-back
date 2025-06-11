package com.budgetpartner.APP.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDtoResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken
) {
}