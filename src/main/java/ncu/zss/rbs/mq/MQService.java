package ncu.zss.rbs.mq;

import org.springframework.stereotype.Service;

/**
 * Generic MQService interface. This interface defines basic message queue actions.
 * 
 * @author Zhou Shengsheng
 * 
 */
@Service
public interface MQService {
	
	/**
	 * Send a message to the given exchange using the given routing key.
	 * @param exchange Exchange to send to.
	 * @param routingKey Routing key to rout.
	 * @param message Message to be sent.
	 * @throws Exception
	 */
	void send(String exchange, String routingKey, Object message) throws Exception;
	
	/**
	 * Bind message listener to given queues.
	 * @param messageListener Message listener to handle the new message.
	 * @param queues Queues to bind the listener.
	 * @return The controller for managing the binding. You may use this controller to cancel binding later.
	 * @throws Exception
	 */
	Object bindListener(MessageListener messageListener, String... queues) throws Exception;
	
	/**
	 * Unbind message listener.
	 * @param controller The returned value of {@link bindMessageListener}.
	 * @see bindMessageListener
	 */
	void unbindListener(Object controller);
	
	/**
	 * MessageListener Message listener to handle the new message.
	 */
	public interface MessageListener {
		/**
		 * The callback on received message.
		 * @param message Received message.
		 */
		void onReceivedMessage(Object message);
		
		/**
		 * Man a manual acknowledgment.
		 * @throws Exception
		 */
		void ack() throws Exception;
		
		/**
		 * Reject the message.
		 * @param requeue True to indicate broker to requeue the message. False to discard the message.
		 * @throws Exception
		 */
		void reject(boolean requeue) throws Exception;
		
		/**
		 * Get the bind controller.
		 */
		Object getController();
	}
	
}