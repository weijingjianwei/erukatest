<template>
  <div id="app">
    <Row :gutter="16" style="background:#eee;padding:10%">
      <i-col span="16">
        <Card style="height: 300px">
          <p slot="title">
            <Icon type="ios-cloud-upload"></Icon>
            本地上传
          </p>
          <div style="text-align: center;">
            <Upload
              :before-upload="handleLocalUpload"
              action="/upload/local"
              ref="localUploadRef"
              :on-success="handleLocalSuccess"
              :on-error="handleLocalError"
            >
              <i-button icon="ios-cloud-upload-outline">选择文件</i-button>
            </Upload>
            <i-button
              type="primary"
              @click="localUpload"
              :loading="local.loadingStatus"
              :disabled="!local.file">
              {{ local.loadingStatus ? '本地文件上传中' : '本地上传' }}
            </i-button>
          </div>
          <div>
            <div v-if="local.log.status != 0">状态：{{local.log.message}}</div>
            <div v-if="local.log.status === 200">文件名：{{local.log.fileName}}</div>
            <div v-if="local.log.status === 200">文件路径：{{local.log.filePath}}</div>
          </div>
        </Card>
      </i-col>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
      return {
        local:
          {
          file: null,
          // 标记上传状态
          loadingStatus: false,
          log: {
          status: 0,
            message: "",
            fileName: "",
            filePath: ""
          }
        }
    };
  },
  methods: {
    handleLocalUpload(file) {
      this.local.file = file;
      return false;
    },

    localUpload() {
      this.local.loadingStatus = true;  // 标记上传状态
      this.$refs.localUploadRef.post(this.local.file);
    },

    handleLocalSuccess(response) {
      this.local.file = null;
      this.local.loadingStatus = false;
      if (response.code === 200) {
        this.$Message.success(response.message);
        this.local.log.status = response.code;
        this.local.log.message = response.message;
        this.local.log.fileName = response.data.fileName;
        this.local.log.filePath = response.data.filePath;
        this.$refs.localUploadRef.clearFiles();
      } else {
        this.$Message.error(response.message);
        this.local.log.status = response.code;
        this.local.log.message = response.message;
      }
    },

    handleLocalError() {
      this.local.file = null;
      this.local.loadingStatus = false;
      this.$Message.error('上传失败');
    },
    exportEmps(){
      this.getRequest("/test").then(resp => {

      })
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
