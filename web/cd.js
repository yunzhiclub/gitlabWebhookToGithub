// 测试方法：
// 1. 定义环境变量CI_PROJECT_NAME
// 1. 定义 IS_RSA为登录服务器的私钥
// 1. 定义服务器地址SERVER_HOST
// 1. 在本地打包好
// 1. 执行node cd.js 进行测试
// 引入第三方模块
const fs = require('fs')
const path = require('path')
const {NodeSSH} = require('node-ssh')

// 初始化变量
const projectName = process.env.CI_PROJECT_NAME; // 由环境变量获取的工程名称
const idRsaPath = process.env.ID_RSA;            // RSA私钥
const container = `${projectName}-web`;          // 自定义容器名称
const appDir = `/home/app/${projectName}`;       // 应用根位置
const rootWebDir = `${appDir}/web`;              // web应用位置
const host = process.env.SERVER_HOST ? process.env.SERVER_HOST : '192.168.1.17';
const network = `${projectName}-network`         // 网络
const reCreateContainer = true;                  // 是否重新创建容器

// 定义开始函数，在文件结尾调用
const start = async function() {
  try {
    console.log('尝试连接生产服务器');
    const ssh = new NodeSSH();
    await ssh.connect({
      host: `${host}`,
      port: '17022',
      username: 'root',
      privateKeyPath: `${idRsaPath}`
    });

    console.log('服务器连接成功，尝试停止原容器');
    let status = await ssh.execCommand(`docker ps -q -f name=${container}`);
    if (status.stdout !== '') {
      await ssh.execCommand(`docker stop ${status.stdout}`);
    }

    if (reCreateContainer) {
      console.log('尝试删除原容器');
      await ssh.execCommand(`docker container rm ${container}`);
    }

    console.log("查看是否存在网络，没有则创建");
    status = await ssh.execCommand(`docker network ls -q -f name=${network}`);
    if (status.stdout === '') {
      await ssh.execCommand(`docker network create ${network}`);
    }

    console.log('开始上传文件夹及配置文件');
    await ssh.execCommand(`mkdir -p ${rootWebDir}`);
    await ssh.execCommand(`rm -rf ${rootWebDir}`);
    await ssh.putDirectory(`${__dirname}/dist/web`,
     `${rootWebDir}`, {
       recursive: true,
       concurrency: 10
    });
    await ssh.putFile(`${__dirname}/nginx.conf`,
     `${appDir}/nginx.conf`);

    console.log(`查看是否存在容器，不存在开始构建`);
    status = await ssh.execCommand(`docker ps -a -q -f name=${container}`);
    if (status.stdout === '') {
      const dockerCommand = `docker create -p 17080:80 --cpus=1 --memory=1G --name=${container}`
        + ` -v ${rootWebDir}:/usr/share/nginx/html:ro` 
        + ` -v ${appDir}/nginx.conf:/etc/nginx/conf.d/default.conf:ro`
        + ` --network=${network}`
        + ` nginx`;
      console.log(dockerCommand);
      status = await ssh.execCommand(dockerCommand);
      if (status.code === 0) {
          console.log('构建容器完成');
      } else {
          console.error('构建容器发生错误');
          throw new Error(status);
      }
    }

    console.log('(重新)启动容器');
    await ssh.execCommand(`docker start ${container}`);
   
    // 以下代码用于查看启动的容器详细配置，当容器的表现与我们的预期不一致时可以启用
    // status = await ssh.execCommand(`docker inspect ${container}`);
    // console.log(status);
  } catch(e) {
    console.log('自动构建发发生错误', e);
  } finally {
      process.exit(0);
  }
}

start().then().catch(function(error) {
  console.log('发生错误', error);
  process.exit(1);
});

