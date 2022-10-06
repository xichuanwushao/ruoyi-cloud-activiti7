/*
 * @Author: sdf iamsdf@163.com
 * @Date: 2022-10-06 01:34:14
 * @LastEditors: sdf iamsdf@163.com
 * @LastEditTime: 2022-10-06 03:15:55
 * @FilePath: \ryvite\src\main.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { createApp } from 'vue'
import App from './App.vue'
// element-plus依赖
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/lib/locale/lang/zh-cn' // 中文语言

import '@/assets/styles/index.scss' // global css
// pinia store
import pinia from "@/store/index";
// vue Router
import router from "@/router/index";
// 
import directive from './directive' // directive
// 图标
import 'virtual:svg-icons-register'
import SvgIcon from '@/components/SvgIcon'
import elementIcons from '@/components/SvgIcon/svgicon'
// 分页组件
import Pagination from '@/components/Pagination'
// 自定义表格工具组件
import RightToolbar from '@/components/RightToolbar'
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 自定义树选择组件
import TreeSelect from '@/components/TreeSelect'
// 字典标签组件
import DictTag from '@/components/DictTag'
import './permission' // permission control

// 注册指令
import plugins from './plugins' // plugins
import { download } from '@/utils/request'
import { useDict } from '@/utils/dict'
import { parseTime, resetForm, addDateRange, handleTree, sselectDictLabelV2,selectDictLabel, selectDictLabels } from '@/utils/ruoyi'



const app = createApp(App)
app.use(plugins)
// 全局方法挂载
app.config.globalProperties.useDict = useDict
app.config.globalProperties.download = download
app.config.globalProperties.parseTime = parseTime
app.config.globalProperties.resetForm = resetForm
app.config.globalProperties.handleTree = handleTree
app.config.globalProperties.addDateRange = addDateRange
app.config.globalProperties.selectDictLabel = selectDictLabel
app.config.globalProperties.selectDictLabels = selectDictLabels
app.config.globalProperties.sselectDictLabelV2 = sselectDictLabelV2

// 全局组件挂载
app.component('DictTag', DictTag)
app.component('Pagination', Pagination)
app.component('TreeSelect', TreeSelect)
app.component('FileUpload', FileUpload)
app.component('ImageUpload', ImageUpload)
app.component('ImagePreview', ImagePreview)
app.component('RightToolbar', RightToolbar)

app.use(ElementPlus)
.use(pinia)
.use(router)
.use(elementIcons)
.component('svg-icon', SvgIcon)
directive(app)
app.mount('#app')

