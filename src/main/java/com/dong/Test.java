package com.dong;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

    public static void main(String[] args) {
        TransportClient transportClient = null;
        try{
            Settings settings = Settings.builder()
//                    .put("cluster.name", "utan-es") //设置ES实例的名称
                    .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                    .build();
            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
            XContentBuilder builder = jsonBuilder().startObject()
                    .field("user", "Peter")
                    .field("age", 20)
                    .endObject();
            IndexResponse response = transportClient.prepareIndex("author", "book", "1")
                    .setSource(builder).get();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(transportClient != null){
                transportClient.close();
            }
        }
    }
}
