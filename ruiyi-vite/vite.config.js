import {
  defineConfig,
  loadEnv
} from 'vite'
// import vue from '@vitejs/plugin-vue'
import path from 'path'
import createVitePlugins from './vite/plugins/index'
// 自动导入import
// import AutoImport from 'unplugin-auto-import/vite'
// https://vitejs.dev/config/
export default defineConfig(({
  mode,
  command
}) => {
  // 区分开发生产环境
  const env = loadEnv(mode, process.cwd())
  const {
    VITE_APP_ENV
  } = env
  // eslint报错解决
  return {
    base: VITE_APP_ENV === 'production' ? '/' : '/',
    eslintrc: {
      enabled: false, // Default `false`
      filepath: './.eslintrc-auto-import.json', // Default `./.eslintrc-auto-import.json`
      globalsPropValue: true, // Default `true`, (true | false | 'readonly' | 'readable' | 'writable' | 'writeable')
    },
    plugins: createVitePlugins(env, command === 'build'),
    // plugins: [
    //   vue(), //不写入vue就会报错
    //   AutoImport({
    //     imports: ["vue", "vue-router"], // 自动导入vue和vue-router相关函数
    //     dts: "src/auto-import.d.ts" // 生成 `auto-import.d.ts` 全局声明
    //   })

    // ],
    resolve: {
      // https://cn.vitejs.dev/config/#resolve-alias
      alias: {
        // 设置路径
        '~': path.resolve(__dirname, './'),
        // 设置别名
        '@': path.resolve(__dirname, './src')
      },
      // https://cn.vitejs.dev/config/#resolve-extensions
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
    },
    server: {
      port: 8888,
      host: true,
      // open: true,
      proxy: {
        // https://cn.vitejs.dev/config/#server-proxy
        '/dev-api': {
          target: 'http://localhost:8087',
          changeOrigin: true,
          rewrite: (p) => p.replace(/^\/dev-api/, '')
        }
      }
    },
  }
})