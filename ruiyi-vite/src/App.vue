<template>
  <router-view />
</template>

<script setup> 
import { onMounted } from 'vue';
import useSettingsStore from '@/store/modules/settings'
import { handleThemeStyle } from '@/utils/theme'

onMounted(() => {
  nextTick(() => {
    handleThemeStyle(useSettingsStore().theme)
  })
})

</script> 