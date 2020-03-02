package com.meikinfo.erukaprovide.erukaprovide.util;

import cn.hutool.core.lang.Dict;

import java.util.ArrayList;

/**
 * 描述:
 * EnumUtils
 *
 * @author hongjw
 * @create 2020-03-02 11:02
 */
public class EnumUtils {

    /**
     * 政治面貌
     */
    public  enum  PoliticsEnum{

        DANYUAN(1,"中共党员"),
        YUBEI_DANYUAN(2,"中共预备党员"),
        GONGQING_TUANYUAN(3,"共青团员"),
        MINGGE_DANYUAN(4,"民革党员"),
        MINGMENG_MENGYUAN(5,"民盟盟员"),
        MINGJIAN_HUIYUAN(6,"民建会员");



        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        PoliticsEnum(int id, String name){
            this.id=id;
            this.name=name;
        }

        public static String getPoliticsNameBycode(int id){
            for(PoliticsEnum p: PoliticsEnum.values()){
                if(p.getId()==id)
                    return p.getName();
            }
            return null;
        }

    }

    /**
     * 职位
     */

    public enum PositionEnum {

        JISHUZONGJAIN(29,"技术总监"),
        KAIFA(30,"开发人员"),
        CHANPING(31,"产品"),
        UI(32,"UI"),
        QIANDUAN(33,"前端"),
        YUNWEI(34,"运维");



        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        PositionEnum(int id, String name){
            this.id=id;
            this.name=name;
        }

        public static String getPoliticsNameBycode(int id){
            for(PositionEnum p: PositionEnum.values()){
                if(p.getId()==id)
                    return p.getName();
            }
            return null;
        }
    }
}
