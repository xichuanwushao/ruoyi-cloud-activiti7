<template>
  <div>
    <div>
    <h2>申请人：{{form.createName}}</h2>
    <el-form  label-width="80px">
      <el-form-item label="报销类型" >
       <el-input v-model="form.anleihen"/>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.title"/>
      </el-form-item>
      <el-form-item label="原因" >
        <el-input v-model="form.reason" />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-input v-model="form.reimStartTime"/>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-input v-model="form.reimEndTime"/>
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
    }

  }
</script>

<style scoped>

</style>
