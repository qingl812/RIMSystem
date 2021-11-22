package com.example.rimsystem.controller;

import com.example.rimsystem.service.RoadDocService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.seucurity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @auther luyu
 */
@Controller
@RequestMapping("/api")
public class RoadDocController {
    @Autowired
    RoadDocService roadDocService;
    public final static String UPLOAD_PATH_PREFIX = "static/uploadFile/";
    @PostMapping("uploadFile")
    @ResponseBody
    public Result uploadFile(HttpServletRequest request, MultipartFile uploadFile, Authentication authentication){
        if(uploadFile.isEmpty()){
            //返回选择文件提示
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
//        获取spring security中登录的用户名
        User principal = (User)authentication.getPrincipal();
        String username = principal.getUsername()+'/';
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
//        给每一个用户都创建一个文件夹来存放他上传的文件
        File file = new File(realPath + username);
        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
//        获取上传的文件一开始的文件名
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            File newFile = new File(file.getAbsolutePath()+File.separator+newName);
            uploadFile.transferTo(newFile);
            return Result.ok();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error();
    }
}
