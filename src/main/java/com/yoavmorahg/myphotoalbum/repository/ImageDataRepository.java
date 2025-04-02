package com.yoavmorahg.myphotoalbum.repository;

import com.yoavmorahg.myphotoalbum.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
}
