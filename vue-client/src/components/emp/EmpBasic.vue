<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="高级搜索"
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchEmp"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                          style="margin-right: 5px"></i>{{fileUploadBtnText}}
            </el-button>
          </el-upload>
          <el-button type="success" size="mini" @click="exportEmps"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddEmpView">
            添加人员
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div
              style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
              v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="5">
                  政治面貌:
                  <el-select v-model="emp.politicid" style="width: 130px" size="mini" placeholder="政治面貌">
                    <el-option
                      v-for="item in politics"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>

                <el-col :span="4">
                  职位:
                  <el-select v-model="emp.posid" style="width: 130px" size="mini" placeholder="请选择职位">
                    <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>

                <el-col :span="10">
                  入职日期:
                  <el-date-picker
                    v-model="beginDateScope"
                    unlink-panels
                    size="mini"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchEmp">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="emps"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="left"
              width="30">
            </el-table-column>
            <el-table-column
              prop="name"
              align="left"
              fixed
              label="姓名"
              width="90">
            </el-table-column>

            <el-table-column
              prop="gender"
              label="性别"
              width="50">
            </el-table-column>

            <el-table-column
              prop="politicname"
              label="政治面貌"
              width="90">
            </el-table-column>

            <el-table-column
              width="100"
              align="left"
              label="出生日期">
              <template slot-scope="scope">{{ scope.row.birthday | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="idcard"
              width="150"
              align="left"
              label="身份证号码">
            </el-table-column>
            <el-table-column
              prop="wedlock"
              width="70"
              label="婚姻状况">
            </el-table-column>
            <el-table-column
              width="50"
              prop="nationid"
              label="民族">
            </el-table-column>

            <el-table-column
              prop="nativeplace"
              width="100"
              label="籍贯">
            </el-table-column>

            <el-table-column
              prop="email"
              width="100"
              align="left"
              label="电子邮件">
            </el-table-column>
            <el-table-column
              prop="phone"
              width="100"
              label="电话号码">
            </el-table-column>
            <el-table-column
              prop="address"
              width="220"
              align="left"
              label="联系地址">
            </el-table-column>


          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        emps: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        nations: [],
        politics: [],
        positions: [],
        joblevels: [],
        totalCount: -1,
        currentPage: 1,
        deps: [],
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        emp: {
          name: '',
          gender: '',
          birthday: '',
          idcard: '',
          wedlock: '',
          nationid: '',
          nativeplace: '',
          politicid: '',
          politicname: '',
          email: '',
          phone: '',
          address: '',
          departmentid: '',
          departmentname: '所属部门...',
          jobLevelid: '',
          posid: '',
          engageform: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: ''
        },
    };
    },
    mounted: function () {
      this.initData();
      this.loadEmps();
    },
    methods: {
      fileUploadSuccess(response, file, fileList) {

      },
      fileUploadError(err, file, fileList) {

      },
      beforeFileUpload(file) {

      },
      exportEmps() {

      },
      cancelSearch() {

      },
      showAdvanceSearchView() {
        this.advanceSearchViewVisible=true;
      },
      handleSelectionChange(val) {

      },


      keywordsChange(val) {

      },
      searchEmp() {
        this.loadEmps();
      },
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadEmps();
      },
      loadEmps() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/employee/basic/emp?page=" + this.currentPage + "&size=10&politicid="+this.emp.politicid+"&posid="+this.emp.posid).then(resp => {
          debugger;
          this.tableLoading = false;
          _this.emps=resp.data;
        })
      },


      initData() {
        var _this = this;
        this.getRequest("/employee/basic/basicdata").then(resp => {
          _this.politics=resp.data.politics;
          _this.positions=resp.data.positions;
        })
      },
      showAddEmpView() {

      },
      emptyEmpData() {

      }
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
