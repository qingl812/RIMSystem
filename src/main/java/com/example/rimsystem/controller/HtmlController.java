package com.example.rimsystem.controller;

import com.example.rimsystem.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HtmlController {
    @RequestMapping("/homePage")
    public String homePage() {
        return "homePage";
    }

    @RequestMapping("/login")
    public String logIn() {
        return "login";
    }

    @RequestMapping("/maintenanceManagement")
    public String maintenanceManagement() {
        return "maintenanceManagement";
    }

    @RequestMapping("/moneyManagement")
    public String moneyManagement() {
        return "moneyManagement";
    }

    @RequestMapping("/roadDetection")
    public String roadDetection() {
        return "roadDetection";
    }

    @RequestMapping("/roadInformation")
    public String roadInformation() {
        return "roadInformation";
    }

    @RequestMapping("/systemManagement")
    public String systemManagement() {
        return "systemManagement";
    }

    @RequestMapping("/visaRecord")
    public String visaRecord() {
        return "visaRecord";
    }

    @RequestMapping("/workCommunication")
    public String workCommunication() {
        return "workCommunication";
    }

    @RequestMapping("/sharedPages/head")
    public String head() {
        return "sharedPages/head";
    }

    @RequestMapping("/sharedPages/navigation")
    public String navigation() {
        return "sharedPages/navigation";
    }

    @RequestMapping("/sharedPages/status")
    public String status() {
        return "sharedPages/status";
    }

    @RequestMapping("/frames/map")
    public String map() {
        return "frames/map";
    }

    @RequestMapping("/frames/mapStatus")
    public String mapStatus() {
        return "frames/mapStatus";
    }

    @RequestMapping("/frames/search")
    public String search() {
        return "frames/search";
    }
}
