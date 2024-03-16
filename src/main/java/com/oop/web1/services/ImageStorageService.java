//package com.oop.web1.services;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//public class ImageStorageService {
//    @Value("${upload.path}")
//    private String uploadPath; // Đường dẫn tới thư mục lưu trữ ảnh
//
//    // Phương thức lưu ảnh và trả về đường dẫn
//    public String storeImage(MultipartFile imageFile) throws IOException {
//        // Kiểm tra xem người dùng đã chọn file ảnh chưa
//        if (imageFile.isEmpty()) {
//            throw new IOException("No image file selected");
//        }
//
//        // Tạo thư mục lưu trữ ảnh nếu chưa tồn tại
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }
//
//        // Tạo tên file ngẫu nhiên
//        String filename = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
//
//        // Lưu ảnh vào thư mục lưu trữ
//        byte[] bytes = imageFile.getBytes();
//        Path filePath = Paths.get(uploadPath + File.separator + filename);
//        Files.write(filePath, bytes);
//
//        // Trả về đường dẫn của ảnh đã lưu
//        return "/uploads/" + filename;
//    }
//}
