package com.yoavmorahg.myphotoalbum.controller;

import com.yoavmorahg.myphotoalbum.exception.ImageNotFoundException;
import com.yoavmorahg.myphotoalbum.model.ImageData;
import com.yoavmorahg.myphotoalbum.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/images")
    public List<ImageData> getGallery(@RequestParam(required = false) Boolean viewArchived) {
        return imageService.getImages(viewArchived);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<ImageData> get(@PathVariable Long imageId) {
        return imageService.getImage(imageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageData uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return imageService.saveImage(file);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/images/preview")
    public ImageData previewFile(@RequestParam("file") MultipartFile file) {
        try {
            return imageService.preview(file);
        }  catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO refactor to use PATCH rather than PUT
    @PutMapping("/images/{imageId}/archived")
    public ImageData updateImage(@PathVariable Long imageId,
                                                 @RequestParam Boolean isArchived) {
        try {
            return imageService.updateIsArchived(imageId, isArchived);
        } catch (ImageNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
