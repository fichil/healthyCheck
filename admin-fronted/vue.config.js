const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 修改默认端口号
  devServer: {
    port: 8081,
    // 代理转发
    proxy: {
      '/api': {
        target: 'http://localhost:8080/api/',
        pathRewrite: { '^/api': '' }
      }
    }
  }
})

