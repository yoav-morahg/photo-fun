package com.yoavmorahg.myphotoalbum.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="image_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ImageData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="filename", nullable = false)
    private String filename;

    @Lob
    @Column(name="img_data", nullable = false)
    private byte[] data;

    @Column(name="is_archived")
    public boolean archived = false;

    @Column(name = "created_ts", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    public LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    public LocalDateTime updatedTs;
}
