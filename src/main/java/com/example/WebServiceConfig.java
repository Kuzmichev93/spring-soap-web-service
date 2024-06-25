package com.example;

import com.data.Repository;
import localhost._8080.Information;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.apache.log4j.Logger;


import java.sql.SQLException;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	public static String DB_URL;
	public static String USER;
	public static String PASS;
	public final static Logger logger = Logger.getLogger( WebServiceConfig.class );

	@Bean
	public Repository startbd() throws SQLException {
		DB_URL = "jdbc:postgresql://localhost:5433/postgres";
		USER = "postgres";
		PASS = "12345";
		return new Repository(DB_URL,USER,PASS);
	}


	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		System.out.println(servlet.getMessageReceiverHandlerAdapterBeanName());
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean(name = "people")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PeoplePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://localhost:8080");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;

	}

	@Bean
	public Information information(){
		return new Information();
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("people.xsd"));
	}

	/*@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		LogHttpHeaderEndpointInterceptor validatingInterceptor = new LogHttpHeaderEndpointInterceptor();
		validatingInterceptor.setValidateRequest(true);
		validatingInterceptor.setValidateResponse(true);
		validatingInterceptor.setAddValidationErrorDetail(true);
		validatingInterceptor.setXsdSchema(countriesSchema());
		interceptors.add(validatingInterceptor);
	}

	public LogHttpHeaderEndpointInterceptor customSmartEndpointInterceptor() {
		LogHttpHeaderEndpointInterceptor customSmartEndpointInterceptor = new LogHttpHeaderEndpointInterceptor();
		//customSmartEndpointInterceptor.setValidationActions("UsernameToken");
		customSmartEndpointInterceptor.setMessageFactory(messageFactory());
		//customSmartEndpointInterceptor.setValidationCallbackHandler(new Wss4jSecurityCallbackImpl(login, pwd));

		return customSmartEndpointInterceptor;
	}*/
}
