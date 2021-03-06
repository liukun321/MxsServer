package com.mxs.mxsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mxs.mxsserver.server.HttpServer;
import com.mxs.mxsserver.server.NioServer;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//去除默认的mongoDB连接
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableScheduling
public class MxsServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MxsServerApplication.class);
//		app.setWebEnvironment(false);
//		app.addListeners(new KeepAliveListener());
		app.run(args);
//		NioServer nioServer = 
				new NioServer();
//		HttpServer httpServer = 
				new HttpServer();
	}
}
