package com.application;

import com.application.consoleApplication.ConsoleDatabaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Module4HomeworkApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Module4HomeworkApplication.class, args);

		ConsoleDatabaseApplication application= context.getBean(ConsoleDatabaseApplication.class);
		application.run();

	}

}
