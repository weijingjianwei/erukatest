package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.meikinfo.erukaprovide.erukaprovide.consts.MessageStruct;
import com.meikinfo.erukaprovide.erukaprovide.consts.RabbitConsts;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    RabbitTemplate rabbitTemplate;
    private static int oneReadNum=5000;

    @Transactional
    public void analysis(MultipartFile file) throws InterruptedException, IOException {
        log.info("【开始导入人员信息数据】起始时间:{}", DateTime.now().toMsStr());

            List<Person> peoples=new ArrayList<>();
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
                    List<Person> list = batchBuildData(sheet, start, end, latch);
                    peoples.addAll(list);
                });
            });
            latch.await();
            executorService.shutdown();

            Optional.of("人员总数"+peoples.size()+"=========").ifPresent(System.out::println);

            personMapper.insertList(peoples);

        log.info("【导入人员信息数据完成】完成时间:{}", DateTime.now().toMsStr());

        //导入完全后通知
        rabbitTemplate.convertAndSend(RabbitConsts.DIRECT_MODE_QUEUE_ONE,new MessageStruct("恭喜成功导入"+peoples.size()+"条人员信息数据"));
        log.info("【消息发送中】");
    }

    public List<Person> batchBuildData(Sheet sheet, int start, int end, CountDownLatch latch) {
        List<Person> list = new ArrayList<>();
        IntStream.range(start,end).boxed().forEach(i->{
            Row row = sheet.getRow(i);
            Person person = new Person(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), String.valueOf(row.getCell(2).getNumericCellValue()));
            list.add(person);
        });
        log.info("【线程:{}完成人员信息组装:】 起始行数：{} 结束行数{}", Thread.currentThread().getName(),start,end);
        latch.countDown();
        return list;

    }
}
