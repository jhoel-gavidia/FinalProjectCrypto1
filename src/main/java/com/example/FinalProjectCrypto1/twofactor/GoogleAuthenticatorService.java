package com.example.FinalProjectCrypto1.twofactor;

public interface GoogleAuthenticatorService {

    String generarSecret();

    boolean verificarCodigo(String secret, int codigo);

}
