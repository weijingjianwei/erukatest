package com.meikinfo.erukaconsumer.erukaconsumer.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.meikinfo.erukaconsumer.erukaconsumer.service.UploadService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;

/**
 * 描述:
 * uploadcontroller
 *
 * @author hongjw
 * @create 2020-02-24 16:07
 */
@Slf4j
@RestController
public class UploadController {

    @Value("${spring.servlet.multipart.location}")
    private String fileTempPath;

    @Value("${provide.url}")
    private String provideUrl;

    @Autowired
    private UploadService uploadService;

    @PostMapping(value = "/upload/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dict local(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Dict.create().set("code", 400).set("message", "文件内容为空");
        }
        String fileName = file.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String localFilePath = StrUtil.appendIfMissing(fileTempPath, "\\") + rawFileName + "-" + DateUtil.current(false) + "." + fileType;
        File transfile=null;
        try {
            transfile = new File(localFilePath);
            file.transferTo(transfile);
            //调取服务提供方接口,传输文件数据
            boolean uploadState = uploadService.postFileToerukaProvider(localFilePath, provideUrl);
            if(uploadState)
            log.info("【上传远程服务器成功】，绝对路径：{}", localFilePath);
        } catch (IOException e) {
            log.error("【文件上传】失败，绝对路径：{}", localFilePath);
            return Dict.create().set("code", 500).set("message", "文件上传失败");
        }finally {
            transfile.delete();
        }



        log.info("【文件上传远程服务器成功】绝对路径：{}", localFilePath);
        return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create().set("fileName", fileName).set("filePath", localFilePath));
    }

}
