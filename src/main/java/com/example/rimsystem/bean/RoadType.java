package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;

/**
 * @auther luyu
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoadType {
    @Id
    private Integer id;
    private Integer typeId;
    private String name;
}
