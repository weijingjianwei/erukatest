package com.meikinfo.erukaprovide.erukaprovide.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.meikinfo.erukaprovide.erukaprovide.service.FileExcutuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileExcutuService fileExcutuService;

    @PostMapping(value = "/upload/romote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean local(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        String fileType = StrUtil.subAfter(fileName, ".", true);

        try {
            fileExcutuService.analysis(file);
        } catch (InterruptedException e) {
            log.error("【导入人员信息数据失败】失败原因:{}"+ e.getCause());
        } catch (IOException e) {
            log.error("【导入人员信息数据失败】失败原因:{}"+ e.getCause());
        }

        return true;

    }
}
