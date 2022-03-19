package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.List;

/**
 * @auther luyu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "road_picture")
public class RoadPicture {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String pictureName;
    private String pictureType;
    private String remark;
    private String picturePath;
    @Transient
    private MultipartFile pictureEntity;
    @Transient
    private int roadId;
}
