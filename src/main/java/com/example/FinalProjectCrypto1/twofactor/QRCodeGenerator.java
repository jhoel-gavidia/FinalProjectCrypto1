package com.example.FinalProjectCrypto1.twofactor;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCodeGenerator {

    public static String generarQR(String texto) {

        try {

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix = writer.encode(
                    texto,
                    BarcodeFormat.QR_CODE,
                    250,
                    250
            );

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    matrix,
                    "PNG",
                    outputStream
            );

            return Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
