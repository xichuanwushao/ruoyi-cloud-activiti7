
## 平台简介
*基RuoYi-cloud3.5.0 + activiti7 的工作流管理 ~

记得右上角点个 star 持续关注更新哟~~
* <img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/star.png"/>
## 内置功能
* 1.  我的OA：报销申请,编写报销单，下载pdf报销单,上传附件(下载后签字)。
* 2.  待办任务：查看pdf报销单,领导进行审批。
* 3.  历史流程：查看pdf报销单,财务审批和历史流程查看。
  
* 1.  我的OA：上传请假申请。
* 2.  待办任务：领导进行审批。
* 3.  历史流程：HR审批和历史流程查看。
  
* 1.  流程定义：可以在线设计和上传部署查看导出流程。
## 在线体验
* http://152.136.153.155:9091/
* 测试账号
* 流程管理账号：admin / admin123(可以绘制流程,测试流程请使用测试账号)
* 请假流程测试账号
* 普通员工1：guanxing / 123456
* 普通员工2：zhoucang / 123456
* 部门领导1：guanyu / 123456
* 部门领导2：zhaoyun / 123456
* 人事：zhugeliang / 123456
* (机器资源小,流程绘制导入按钮不支持线上体验)
## vue2版本演示图（运行ruoyi-ui项目 若依微服务原生版本）
<table>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/baoxiao01.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/baoxiao-02.png"/></td>
    </tr>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/baoxiao03.png"/></td>
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
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/oa_pic1.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/oa_pic02.png"/></td>
    </tr>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/dingyi-caidan.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/dingyi-shangchuan.png"/></td>
    </tr>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/dingyi-bushu.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/dingyi-huizhi.png"/></td>
    </tr>
</table>

## vue3版本演示图（还在开发中 运行ruiyi-vite项目 由SDF同学进行维护）
<table>
    <tr>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/vue301.png"/></td>
        <td><img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/vue302.png"/></td>
    </tr>
</table>

## QQ交流群
* QQ交流群：852160973(加群请先star项目 不然验证不通过 备注格式：gitee用户名)
* <img src="https://image-1304556315.cos.ap-beijing.myqcloud.com/star.png"/>
* 点击链接加入群聊【ruoyi-cloud-activiti7交流群】：https://jq.qq.com/?_wv=1027&k=gElPi0vw
## 启动说明
需要对以下模块进行启动 启动顺序随意
* RuoYiAuthApplication
* RuoYiSystemApplication
* RuoYiGatewayApplication
* RuoYiDannyApplication( 工作流模块 )
* RuoYiFileApplication( 文件服务 新建流程图导入按钮使用 )
#### 友情链接
* 感谢若依大佬开源的微服务项目:https://gitee.com/y_project/RuoYi-Cloud
* 感谢danny同学开源的工作流项目:https://gitee.com/smell2/ruoyi-vue-activiti
#### 插件版本
* RuoYi-Cloud 3.5.0
* Nacos 2.0.2
* Node.js 14.0.0
* npm 6.14.4
#### 问题难点
* 众所周知,activiti7的新特性必须结合Spring Security来使用, 如果系统未使用Security的时候如何使用新特性呢？
  这个项目主要为了解决若依微服务未使用SpringSecrity的情况下 ,如何使用Activiti7的新特性。这里采用了模拟登录
  SpringSecrity方式进行activiti功能调用,把danny同学前后端分离的工作流项目,修改为若依微服务版本3.5.0。
#### 接口文档及调试
* 为了方遍大家调试 楼主已经整理出了流程定义审批查看主要的接口
  调试使用postman进行,打开postman,点击导入，选择ruoyi-cloud-activiti7.postman_collection2.0.json文件
  即可 
* 详细操作见
* https://gitee.com/avatarwx/ruoyi-cloud-activiti7/wikis/postman
#### 常见问题及报错解决
* 常见问题及报错解决见
* https://gitee.com/avatarwx/ruoyi-cloud-activiti7/wikis/Home