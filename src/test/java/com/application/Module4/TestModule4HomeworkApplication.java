package com.application.Module4;

import com.application.Module4HomeworkApplication;
import org.springframework.boot.SpringApplication;

public class TestModule4HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.from(Module4HomeworkApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
