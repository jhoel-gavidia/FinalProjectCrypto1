package com.example.FinalProjectCrypto1.twofactor;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthenticatorServiceImpl implements GoogleAuthenticatorService {

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    @Override
    public String generarSecret() {

        return googleAuthenticator
                .createCredentials()
                .getKey();

    }

    @Override
    public boolean verificarCodigo(String secret, int codigo) {

        return googleAuthenticator.authorize(secret, codigo);

    }

}