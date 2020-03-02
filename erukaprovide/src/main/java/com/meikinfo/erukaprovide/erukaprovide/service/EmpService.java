package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.EsPerson;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import com.meikinfo.erukaprovide.erukaprovide.repository.PersonRepository;
import com.meikinfo.erukaprovide.erukaprovide.util.EnumUtils;
import com.netflix.discovery.converters.Auto;
import org.elasticsearch.index.query.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * empservice
 *
 * @author hongjw
 * @create 2020-02-29 15:12
 */
@Service
public class EmpService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    PersonRepository personRepository;


    public List<Person> loadEmpsForDb(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Person> peoples = personMapper.selectAll();
        peoples.forEach(p->p.setPoliticname(EnumUtils.PoliticsEnum.getPoliticsNameBycode(p.getPoliticid())));
        PageInfo<Person> personPageInfo = new PageInfo<>(peoples);
        return personPageInfo.getList();
    }



    public List<Person> loadEmpsForES(Integer page, Integer size, Long politicid, Long posid) {
        ArrayList<Person> peoples = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(ObjectUtil.isNotNull(politicid)){
            boolQueryBuilder.must(new TermQueryBuilder("politicid", politicid));
        }
        if(ObjectUtil.isNotNull(posid)){
            boolQueryBuilder.must(new MatchQueryBuilder("posid", posid)) ;
        }

        Page<EsPerson> personPage = personRepository.search(boolQueryBuilder, PageRequest.of(page, size));
//        personRepository.findAll().forEach(p->{
//            Person person = new Person();
//            BeanUtils.copyProperties(p,person);
//            person.setPoliticname(EnumUtils.PoliticsEnum.getPoliticsNameBycode(p.getPoliticid()));
//            peoples.add(person);
//        });
        personPage.getContent().forEach(p->{
            Person person = new Person();
            BeanUtils.copyProperties(p,person);
            person.setPoliticname(EnumUtils.PoliticsEnum.getPoliticsNameBycode(p.getPoliticid()));
            peoples.add(person);
        });
        return peoples;
    }
}
