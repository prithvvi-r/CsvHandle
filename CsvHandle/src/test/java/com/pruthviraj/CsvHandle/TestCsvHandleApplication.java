package com.pruthviraj.CsvHandle;

import org.springframework.boot.SpringApplication;

public class TestCsvHandleApplication {

	public static void main(String[] args) {
		SpringApplication.from(CsvHandleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
