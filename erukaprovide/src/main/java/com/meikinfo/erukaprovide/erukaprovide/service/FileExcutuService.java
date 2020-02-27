package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.meikinfo.erukaprovide.erukaprovide.consts.MessageStruct;
import com.meikinfo.erukaprovide.erukaprovide.consts.RabbitConsts;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
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
    private static int oneReadNum=100;

    @Transactional
    public void analysis(MultipartFile file) throws InterruptedException, IOException {
        log.info("【开始导入人员信息数据】起始时间:{}", DateTime.now().toMsStr());

            List<Person> peoples=new ArrayList<>();
//            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
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

        //导入后通知
        rabbitTemplate.convertAndSend(RabbitConsts.FANOUT_MODE_QUEUE,"",new MessageStruct("恭喜成功导入"+peoples.size()+"条人员信息数据"));

        log.info("【消息发送中】");
    }

    public List<Person> batchBuildData(Sheet sheet, int start, int end, CountDownLatch latch) {
        List<Person> list = new ArrayList<>();
        IntStream.range(start,end).boxed().forEach(i->{
            Row row = sheet.getRow(i);
            Person person = buildPerson(row);
            list.add(person);
        });
        log.info("【线程:{}完成人员信息组装:】 起始行数：{} 结束行数{}", Thread.currentThread().getName(),start,end);
        latch.countDown();
        return list;

    }

    private Person buildPerson(Row row) {


        Person person = new Person();
        person.setId((int)row.getCell(0).getNumericCellValue());
        person.setName(row.getCell(1).getStringCellValue());
        person.setGender(row.getCell(2).getStringCellValue());
        person.setBirthday(formatterData(row.getCell(3).getStringCellValue()));
        person.setIdcard(row.getCell(4).getNumericCellValue()+"");
        person.setWedlock(row.getCell(5).getStringCellValue());
        person.setNationid((int)row.getCell(6).getNumericCellValue());
        person.setNativeplace(row.getCell(7).getStringCellValue());
        person.setPoliticid((int)row.getCell(8).getNumericCellValue());
        person.setEmail(row.getCell(9).getStringCellValue());
        person.setPhone(row.getCell(10).getNumericCellValue()+"");
        person.setAddress(row.getCell(11).getStringCellValue());
        person.setDepartmentid((int)row.getCell(12).getNumericCellValue());
        person.setJoblevelid((int)row.getCell(13).getNumericCellValue());
        person.setPosid((int)row.getCell(14).getNumericCellValue());
        person.setEngageform(row.getCell(15).getStringCellValue());
        person.setTiptopdegree(row.getCell(16).getStringCellValue());
        person.setSpecialty(row.getCell(17).getStringCellValue());
        person.setSchool(row.getCell(18).getStringCellValue());
        person.setBegindate(formatterData(row.getCell(19).getStringCellValue()));
        person.setWorkstate(row.getCell(20).getStringCellValue());
        person.setWorkid(row.getCell(21).getStringCellValue());

        return person;
    }

    private Date formatterData(String stringCellValue) {
        String[] split = stringCellValue.split("/");
        Calendar myCalendar = new GregorianCalendar(Integer.parseInt(split[2]),Integer.parseInt(split[1]),Integer.parseInt(split[0]));
        return myCalendar.getTime();

    }
}
