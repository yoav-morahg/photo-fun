package com.yoavmorahg.myphotoalbum.service;

import com.yoavmorahg.myphotoalbum.exception.ImageNotFoundException;
import com.yoavmorahg.myphotoalbum.model.ImageData;
import com.yoavmorahg.myphotoalbum.repository.ImageDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDataRepository imageDataRepository;

    public ImageServiceImpl(ImageDataRepository imageDataRepository) {
        this.imageDataRepository = imageDataRepository;
    }

    @Override
    public ImageData saveImage(MultipartFile file) throws IOException {
        ImageData data = getNewImageData(file);
        return imageDataRepository.save(data);
    }

    @Override
    public ImageData preview(MultipartFile file)  throws IOException {
        return getNewImageData(file);
    }

    @Override
    public List<ImageData> getImages(Boolean archivedSet) {
        List<ImageData> images = imageDataRepository.findAll();
        return images.stream()
                .filter( img -> {
                        if (archivedSet == null) {
                            return true;
                        }
                        return archivedSet ? img.isArchived() : !img.isArchived();
                    })
                .sorted(
                        (a, b) -> Long.valueOf(a.getId() - b.getId()).intValue()
                )
                .toList();
    }
    @Override
    public Optional<ImageData> getImage(Long imageId) {
        return imageDataRepository.findById(imageId);
    }

    @Override
    //TODO do this better with PATCH and lambdas
    public ImageData updateIsArchived(Long imageId, boolean isArchived) throws ImageNotFoundException {
        Optional<ImageData> image = imageDataRepository.findById(imageId);
        if (image.isPresent()) {
            if (image.get().isArchived() != isArchived) {
                image.get().setArchived(isArchived);
                image.get().setUpdatedTs(LocalDateTime.now());
                return imageDataRepository.save(image.get());
            } else {
                return image.get();
            }
        }
        throw new ImageNotFoundException("Unable to retrieve image with id " + imageId);
    }

    private ImageData getNewImageData(MultipartFile file) throws IOException {
        ImageData data = new ImageData();
        data.setFilename(file.getOriginalFilename());
        data.setData(file.getBytes());
        return data;
    }


}
