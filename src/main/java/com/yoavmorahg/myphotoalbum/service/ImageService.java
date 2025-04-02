package com.yoavmorahg.myphotoalbum.service;

import com.yoavmorahg.myphotoalbum.exception.ImageNotFoundException;
import com.yoavmorahg.myphotoalbum.model.ImageData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    /**
     * Returns data for image without saving it (to be used to preview image)
     * @param file image file
     * @return ImageData object
     */
    ImageData preview(MultipartFile file) throws IOException;


    /**
     * Saves an image
     * @param file image file
     * @return ImageData object
     */
    ImageData saveImage(MultipartFile file) throws IOException;

    /**
     * Get all images
     * @param archivedSet whether to get archived images (if null returns all images)
     * @return List of ImageData objects
     */
    List<ImageData> getImages(Boolean archivedSet);

    /**
     * Get an image by its id
     * @param imageId id of image to retrieve
     * @return Optional with ImageData object if not empty
     */
    Optional<ImageData> getImage(Long imageId);

    /**
     * Update archived status of image
     * @param imageId id of image to update
     * @param isArchived value to update update state to
     * @return ImageData object
     * @throws ImageNotFoundException
     */
    ImageData updateIsArchived(Long imageId, boolean isArchived) throws ImageNotFoundException;


}
