<template>
  <div>
    <div>
    <h2>申请人：{{form.createName}}</h2>
    <el-form  label-width="80px">
      <el-form-item label="报销金额" >
       <el-input v-model="form.amount"/>
      </el-form-item>
      <el-form-item label="借款金额">
        <el-input v-model="form.anleihen"/>
      </el-form-item>
      <el-form-item label="实际金额" >
        <el-input v-model="form.balance" />
      </el-form-item>
      <el-form-item label="申请时间" >
        <el-input v-model="form.createTime" />
      </el-form-item>
      <el-form-item label="查看附件" >
<!--        <el-input v-model="form.attachmentLink" />-->
        <template slot-scope="scope">
          <a @click="preView(form.attachmentLink)" target="_blank" class="buttonText" style="color: #00afff;text-decoration:underline">附件</a>
        </template>
      </el-form-item>
    </el-form>
    </div>
    <div  v-for="(historyData, index) in fromData"
          :key="index" >
      <h2>{{historyData.taskNodeName}}</h2>
      <h3>审批人:{{historyData.createName}}</h3>
      <h3>审批时间:{{historyData.createdDate}}</h3>
      <el-form v-for="(fistoryFormData, indexH) in historyData.formHistoryDataDTO" :key="indexH" label-width="80px">
        <el-form-item :label=fistoryFormData.title >
          <el-input v-model="fistoryFormData.value"/>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { getReim } from '@/api/workflow/reim'
  import { historyFromData } from '@/api/activiti/historyFormdata'
  export default {
    name: "reimHistoryForm",
    props: {
      businessKey: {
        type: String
      }
    },
    data(){
      return{
        // 表单参数
        form: {},
        fromData:[],
      }
    },
    created() {
      this.getReim()
      this.historyFromData()
    },
    methods:{
      getReim() {
        getReim(this.businessKey).then(response => {
          this.form = response.data
        })
      },
      historyFromData() {
        historyFromData(this.businessKey).then(response => {
          this.fromData = response.data
        })
      },

      /**文档预览**/
      preView(filePath){
        console.info(filePath);
        if (null == filePath || '' == filePath) {
          this.$alert('申请人没有上传附件哦', '消息提示', {
            confirmButtonText: '确定',
          });
        }else {
          // let originUrl = 'http://127.0.0.1:80/ruoyi-admin/';   //要预览文件的访问地址'
          window.open(filePath);
        }
        // const bb = originUrl  + aa;
        // window.open('http://127.0.0.1:8012/onlinePreview?url='+encodeURIComponent(Base64.encode(bb)));
      },
    }

  }
</script>

<style scoped>

</style>
