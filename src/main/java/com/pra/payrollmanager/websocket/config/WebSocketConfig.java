package com.pra.payrollmanager.websocket.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.pra.payrollmanager.websocket.security.AppHandshakeHandler;
import com.pra.payrollmanager.websocket.security.AppHandshakeInterceptor;
import com.pra.payrollmanager.websocket.security.AppInChannelInterceptor;
import com.pra.payrollmanager.websocket.security.AppWebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	public static final String WS_ENDPOINT = "/stomp";
	public static final String WS_ENDPOINT_SOCKJS_INFO = "/stomp/info";
	public static final String TOPIC_PREFIX = "/topic";
	public static final String DIRECT_USER_PREFIX = "/user";
	public static final String APP_PREFIX = "/app";

	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	@Autowired
	private AppInChannelInterceptor appChannelInterceptor;

	@Autowired
	private AppWebSocketHandlerDecoratorFactory appWebSocketHandlerDecoratorFactory;

	@Autowired
	private AppHandshakeInterceptor appHandshakeInterceptor;

	@Autowired
	AppHandshakeHandler assignPrincipalHandshakeHandler;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(WS_ENDPOINT)
				.setHandshakeHandler(assignPrincipalHandshakeHandler)
				.addInterceptors(appHandshakeInterceptor)
				.setAllowedOrigins("*");
//				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes(APP_PREFIX);
		config.enableSimpleBroker(TOPIC_PREFIX, DIRECT_USER_PREFIX)
				.setTaskScheduler(new DefaultManagedTaskScheduler())
				.setHeartbeatValue(new long[] { 45000, 45000 });
		// , "/queue"
	}

	@Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(appWebSocketHandlerDecoratorFactory);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(appChannelInterceptor);
	}

}