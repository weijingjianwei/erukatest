package com.meikinfo.erukaprovide.erukaprovide.controller;

import cn.hutool.core.lang.Dict;
import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import com.meikinfo.erukaprovide.erukaprovide.service.EmpService;
import com.meikinfo.erukaprovide.erukaprovide.util.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * empcontroller
 *
 * @author hongjw
 * @create 2020-02-29 14:50
 */
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping(value = "/employee/basic/emp")
    public List<Person> loadEmps(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Long politicid,Long posid){
//        return empService.loadEmpsForDb(page,size,politicid,posid);
        return empService.loadEmpsForES(page,size,politicid,posid);
    }

    @GetMapping(value = "/employee/basic/basicdata")
    public  Dict buildPoliticsDict(){
        ArrayList<Dict> PoliticsDicts = new ArrayList<>();
        ArrayList<Dict> PositionDicts = new ArrayList<>();
        for(EnumUtils.PoliticsEnum p: EnumUtils.PoliticsEnum.values()){
            PoliticsDicts.add(Dict.create().set("id",p.getId()).set("name",p.getName()));
        }

        for(EnumUtils.PositionEnum p: EnumUtils.PositionEnum.values()){
            PositionDicts.add(Dict.create().set("id",p.getId()).set("name",p.getName()));
        }

        return Dict.create().set("politics",PoliticsDicts).set("positions",PositionDicts);
    }

}
