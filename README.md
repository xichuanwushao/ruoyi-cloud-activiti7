
## 平台简介
* 把danny同学前后端分离的工作流项目,修改为若依微服务版本3.5.0, 众所周知,activiti的新特性必须结合Spring Security
  来使用, 如果系统未使用Security的时候如何使用新特性呢？ 这个项目主要为了解决若依微服务未使用SpringSecrity的情况下
  ,如何使用Activiti7的新特性。这里采用了模拟登录SpringSecrity方式进行activiti功能调用
#### 友情链接
* 感谢若依大佬开源的微服务项目:https://gitee.com/y_project/RuoYi-Cloud
* 感谢danny同学开源的工作流项目:https://gitee.com/smell2/ruoyi-vue-activiti
## 内置功能
* 1.  我的OA：上传请假申请。
* 2.  待办任务：领导进行审批。
* 3.  历史流程：HR审批和历史流程查看。
## 在线体验
* http://152.136.153.155:9091/
* 测试账号
* 流程管理账号：admin / admin123
* 请假流程测试账号
* 普通员工1：guanxing / 123456
* 普通员工2：zhoucang / 123456
* 部门领导1：guanyu / 123456
* 部门领导2：zhaoyun / 123456
* 人事：zhugeliang / 123456
## 演示图
<table>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/his_list2.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/oa_list.png"/></td>
    </tr>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/task_list2.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/task_approvel.png"/></td>
    </tr>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/task_approvel3.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/his_info.png"/></td>
    </tr>
</table>
## ruoyi-cloud-activiti7交流群
* QQ群：852160973