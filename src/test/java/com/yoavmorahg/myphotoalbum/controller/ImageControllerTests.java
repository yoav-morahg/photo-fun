package com.yoavmorahg.myphotoalbum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoavmorahg.myphotoalbum.exception.ImageNotFoundException;
import com.yoavmorahg.myphotoalbum.model.ImageData;
import com.yoavmorahg.myphotoalbum.service.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ImageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final ImageData IMAGE1 =
            ImageData.builder()
                .id(1L)
                .filename("TEST1.jpg")
                .data(new byte[100])
                .archived(false)
                .build();

    private static final ImageData IMAGE2 =
            ImageData.builder()
                    .id(2L)
                    .filename("TEST2.jpg")
                    .data(new byte[100])
                    .archived(true)
                    .build();

    private static final ImageData IMAGE3 =
            ImageData.builder()
                    .id(3L)
                    .filename("TEST3.jpg")
                    .data(new byte[100])
                    .archived(false)
                    .build();

    @Test
    public void givenNull_whenGetImages_thenReturnsAllImages() throws Exception {
        List<ImageData> images = List.of(IMAGE1, IMAGE2, IMAGE3);

        given(imageService.getImages(null)).willReturn(images);

        ResultActions response = mockMvc.perform(get("/images"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(images.size())));
    }

    @Test
    public void givenFalse_whenGetImages_thenReturnsNonArchivedImages() throws Exception {
        List<ImageData> images = List.of(IMAGE1, IMAGE3);

        given(imageService.getImages(false)).willReturn(images);

        ResultActions response = mockMvc.perform(get("/images?viewArchived=false"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(images.size())));
    }

    @Test
    public void givenTrue_whenGetImages_thenReturnsNonArchivedImages() throws Exception {
        List<ImageData> images = List.of(IMAGE2);

        given(imageService.getImages(true)).willReturn(images);

        ResultActions response = mockMvc.perform(get("/images?viewArchived=true"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(images.size())));
    }

    @Test
    public void givenValidId_whenGetImage_thenReturnsImageData() throws Exception {
        given(imageService.getImage(2L)).willReturn(Optional.ofNullable(IMAGE2));

        ResultActions response = mockMvc.perform(get("/images/2"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.filename",
                        is("TEST2.jpg")))
                .andExpect(jsonPath("$.archived",
                        is(true)));
    }

    @Test
    public void givenInvalidId_whenGetImage_thenNotFoundStatus() throws Exception {
        given(imageService.getImage(2L)).willReturn(Optional.ofNullable(IMAGE2));

        ResultActions response = mockMvc.perform(get("/images/999"));

        response.andExpect(status().isNotFound());
    }



    @Test
    public void givenValidData_whenUpdateImage_returnsUpdatedData() throws Exception {

        given(imageService.updateIsArchived(1L, true))
                .willReturn(ImageData.builder()
                        .id(1L)
                        .filename("TEST1.jpg")
                        .data(new byte[100])
                        .archived(true)
                        .build());
        ResultActions response = mockMvc.perform(put("/images/1/archived?isArchived=true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.filename",
                        is("TEST1.jpg")))
                .andExpect(jsonPath("$.archived",
                        is(true)));



    }

    @Test
    public void givenInvalidId_whenUpdateImage_thenNotFoundStatus() throws Exception {
        given(imageService.updateIsArchived(99L, true))
                .willThrow(ImageNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/images/99/archived?isArchived=true"));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void givenValidData_whenUploadFile_thenStatusIsCreated() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());

        given(imageService.saveImage(Mockito.any(MultipartFile.class)))
                .willReturn(IMAGE1);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/images")
                        .file("file", jsonFile.getBytes())
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenValidData_whenPreview_thenStatusIsOkAndDataReturned() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());

        given(imageService.saveImage(Mockito.any(MultipartFile.class)))
                .willReturn(IMAGE1);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/images/preview")
                        .file("file", jsonFile.getBytes())
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

}
