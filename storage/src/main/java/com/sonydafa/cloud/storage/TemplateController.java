package com.sonydafa.cloud.storage;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TemplateController {
    @RequestMapping("/viewList")
    @ResponseBody public String fileList(){
        String home=System.getenv("HOME")+"/cloudStorage";
        File dir=new File(home);
        List<String> fileList=new ArrayList<>();
        if(dir.exists() && dir.isDirectory()){
            String[] list = dir.list();
            if(list!=null){
                fileList.addAll(Arrays.asList(list));
            }
        }
        return new Gson().toJson(fileList);
    }
    @RequestMapping("/uploadFile")
    @ResponseBody public String file(@RequestParam("file")MultipartFile file){
        String filename = file.getOriginalFilename();
        String home=System.getenv("HOME")+"/cloudStorage";
        File cloudStorage=new File(home);
        //不存在就新建
        if(!cloudStorage.exists() && !cloudStorage.isDirectory()){
            boolean mkdir = cloudStorage.mkdir();
        }
        File f=new File(home+"/"+filename);
        try{
            FileOutputStream fs=new FileOutputStream(f);
            fs.write(file.getBytes());
            return "ok";
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("上传成功");
        return "fail";
    }
    @RequestMapping(value="/downloadFile/{fName}",method = RequestMethod.GET)
    public void downloadFile(@PathVariable String fName, HttpServletResponse response){
        String dir=System.getenv("HOME")+"/cloudStorage/";
        File file=new File(dir+fName);

        if(file.exists()){
            try {
                ServletOutputStream os = response.getOutputStream();
                FileInputStream fs=new FileInputStream(file);
                byte[]buff=new byte[1024];
                int len=-1;
                while((len=fs.read(buff))!=-1){
                    os.write(buff,0,len);
                }
                os.close();
                fs.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
