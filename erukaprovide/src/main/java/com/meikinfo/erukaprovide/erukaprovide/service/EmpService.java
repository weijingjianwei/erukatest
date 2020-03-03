package com.meikinfo.erukaprovide.erukaprovide.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meikinfo.erukaprovide.erukaprovide.config.HighLightResultMapper;
import com.meikinfo.erukaprovide.erukaprovide.dao.PersonMapper;
import com.meikinfo.erukaprovide.erukaprovide.domain.EsPerson;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import com.meikinfo.erukaprovide.erukaprovide.repository.PersonRepository;
import com.meikinfo.erukaprovide.erukaprovide.util.EnumUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private HighLightResultMapper highLightResultMapper;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    public List<Person> loadEmpsForDb(Integer page, Integer size,Long politicid, Long posid) {
        PageHelper.startPage(page, size);
        List<Person> peoples = personMapper.selectByparam(politicid,posid);
        peoples.forEach(p->p.setPoliticname(EnumUtils.PoliticsEnum.getPoliticsNameBycode(p.getPoliticid())));
        PageInfo<Person> personPageInfo = new PageInfo<>(peoples);
        return personPageInfo.getList();
    }



    public List<Person> loadEmpsForES(Integer page, Integer size, Long politicid, Long posid) {
        ArrayList<Person> peoples = new ArrayList<>();
        String preTags = "<span style=\"color:red\">";
        String postTags = "</span>";
//
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        if(ObjectUtil.isNotNull(politicid)){
//            boolQueryBuilder.must(new TermQueryBuilder("politicid", politicid));
//        }
//        if(ObjectUtil.isNotNull(posid)){
//            boolQueryBuilder.must(new MatchQueryBuilder("posid", posid)) ;
//        }
//
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        if(ObjectUtil.isNotNull(politicid)){
            queryBuilder.withQuery(QueryBuilders.termQuery("politicid",politicid));
        }
        if(ObjectUtil.isNotNull(posid)){
            queryBuilder.withQuery(QueryBuilders.matchQuery("posid",posid));
        }
        queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        queryBuilder.withPageable(PageRequest.of(page, size));
        queryBuilder.withHighlightFields(new HighlightBuilder.Field("email").preTags(preTags).postTags(postTags));

//        Page<EsPerson> personPage = personRepository.search(queryBuilder.build());
        Page<EsPerson> personPage = elasticsearchTemplate.queryForPage(queryBuilder.build(),EsPerson.class, highLightResultMapper);
        personPage.getContent().forEach(p->{
            Person person = new Person();
            BeanUtils.copyProperties(p,person);
            person.setPoliticname(EnumUtils.PoliticsEnum.getPoliticsNameBycode(p.getPoliticid()));
            peoples.add(person);
        });
        return peoples;

    }
}
