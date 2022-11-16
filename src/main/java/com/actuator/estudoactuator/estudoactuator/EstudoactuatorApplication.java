package com.actuator.estudoactuator.estudoactuator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class EstudoactuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudoactuatorApplication.class, args);
	}

}
