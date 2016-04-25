package ncu.zss.rbs.mq.rabbitmq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Singleton to retrieve application context related to rabbitmq.
 * 
 * @author Zhou Shengsheng
 * 
 */
final public class RabbitMQContextUtil {
	/**
	 * Rabbitmq context resource path. 
	 */
	private final static String rabbitmqContextResourcePath = "conf/rabbitmq-config.xml";
	/**
	 * Singleton rabbitmq context.
	 */
	private static ApplicationContext rabbitMQContext;
	
	private RabbitMQContextUtil() {
	}
	
	/**
	 * Init rabbitMQContext when the class is being loaded.
	 */
	static {
		rabbitMQContext = new FileSystemXmlApplicationContext(
				"file:" + RabbitMQContextUtil.class.getClassLoader().getResource(rabbitmqContextResourcePath).getFile());
	}
	
	/**
	 * Get the singleton rabbitmq context.
	 * @return Singleton rabbitmq context.
	 */
	public static ApplicationContext getRabbitMQContext() {
		return rabbitMQContext;
	}
}
