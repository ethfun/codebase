package com.inglc.codebase.config.rabbitmq;

public enum RabbitMQEnum {

    INDEX_GOODS_ON_QUEUE("queue.index.goods.on", "商品上架刷索引队列"),
    INDEX_GOODS_OFF_QUEUE("queue.index.goods.off", "商品下架刷索引队列"),
    INDEX_GOODS_PRICE_QUEUE("queue.index.goods.price", "商品价格修改刷索引队列"),
    INDEX_GOODS_ISHIDDEN_QUEUE("queue.index.goods.ishidden", "商品是否隐藏刷索引队列"),
    INDEX_GOODS_INFO_QUEUE("queue.index.goods.info", "商品属性修改刷索引队列"),

    INDEX_GOODS_ON_ROUTING_KEY("index.goods.on", "商品上架路由key"),
    INDEX_GOODS_OFF_ROUTING_KEY("index.goods.off", "商品下架路由key"),
    INDEX_GOODS_ISHIDDEN_ROUTING_KEY("index.goods.ishidden", "商品是否隐藏路由key"),
    INDEX_GOODS_PRICE_ROUTING_KEY("index.goods.price", "商品价格变动路由key"),
    INDEX_GOODS_INFO_ROUTING_KEY("index.goods.info", "商品属性修改路由key"),

    OLD_INDEX_GOODS_DELTA_QUEUE("updateGoodsIndexerQueue", "老的商品信息变动刷索引队列"),

    INDEX_GOODS_TOPIC_EXCHANGE("exchange.topic.index.goods", "商品修改刷索引交换机"),


    INDEX_USER_EXCHANGE("exchange.index.user", "用户修改刷索引交换机"),

    INDEX_USER_INFO_QUEUE("queue.index.user.info", "用户信息刷索引队列"),

    INDEX_USER_INFO_ROUTING_KEY("index.user.info", "用户刷索引路由key");


    private String name;
    private String desc;

    RabbitMQEnum(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
