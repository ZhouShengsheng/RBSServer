package ncu.zss.rbs.mq.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.mq.MQService;

/**
 * An implementation of MQService using RabbitMQ.
 * 
 * @author Zhou Shengsheng
 * 
 */
@Service("rabbitMQService")
public class RabbitMQService implements MQService {
	/**
	 * Spring context which contains rabbitmq staff.
	 */
	private ApplicationContext rabbitMQContext;
	
	/**
	 * Convenient rabbitmq wrapper.
	 */
	private RabbitTemplate template;
	
	public RabbitMQService() {
		rabbitMQContext = RabbitMQContextUtil.getRabbitMQContext();
		template = rabbitMQContext.getBean(RabbitTemplate.class);
	}
	
	@Override
	public void send(String exchange, String routingKey, Object message) throws AmqpException {
		template.convertAndSend(exchange, routingKey, message);
	}

	@Override
	public Object bindListener(MessageListener messageListener, String... queues) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(template.getConnectionFactory());
		container.setQueueNames(queues);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setPrefetchCount(1);
		container.setMessageListener(messageListener);
		if (messageListener instanceof RabbitMQMessageListener) {
			((RabbitMQMessageListener)messageListener).setController(container);
		}
		container.start();
		return container;
	}

	@Override
	public void unbindListener(Object controller) {
		if (controller instanceof MessageListenerContainer) {
			((MessageListenerContainer)controller).stop();
		} else {
			System.err.println("Failed to unbind! Controller must be an instance of " + MessageListenerContainer.class);
		}
	}
}
