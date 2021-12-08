package com.hlrconsult.apiemail.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.DirectExchangeRoutingKeyConfigurer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	private static Map<String, Object> args = new HashMap<String, Object>();

	public final static String MAIL_QUEUE = "mail.queue";

	private final static String MAIL_EXCHANGE = "mail.exchange";

	private final static String DEAD_LETTER_QUEUE = "dead.letter.mail.queue";

	private final static String DEAD_LETTER_EXCHANGE = "dead.letter.mail.exchange";

	static {
		args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
		// args.put("x-message-ttl", 5000);
	}

	// @Value("${spring.rabbitmq.mailqueue}")
	// private String mailQueue;

	@Bean
	public Queue mailQueue() {
		return new Queue(MAIL_QUEUE, true, false, false, args);
	}

	@Bean
	public DirectExchange emailExchange() {
		return new DirectExchange(MAIL_EXCHANGE, true, false);
	}

	@Bean
	public Binding mailExchangeBinding(Queue mailQueue) {
		return BindingBuilder.bind(mailQueue).to(emailExchange()).with(mailQueue.getName());
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue(DEAD_LETTER_QUEUE, true, false, false);
	}

	@Bean
	public DirectExchange deadLetterExchange() {
		return new DirectExchange(DEAD_LETTER_EXCHANGE);
	}

	@Bean
	public DirectExchangeRoutingKeyConfigurer deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/*
	 * @Bean public RabbitListenerContainerFactory<DirectMessageListenerContainer>
	 * rabbitListenerContainerFactory( ConnectionFactory connectionFactory) {
	 * DirectRabbitListenerContainerFactory factory = new
	 * DirectRabbitListenerContainerFactory();
	 * 
	 * factory.setConnectionFactory(connectionFactory);
	 * factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
	 * factory.setPrefetchCount(4); //factory.setGlobalQos(true);
	 * //factory.setConsumersPerQueue(20);
	 * 
	 * return factory; }
	 */
}
