package com.example.rimsystem.controller;

import com.example.rimsystem.annotation.Log;
import com.example.rimsystem.bean.RoadDoc;
import com.example.rimsystem.bean.RoadPicture;
import com.example.rimsystem.bean.Table;
import com.example.rimsystem.service.RoadDocService;
import com.example.rimsystem.service.RoadService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.seucurity.ResultCode;
import com.example.rimsystem.tool.DocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auther luyu
 */
@Controller
public class RoadDocController {
    @Autowired
    RoadDocService roadDocService;
    @Autowired
    RoadService roadService;
    public final static String UPLOAD_PATH_PREFIX = "static/uploadFile/";
    @PostMapping("uploadPicture")
    @ResponseBody
    @Log("上传照片")
    public Result uploadPicture(RoadPicture roadPicture){
        MultipartFile pictureEntity = roadPicture.getPictureEntity();
        if(pictureEntity.isEmpty()){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+UPLOAD_PATH_PREFIX;
        File file = new File(realPath+roadPicture.getRoadId()+'/'+"img"+'/');
        if(!file.isDirectory()){
            file.mkdirs();
        }
        String oldName = pictureEntity.getOriginalFilename();
        String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            File newFile = new File(file.getAbsolutePath()+File.separator+newName);
            pictureEntity.transferTo(newFile);
            roadPicture.setPicturePath(file.getAbsolutePath()+File.separator+newName);
            roadService.insertPicture(roadPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
//    上传各种附件,除了图片附件以外
    @PostMapping("/uploadFile")
    @Log("上传附件")
    @ResponseBody
    public Result uploadFile(RoadDoc roadDoc){
        MultipartFile uploadFile = roadDoc.getDocEntity();
        if(uploadFile.isEmpty()){
            //返回选择文件提示
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+UPLOAD_PATH_PREFIX;
//        给每一个用户都创建一个文件夹来存放他上传的文件
        File file = new File(realPath + roadDoc.getRoadId()+'/'+"doc"+'/');
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
            roadDoc.setDocPath(file.getAbsolutePath()+File.separator+newName);
            roadDocService.insertDoc(roadDoc);
            return Result.ok();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error();
    }
//    下载各种附件，上传的是什么形式，下载下来就是什么形式
    @RequestMapping("/downLoadFile")
    public void downLoadFile(Integer docId, HttpServletResponse response){
        try {
            RoadDoc roadDoc = roadDocService.selectDocPath(docId);
            String docPath = roadDoc.getDocPath();
            File file = new File(docPath);
            // 获取文件名
            String filename = file.getName();
//        获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
// filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    将文件转为word形式，下载。将信息保存到word中供下载
    /*
     *将文件转化为word 在道路检测阶段
     */
    @RequestMapping("/downloadWord")
    @Log("下载附件")
    public void downloadWord(@RequestBody Table table, HttpServletResponse response) throws IOException {
        String newWordName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+".docx";
        newWordName = URLEncoder.encode(newWordName, "utf-8"); //这里要用URLEncoder转下才能正确显示中文名称
        response.addHeader("Content-Disposition", "attachment;filename=" + newWordName+"");
        response.setContentType("application/octet-stream");
        Map dataMap = new HashMap();
        dataMap.put("projectName",table.getProjectName());
        dataMap.put("projectType",table.getProjectType());
        dataMap.put("projectAddress",table.getProjectAddress());
        dataMap.put("projectPrice",table.getProjectPrice());
        dataMap.put("person1",table.getResponsible_person1());
        dataMap.put("person2",table.getResponsible_person2());
        dataMap.put("projectContent",table.getProjectDetail());
        dataMap.put("projectAdvise",table.getAdvise());
        dataMap.put("projectUnit",table.getConstruction());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(table.getApproveTime().getTime());
        String format = df.format(date);
        dataMap.put("approveTime",format);
        date.setTime(table.getStartTime().getTime());
        format = df.format(date);
        dataMap.put("startTime",format);
        date.setTime(table.getEndTime().getTime());
        format = df.format(date);
        dataMap.put("endTime",format);
        DocUtils.downloadDoc(response.getOutputStream(), newWordName,"/docTemplates/template1.docx",dataMap);
    }

}
