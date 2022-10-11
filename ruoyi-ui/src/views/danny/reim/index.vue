<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="总金额" prop="amount">-->
<!--        <el-input-->
<!--          v-model="queryParams.amount"-->
<!--          placeholder="请输入总金额"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="借款" prop="anleihen">-->
<!--        <el-input-->
<!--          v-model="queryParams.anleihen"-->
<!--          placeholder="请输入借款"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="差额" prop="balance">-->
<!--        <el-input-->
<!--          v-model="queryParams.balance"-->
<!--          placeholder="请输入差额"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.workflow_reim_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.workflow_reim_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="报销标题" prop="title">-->
<!--        <el-input-->
<!--          v-model="queryParams.title"-->
<!--          placeholder="请输入报销标题"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="开始时间">-->
<!--        <el-date-picker-->
<!--          v-model="daterangeLeaveStartTime"-->
<!--          style="width: 240px"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="结束时间">-->
<!--        <el-date-picker-->
<!--          v-model="daterangeLeaveEndTime"-->
<!--          style="width: 240px"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="流程实例id" prop="instanceId">-->
<!--        <el-input-->
<!--          v-model="queryParams.instanceId"-->
<!--          placeholder="请输入流程实例id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="创建者名称" prop="createName">-->
<!--        <el-input-->
<!--          v-model="queryParams.createName"-->
<!--          placeholder="请输入创建者名称"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="申请时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['danny:reim:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['danny:reim:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['danny:reim:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['danny:reim:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reimList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="reimId" />
      <el-table-column label="报销类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.workflow_reim_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="报销金额" align="center" prop="amount" />
      <el-table-column label="借款金额" align="center" prop="anleihen" />
      <el-table-column label="实际金额" align="center" prop="balance" />

<!--      <el-table-column label="状态" align="center" prop="status">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.workflow_reim_status" :value="scope.row.status"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.status!=0">
            <dict-tag :options="dict.type.workflow_reim_status" :value="scope.row.status"/>
          </div>
          <div v-else>
            {{scope.row.taskName}}
          </div>
        </template>
      </el-table-column>

<!--      <el-table-column label="报销标题" align="center" prop="title" />-->
<!--      <el-table-column label="报销原因" align="center" prop="reason" />-->
<!--      <el-table-column label="开始时间" align="center" prop="leaveStartTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.leaveStartTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="结束时间" align="center" prop="leaveEndTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.leaveEndTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="附件" align="center" prop="attachmentLink" />-->

      <el-table-column label="附件" align="center" prop="attachmentLink">
        <template slot-scope="scope">
          <a @click="preView(scope.row.attachmentLink)" target="_blank" class="buttonText" style="color: #00afff;text-decoration:underline">附件</a>
        </template>
      </el-table-column>

<!--      <el-table-column label="流程实例id" align="center" prop="instanceId" />-->
<!--      <el-table-column label="创建者名称" align="center" prop="createName" />-->
          <el-table-column label="申请时间" align="center" prop="createTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="pdfHandle(scope.row)">报销单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="historyFory(scope.row)"
            v-hasPermi="['danny:reim:edit']">审批详情
          </el-button>
          <el-button v-if="0==scope.row.status"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="checkTheSchedule(scope.row)" v-hasPermi="['danny:reim:edit']" >查看进度
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['danny:reim:edit']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['danny:reim:remove']"-->
<!--          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 进度查询 -->
    <el-dialog
      :visible.sync="modelVisible"
      title="进度查询"
      width="1680px"
      append-to-body
    >
      <div style="position:relative;height: 100%;">
        <iframe id="iframe" :src="modelerUrl" frameborder="0" width="100%" height="720px" scrolling="auto"></iframe>
      </div>
    </el-dialog>
    <!-- 查看详细信息话框 -->
    <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
      <reimHistoryForm :businessKey="businessKey" v-if="open2" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="open2=!open2">关闭</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改报销申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
            v-for="dict in dict.type.workflow_reim_type"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="总金额" prop="amount">-->
<!--          <el-input v-model="form.amount" placeholder="请输入总金额" />-->
<!--        </el-form-item>-->
        <el-form-item label="借款金额" prop="anleihen">
          <el-input v-model="form.anleihen" placeholder="请输入借款" />
        </el-form-item>
<!--        <el-form-item label="差额" prop="balance">-->
<!--          <el-input v-model="form.balance" placeholder="请输入差额" />-->
<!--        </el-form-item>-->
        <!--        <el-form-item label="状态" prop="status">-->
<!--          <el-select v-model="form.status" placeholder="请选择状态">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.workflow_reim_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="报销标题" prop="title">-->
<!--          <el-input v-model="form.title" placeholder="请输入报销标题" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="报销原因" prop="reason">-->
<!--          <el-input v-model="form.reason" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="开始时间" prop="leaveStartTime">-->
<!--          <el-date-picker clearable-->
<!--            v-model="form.leaveStartTime"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="请选择开始时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="结束时间" prop="leaveEndTime">-->
<!--          <el-date-picker clearable-->
<!--            v-model="form.leaveEndTime"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="请选择结束时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
        <el-form-item label="附件">
          <file-upload v-model="form.attachmentLink"/>
        </el-form-item>
<!--        <el-form-item label="流程实例id" prop="instanceId">-->
<!--          <el-input v-model="form.instanceId" placeholder="请输入流程实例id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="创建者名称" prop="createName">-->
<!--          <el-input v-model="form.createName" placeholder="请输入创建者名称" />-->
<!--        </el-form-item>-->
        <el-divider content-position="center">报销条目信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDanWorkflowReimgoods">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDanWorkflowReimgoods">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="danWorkflowReimgoodsList" :row-class-name="rowDanWorkflowReimgoodsIndex" @selection-change="handleDanWorkflowReimgoodsSelectionChange" ref="danWorkflowReimgoods">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="条目标题" prop="title" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.title" placeholder="请输入条目标题" />
            </template>
          </el-table-column>
          <el-table-column label="条目类型" prop="type" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.type" placeholder="请选择条目类型">
                <el-option
                  v-for="dict in dict.type.workflow_reimgoods_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="条目金额" prop="money" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.money" placeholder="请输入条目金额" />
            </template>
          </el-table-column>
          <el-table-column label="条目描述" prop="desc" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.desc" placeholder="请输入条目描述" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <reim-pdf v-if="pdfVisible" ref="pdf" @refreshDataList="loadDataList"></reim-pdf>
  </div>
</template>

<script>
import { listReim, getReim, delReim, addReim, updateReim } from "@/api/danny/reim";
import dayjs from 'dayjs';
import ReimPdf from './reim_pdf.vue';
import reimHistoryForm from "./reimHistoryForm";
export default {
  name: "Reim",
  dicts: ['workflow_reim_status', 'workflow_reimgoods_type', 'workflow_reim_type'],
  components: { ReimPdf ,reimHistoryForm},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDanWorkflowReimgoods: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报销申请表格数据
      reimList: [],
      // 报销条目表格数据
      danWorkflowReimgoodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 条目描述时间范围
      daterangeLeaveStartTime: [],
      // 条目描述时间范围
      daterangeLeaveEndTime: [],
      // 查询参数
      // 条目描述时间范围
      daterangeCreateTime: [],
      pdfVisible: false,
      businessKey: '',
      open2: false,
      modelVisible: false,
      modelerUrl: '',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        amount: null,
        anleihen: null,
        money: null,
        balance: null,
        type: null,
        status: null,
        title: null,
        reason: null,
        leaveStartTime: null,
        leaveEndTime: null,
        attachmentLink: null,
        instanceId: null,
        createName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        amount: [
          { required: true, message: "总金额不能为空", trigger: "blur" }
        ],
        anleihen: [
          { required: true, message: "借款不能为空", trigger: "blur" }
        ],
        balance: [
          { required: true, message: "差额不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        money: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报销申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLeaveStartTime && '' != this.daterangeLeaveStartTime) {
        this.queryParams.params["beginLeaveStartTime"] = this.daterangeLeaveStartTime[0];
        this.queryParams.params["endLeaveStartTime"] = this.daterangeLeaveStartTime[1];
      }
      if (null != this.daterangeLeaveEndTime && '' != this.daterangeLeaveEndTime) {
        this.queryParams.params["beginLeaveEndTime"] = this.daterangeLeaveEndTime[0];
        this.queryParams.params["endLeaveEndTime"] = this.daterangeLeaveEndTime[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listReim(this.queryParams).then(response => {
        this.reimList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reimId: null,
        amount: null,
        anleihen: null,
        balance: null,
        type: null,
        status: null,
        title: null,
        reason: null,
        leaveStartTime: null,
        leaveEndTime: null,
        attachmentLink: null,
        instanceId: null,
        createName: null,
        createBy: null,
        createTime: null,
        updateTime: null
      };
      this.danWorkflowReimgoodsList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeLeaveStartTime = [];
      this.daterangeLeaveEndTime = [];
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reimId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加报销申请";
    // },
    /** 新增按钮操作 */
    handleAdd() {
      this.createName = this.$store.getters.nickname
      if (this.$store.getters.name != "admin") {
        this.reset()
        this.open = true
        this.title = '添加报销申请'
      } else {
        this.$alert('管理员不能创建流程', '管理员不能创建流程', {
          confirmButtonText: '确定',
        });
      }

    },
    pdfHandle: function(row) {
      this.pdfVisible = true;
      this.$nextTick(() => {
        this.$refs.pdf.init(row.reimId);
      });
    },
    loadDataList: function() {
      let that = this;
      that.dataListLoading = true;
      let data = {
        name: that.dataForm.name,
        deptId: that.dataForm.deptId,
        typeId: that.dataForm.typeId,
        status: that.dataForm.status,
        page: that.pageIndex,
        length: that.pageSize
      };
      if (that.dataForm.date != null && that.dataForm.date.length == 2) {
        let startDate = that.dataForm.date[0];
        let endDate = that.dataForm.date[1];
        data.startDate = dayjs(startDate).format('YYYY-MM-DD');
        data.endDate = dayjs(endDate).format('YYYY-MM-DD');
      }
      that.$http('reim/searchReimByPage', 'POST', data, true, function(resp) {
        let page = resp.page;
        let status = {
          0: '待审批2',
          1: '已通过2',
          2: '已否决2',
        };
        let type = { 1: '普通报销', 2: '差旅报销' };
        for (let one of page.list) {
          one.status = status[one.status];
          one.type = type[one.typeId];
          one.content = JSON.parse(one.content);
        }
        that.dataList = page.list;
        that.totalCount = page.totalCount;
        that.dataListLoading = false;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reimId = row.reimId || this.ids
      getReim(reimId).then(response => {
        this.form = response.data;
        this.danWorkflowReimgoodsList = response.data.danWorkflowReimgoodsList;
        this.open = true;
        this.title = "修改报销申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.danWorkflowReimgoodsList = this.danWorkflowReimgoodsList;
          if (this.form.reimId != null) {
            updateReim(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReim(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reimIds = row.reimId || this.ids;
      this.$modal.confirm('是否确认删除报销申请编号为"' + reimIds + '"的数据项？').then(function() {
        return delReim(reimIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 报销条目序号 */
    rowDanWorkflowReimgoodsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 报销条目添加按钮操作 */
    handleAddDanWorkflowReimgoods() {
      let obj = {};
      obj.title = "";
      obj.type = "";
      obj.money = "";
      obj.desc = "";
      this.danWorkflowReimgoodsList.push(obj);
    },
    /** 报销条目删除按钮操作 */
    handleDeleteDanWorkflowReimgoods() {
      if (this.checkedDanWorkflowReimgoods.length == 0) {
        this.$modal.msgError("请先选择要删除的报销条目数据");
      } else {
        const danWorkflowReimgoodsList = this.danWorkflowReimgoodsList;
        const checkedDanWorkflowReimgoods = this.checkedDanWorkflowReimgoods;
        this.danWorkflowReimgoodsList = danWorkflowReimgoodsList.filter(function(item) {
          return checkedDanWorkflowReimgoods.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleDanWorkflowReimgoodsSelectionChange(selection) {
      this.checkedDanWorkflowReimgoods = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('danny/reim/export', {
        ...this.queryParams
      }, `reim_${new Date().getTime()}.xlsx`)
    },
    /** 审批详情 */
    historyFory(row) {
      console.log()
      this.businessKey = row.reimId
      this.open2 = true
      this.title = '审批详情'

    },
    /** 进度查看 */
    checkTheSchedule(row) {
      this.modelerUrl = row.bpmnUrl;
      this.modelVisible = true
    },
    /**文档预览**/
    preView(filePath){
      console.info(filePath);
      if (null == filePath || '' == filePath) {
        this.$alert('请上传附件后预览', '消息提示', {
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
};
</script>
