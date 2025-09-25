package org.example.orderservice.service;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.model.OrderInfo;
import org.example.orderservice.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderService {
//  通过restTemplate来进行对象的关联
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
   private DiscoveryClient discoveryClient;

//  计数器
    private AtomicInteger count = new AtomicInteger(1);

    List<ServiceInstance> instances;

    @PostConstruct
    public void init(){
        //从eureka中获取服务列表
        instances = discoveryClient.getInstances("product-service");
    }

//    public OrderInfo selectOrderById(Integer orderId){
//        OrderInfo orderInfo = orderMapper.selectOrderById(orderId);
//        int index = count.getAndIncrement() % instances.size();
////      这里面是接口间的通信~~
//        String uri = instances.get(index).getUri().toString();
//        String url  = uri+"/product/"+orderInfo.getProductId();
//        log.info("url:{}",url);
//        ProductInfo template = restTemplate.getForObject(url, ProductInfo.class);
//        orderInfo.setProductInfo(template);
//        return orderInfo;
//    }


    public OrderInfo selectOrderById(Integer orderId){
        OrderInfo orderInfo = orderMapper.selectOrderById(orderId);
        String url = "http://product-service/product/"+orderInfo.getProductId();
        log.info("远程调用url:{}",url);
        ProductInfo template = restTemplate.getForObject(url, ProductInfo.class);
        orderInfo.setProductInfo(template);
        return orderInfo;
    }
}
