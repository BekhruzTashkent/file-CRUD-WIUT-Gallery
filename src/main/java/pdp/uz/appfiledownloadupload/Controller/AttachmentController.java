package pdp.uz.appfiledownloadupload.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdp.uz.appfiledownloadupload.Entity.Attachment;
import pdp.uz.appfiledownloadupload.Entity.AttachmentContent;
import pdp.uz.appfiledownloadupload.Repository.AttachmentContentRepository;
import pdp.uz.appfiledownloadupload.Repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

/*
Author: Karimov Bekhruz
CRUD file in DB
Technologies: Java, Spring
server port number 8065
 */

@Controller
@RestController
@RequestMapping("/attachment")
public class AttachmentController {  //  THIS CLASS WE USE TO HANDLE FILE FROM SERVER AND PUSH IT TO DB

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    private static final String uploadDirectory = "yuklangan"; //DIRECTORY FOR

    //INSERT
    @PostMapping("/upload")   //UPLOAD FILE TO DB
    public String uploadFileToDB(MultipartHttpServletRequest request) throws IOException //we need this because file comes as a lot of part
    {
        Iterator<String> fileNames = request.getFileNames(); //if there several coming files
        MultipartFile file = request.getFile(fileNames.next());  //now we getting real file

        if(file != null){
            //GET INFO ABOUT FILE
            String originalFileName = file.getOriginalFilename();  //all 3 from Attachment table
            long size = file.getSize();
            String contentType = file.getContentType();

            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(originalFileName);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAsosiyContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);

            return "File saved. Id: "+ savedAttachment.getId();
        }
        return "error";
    }

    //READ
    @GetMapping("/download/{id}")  //DOWNLOAD FILE FROM DB
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {

      Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
    if(optionalAttachment.isPresent()){
        Attachment attachment = optionalAttachment.get();  //here we save byte
          Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
          if(contentOptional.isPresent()){

              AttachmentContent attachmentContent = contentOptional.get();
              //sending to clientoriginal name
              response.setHeader("Content-Disposition" , "attachment; filename = \""
                      +attachment.getFileOriginalName()+"\"");

              response.setContentType(attachment.getContentType());  //sending to client content type

              FileCopyUtils.copy(attachmentContent.getAsosiyContent(), response.getOutputStream()); ////sending to client bytes

          }
    }
}

    //DELETE
    @DeleteMapping("/delete/{id}")
    public void deleteFile(@PathVariable Integer id){
        attachmentContentRepository.deleteById(id);
    }


    //UPDATE
    @PutMapping("/update/{id}")
    public String updateFile(@PathVariable Integer id, MultipartHttpServletRequest request) throws IOException
    {
    Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if(optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();  //here we save byte
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if(contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();
                Iterator<String> fileNames = request.getFileNames(); //if there several coming files
                MultipartFile file = request.getFile(fileNames.next());  //now we getting real file

                if(file != null){
                    //GET INFO ABOUT FILE
                    String originalFileName = file.getOriginalFilename();  //all 3 from Attachment table
                    long size = file.getSize();
                    String contentType = file.getContentType();


                    attachment.setFileOriginalName(originalFileName);
                    attachment.setSize(size);
                    attachment.setContentType(contentType);
                    Attachment savedAttachment = attachmentRepository.save(attachment);

                    attachmentContent.setAsosiyContent(file.getBytes());
                    attachmentContent.setAttachment(savedAttachment);
                    attachmentContentRepository.save(attachmentContent);

                    return "File saved. Id: "+ savedAttachment.getId();
                }

            }
        }
         return "error";
    }
}
