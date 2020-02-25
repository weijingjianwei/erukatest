package com.meikinfo.erukaconsumer.erukaconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * 描述:
 * upload服务
 *
 * @author hongjw
 * @create 2020-02-25 11:36
 */
@Service
public class UploadService {
    @Autowired
    private RestTemplate restTemplate;

    public boolean postFileToerukaProvider(String localFilePath, String provideUrl) {

           FileSystemResource resource = new FileSystemResource(new File(localFilePath));
           MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
           param.add("deviceId", "fileId");
           param.add("file", resource);
           restTemplate.postForObject(provideUrl+"/upload/romote", param, Boolean.class);
           return true;
    }
}
