package ncu.zss.rbs.mq.rabbitmq;

import java.io.IOException;
import java.lang.ref.WeakReference;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;

import com.rabbitmq.client.Channel;

import ncu.zss.rbs.mq.MQService.MessageListener;

/**
 * Abstract RabbitMQ message listener.
 * 
 * @author Zhou Shengsheng
 * 
 */
public abstract class RabbitMQMessageListener implements MessageListener, ChannelAwareMessageListener {
	/**
	 * Received message.
	 */
	private Message message;
	
	/**
	 * Channel related the received message.
	 */
	private Channel channel;
	
	/**
	 * Bind controller weak reference.
	 */
	private WeakReference<MessageListenerContainer> weakController;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		this.message = message;
		this.channel = channel;
		onReceivedMessage(message.getBody());
	}
	
	@Override
	public void ack() throws IOException {
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@Override
	public void reject(boolean requeue) throws IOException {
		channel.basicReject(message.getMessageProperties().getDeliveryTag(), requeue);
	}
	
	public void setController(MessageListenerContainer controller) {
		weakController = new WeakReference<MessageListenerContainer>(controller);
	}

	@Override
	public Object getController() {
		if (weakController == null) {
			return null;
		}
		return weakController.get();
	}
}
