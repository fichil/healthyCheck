const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 修改默认端口号
  devServer: {
    port: 8083,
    // 代理转发
    proxy: {
      '/api': {
        target: 'http://localhost:8082/api/',
        pathRewrite: { '^/api': '' }
      }
    }
  }
})
