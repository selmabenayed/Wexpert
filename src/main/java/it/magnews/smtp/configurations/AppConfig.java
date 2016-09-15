package it.magnews.smtp.configurations;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
/**
 *
 * @author sbenayed
 */


@Configuration
@ComponentScan("it.magnews.smtp")
@Import({DatabaseConfiguration.class})
@ImportResource("classpath:/spring-security.xml")

public class AppConfig {}
