package com.guedelho.buildYourDeck.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static String getExtencaoArquivo(String nomeArquivo) {
        String[] nameFile = nomeArquivo.split("\\.");
        return nameFile[nameFile.length -1];
    }

    public static String uploadImagem(String fileName, byte[] file, String caminhoImagem) throws IOException {
        String fileExtension =  getExtencaoArquivo("jpg");
        Path path = Paths.get(caminhoImagem);
        Files.createDirectories(path);
        Path pathImg = Paths.get(caminhoImagem + fileName);
        Files.write(pathImg, file);
        return fileName;
    }

    public static String getImage(String fileName) throws IOException {
        File imagem = new File(fileName);
        byte [] image = Files.readAllBytes(imagem.toPath());
        String imgBase64 = org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(image);
        return "data:application/img;base64,"  + imgBase64;
    }
}
