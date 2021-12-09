package com.hlrconsult.apiemail.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${spring.rabbitmq.mailqueue}")
	private String mailQueue;

	@Value("${spring.rabbitmq.deadqueue}")
	private String deadQueue;

	private final static String MAIL_EXCHANGE = "mail.exchange";

	private final static String DEAD_LETTER_EXCHANGE = "dead.letter.mail.exchange";

	public static Map<String, Object> args = new HashMap<String, Object>();

	static {
		args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
	}

	@Bean
	public TopicExchange emailExchange() {
		return new TopicExchange(MAIL_EXCHANGE, true, false);
	}

	@Bean
	public Binding mailExchangeBinding(Queue mailQueue) {
		return BindingBuilder.bind(mailQueue).to(emailExchange()).with("*");
	}

	@Bean
	public Queue mailQueue() {
		return new Queue(mailQueue, true, false, false, args);
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue(deadQueue, true);
	}

	@Bean
	public FanoutExchange deadLetterExchange() {
		return new FanoutExchange(DEAD_LETTER_EXCHANGE);
	}

	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
