package com.budgetpartner.APP.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken
) {
}
/*
public  class TokenResponse {

        String accessToken;
        String refreshToken;

        public TokenResponse(String accessToken, String refreshToken) {
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
}


}*/