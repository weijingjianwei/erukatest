<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">人员信息系统主页</span>
        <div style="display: flex;align-items: center;margin-right: 7px">
        </div>
      </el-header>
      <el-container>
        <el-aside width="180px" class="home-aside">
          <div style="display: flex;justify-content: flex-start;width: 180px;text-align: left;">
            <el-menu style="background: #ececec;width: 180px;" unique-opened router>
              <template v-for="(item,index) in this.routes" v-if="!item.hidden">
                <el-submenu :key="index" :index="index+''">
                  <template slot="title">
                    <i class="fa fa-user-circle-o" style="color: #20a0ff;width: 14px;"></i>
                    <span slot="title">{{item.name}}</span>
                  </template>
                  <el-menu-item width="180px"
                                style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 170px;text-align: left"
                                v-for="child in item.children"
                                :index="child.path"
                                :key="child.path">{{child.name}}
                  </el-menu-item>
                </el-submenu>
              </template>
            </el-menu>
          </div>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view ></router-view>
          </keep-alive>
          <!--<router-view v-if="!this.$route.meta.keepAlive"></router-view>-->
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
  export default {
    mounted: function () {
    },
    methods: {

    },
    data() {
      return {
        isDot: false
      }
    },
    computed: {
      routes() {
        return [
          {

          path: "home",
          component(resolve){
              require(['../components/Home.vue'], resolve)
          },
          name: "主页",
          iconCls: "",
          meta: "主页",
          children: [
            {
              path: "/emp/basic",
              component(resolve){
                require(['../components/emp/EmpBasic.vue'], resolve)
              },
              name: "人员基本信息",
              iconCls: "",
              meta: "人员基本信息",
            }
          ]
        }
        ]
      }
    }
  }
</script>
<style>
  .home-container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .home-header {
    background-color: #20a0ff;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: content-box;
    padding: 0px;
  }

  .home-aside {
    background-color: #ECECEC;
  }

  .home-main {
    background-color: #fff;
    color: #000;
    text-align: center;
    margin: 0px;
    padding: 0px;;
  }

  .home_title {
    color: #fff;
    font-size: 22px;
    display: inline;
    margin-left: 8px;
  }

  .home_userinfo {
    color: #fff;
    cursor: pointer;
  }

  .home_userinfoContainer {
    display: inline;
    margin-right: 20px;
  }

  .el-submenu .el-menu-item {
    width: 180px;
    min-width: 175px;
  }
</style>
