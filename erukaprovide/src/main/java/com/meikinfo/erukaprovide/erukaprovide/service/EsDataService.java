package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.date.DateTime;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.EsPerson;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;


import com.meikinfo.erukaprovide.erukaprovide.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 * EsDataService
 *
 * @author hongjw
 * @create 2020-02-26 16:20
 */
@Service
@Slf4j
public class EsDataService {

    @Autowired
    PersonMapper mapper;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private ElasticsearchTemplate esTemplate;
    /**
     * build es_data
     */
    public void buildPersonDb() {
        //构建用户index
        buildmapper();

        //查询用户数据
        List<Person> peoples = mapper.selectAll();

        for (Person people : peoples) {
            EsPerson esPerson = new EsPerson();
            BeanUtils.copyProperties(people,esPerson);
            personRepository.save(esPerson);
        }

        log.info("【导入人员信息到es库】完成导入条数:{}", peoples.size());

    }

    public void buildmapper(){

        esTemplate.deleteIndex(EsPerson.class);
        esTemplate.createIndex(EsPerson.class);
        esTemplate.putMapping(EsPerson.class);
        log.info("【完成person es库初始化..】");
    }
}
