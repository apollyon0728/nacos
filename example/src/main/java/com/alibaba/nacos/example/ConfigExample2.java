package com.alibaba.nacos.example;

import java.util.Properties;
import java.util.concurrent.Executor;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * Config service example
 *
 * 官方例子中可以通过“示例代码”查看
 * http://console.nacos.io/nacos/index.html#/configurationManagement?dataId=&group=&appName=&namespace=&namespaceShowName=public&pageSize=&pageNo=
 *
 * @author Nacos
 *
 */
public class ConfigExample2 {

    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "localhost";
        String dataId = "test2";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        // FIXME 发布配置
        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println("ConfigExample2 >>>>>>>>>>>>>>>>" + content);

        // FIXME 删除配置
//        boolean isRemoveOk = configService.removeConfig(dataId, group);
//        System.out.println(isRemoveOk);
//        Thread.sleep(3000);
//
//        content = configService.getConfig(dataId, group, 5000);
//        System.out.println(content);
//        Thread.sleep(300000);

    }
}