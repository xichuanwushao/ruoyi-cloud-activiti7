<!--
 * @Author: sdf iamsdf@163.com
 * @Date: 2022-06-07 00:39:16
 * @LastEditors: sdf iamsdf@163.com
 * @LastEditTime: 2022-06-07 00:41:42
 * @FilePath: \essx2\src\components\ErrorMessage\404.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
	<div class="not-container">
		<img src="@/assets/images/404.png" class="not-img" alt="404" />
		<div class="not-detail">
			<h2>404</h2>
			<h4>抱歉，您访问的页面不存在~🤷‍♂️🤷‍♀️</h4>
			<!-- <el-button type="primary" @click="tabStore.goHome()">返回首页</el-button> -->
		</div>
	</div>
</template>

<script setup lang="ts">
// import { TabsStore } from "@/store/modules/tabs";
// const tabStore = TabsStore();
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
