package com.mxs.mxsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.mxs.mxsserver.server.HttpServer;
import com.mxs.mxsserver.server.NioServer;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//去除默认的mongoDB连接
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MxsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MxsServerApplication.class, args);
//		NioServer nioServer = 
				new NioServer();
//		HttpServer httpServer = 
				new HttpServer();
	}
}
