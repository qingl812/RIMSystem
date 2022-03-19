package com.example.rimsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patrol_log")
public class PatrolLog {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT")
    private Timestamp checkTime;
    private String weather;
    private String patrolPerson;
    private Integer roadId;
    private String roadName;
    @Transient
    private List<BranchPatrol> branchPatrol;
    public PatrolLog setRoadId(Integer roadId) {
        this.roadId = roadId;
        return this;
    }

    public PatrolLog(Integer id, Timestamp checkTime, String weather, String patrolPerson, Integer roadId, String roadName) {
        this.id = id;
        this.checkTime = checkTime;
        this.weather = weather;
        this.patrolPerson = patrolPerson;
        this.roadId = roadId;
        this.roadName = roadName;
    }
}
