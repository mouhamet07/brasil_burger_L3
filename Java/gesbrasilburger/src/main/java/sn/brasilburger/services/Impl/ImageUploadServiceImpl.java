package sn.brasilburger.services.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import sn.brasilburger.services.ImageUploadService;

public class ImageUploadServiceImpl implements ImageUploadService{
    private Cloudinary cloudinary;
    private static ImageUploadServiceImpl instance = null;
    private ImageUploadServiceImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }
    public static ImageUploadServiceImpl getInstance(Cloudinary cloudinary){
        if (instance==null) {
            return instance = new ImageUploadServiceImpl(cloudinary);
        }
        return instance;
    }
    @Override
    public String uploadImage(String localPath) throws IOException {
        localPath = localPath.replace("\"", "").trim();
        File file = new File(localPath);
        if (!file.exists()) {
            throw new IOException("Fichier introuvable : " + localPath);
        }
        Map upload = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return upload.get("secure_url").toString();
    }

}
