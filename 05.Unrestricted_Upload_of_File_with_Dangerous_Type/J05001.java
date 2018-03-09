package testbed.unsafe;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.File;

public class J05001 {

    public void upload(HttpServletRequest request) throws ServletException {


        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

        String next = (String) mRequest.getFileNames().next();
        MultipartFile file = mRequest.getFile(next);

        String fileName = file.getOriginalFilename();

        File uploadDir = new File("/app/webapp/data/upload/notice");
        String uploadFilePath = uploadDir.getAbsolutePath()+"/"+fileName;

        try {
            file.transferTo(uploadFilePath);
        } catch (Exception e) {
            System.out.println("upload error");
        }
    }

}
