package com.sda.QuickBite.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class Util {

    public static final String BASE64_PREFIX = "data:image/png; base64, ";

    public byte[] convertToBytes(MultipartFile multipartFile){
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }
    public void saveImage(MultipartFile dishImage,String name, Long id) {
        try{
            Path path = Paths.get("src/main/resources/static/img/"+ name);
            Files.createDirectories(path);
            String imageName = dishImage.getOriginalFilename();

            Files.copy(dishImage.getInputStream(),path.resolve( name + "_" + id.toString() + ".png"));
        }catch (Exception e){
            throw new RuntimeException("Error upload image!");
        }
    }


}
