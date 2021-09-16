package com.example.rimsystem.service.impl;

import com.example.rimsystem.mapper.RoadGeneralMapper;
import com.example.rimsystem.mapper.RoadMapper;
import com.example.rimsystem.service.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther luyu
 */
@Service
public class RoadServiceImpl implements RoadService {
    @Autowired
    RoadGeneralMapper roadGeneralMapper;
    @Autowired
    RoadMapper roadMapper;

}
