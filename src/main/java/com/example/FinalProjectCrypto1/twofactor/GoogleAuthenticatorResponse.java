package com.example.FinalProjectCrypto1.twofactor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleAuthenticatorResponse {

    private String secret;

    private String qrCode;

}
