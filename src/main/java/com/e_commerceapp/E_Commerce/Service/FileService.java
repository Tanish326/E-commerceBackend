package com.e_commerceapp.E_Commerce.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service 
public class FileService {
   public String uploadimage(String path,MultipartFile file) throws IOException{
        String originalfilename = file.getOriginalFilename();
        String randomid = UUID.randomUUID().toString();
        String filename = randomid.concat(originalfilename.substring(originalfilename.lastIndexOf('.')));
        String filpath = path + File.separator + filename;
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(),Paths.get(filpath));
        return filename;
    }
    
}
