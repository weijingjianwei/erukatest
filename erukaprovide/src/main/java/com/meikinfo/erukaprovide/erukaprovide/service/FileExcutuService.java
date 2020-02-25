package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 描述:
 * fileexcuterservice
 *
 * @author hongjw
 * @create 2020-02-25 12:00
 */
@Service
@Slf4j
public class FileExcutuService {

    @Autowired
    PersonMapper personMapper;
    private static int oneReadNum=5000;

    @Transactional
    public void analysis(MultipartFile file) throws InterruptedException, IOException {
        log.info("【开始导入人员信息数据】起始时间:{}", DateTime.now().toMsStr());


            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            Sheet sheet = reader.getSheet();
            int readtime = (sheet.getLastRowNum() / oneReadNum)+1;
            ExecutorService executorService= Executors.newFixedThreadPool(readtime);
            CountDownLatch latch = new CountDownLatch(readtime);
            IntStream.range(0,readtime).boxed().forEach(i->{
                int start,end;
                if(i+1==readtime){
                    start=oneReadNum*i;
                    end=sheet.getLastRowNum();
                }else {
                    start=i*oneReadNum;
                    end =(i+1)*oneReadNum;
                }
                executorService.execute(()->{
                    batchInsert2Db(sheet,start,end,latch);
                });
            });
            latch.await();
            executorService.shutdown();

        log.info("【导入人员信息数据完成】完成时间:{}", DateTime.now().toMsStr());

    }

    public void batchInsert2Db(Sheet sheet, int start, int end,CountDownLatch latch) {
        List<Person> list = new ArrayList<>();
        IntStream.range(start,end).boxed().forEach(i->{
            Row row = sheet.getRow(i);
            Person person = new Person(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), String.valueOf(row.getCell(2).getNumericCellValue()));
            list.add(person);
        });
        int insertnum = personMapper.insertList(list);
        log.info("【导入人员信息数据成功】起始行号：{} 完成行号{}", start,end);
        latch.countDown();
    }
}
