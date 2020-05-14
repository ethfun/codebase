package com.inglc.codebase.config.rabbitmq;

public enum RabbitMqEnum {

    Goods_DELTA_INDEXER_QUEUE("queue.goods.deltaIndex", "商品信息刷索引队列"),
    USER_DELTA_INDEXER_QUEUE("queue.user.deltaIndex", "用户信息刷索引队列"),

    DELTA_INDEX_EXCHANGE("exchange.index.delta", "增量刷索引交换机"),

    GOODS_DELTA_INDEX_ROUTING_KEY("goods.index.delta", "商品刷索引路由key"),
    USER_DELTA_INDEX_ROUTING_KEY("user.index.delta", "用户刷索引路由key");


    private String name;
    private String desc;

    RabbitMqEnum(String name, String desc){
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
