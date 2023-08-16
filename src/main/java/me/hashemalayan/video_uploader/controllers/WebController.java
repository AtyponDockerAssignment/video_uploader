package me.hashemalayan.video_uploader.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.hashemalayan.video_uploader.domain.FileBlob;
import me.hashemalayan.video_uploader.repositories.FileBlobRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class WebController {

    private FileBlobRepository fileBlobRepository;
    @GetMapping("/uploadForm")
    public String displayUploadForm() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        System.out.println("Uploading file: " + file.getOriginalFilename());

        redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");

        var restTemplate = new RestTemplate();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var body = new LinkedMultiValueMap<String, Object>();

        var resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", resource);

        var requestEntity = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(
                "http://fileservice:8080/api/v1/upload",
                requestEntity,
                String.class
        );

        System.out.println(response);

        if(response.getStatusCode().value() == 200 && response.hasBody() && response.getBody() != null) {
            fileBlobRepository.save(
                    FileBlob.builder()
                            .id(0L)
                            .url(response.getBody())
                            .build()
            );
            System.out.println("File upload succeeded");
        }
        else
            System.out.println("File upload failed!");


        return "redirect:uploadForm";
    }
}