package top.content360.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = { "top.content360.conf.*" })
@PropertySource("classpath:system_conf.properties") 
public class Confs {
	
	@Autowired
	private Environment env;
	
	public static final String APPID = "wechat.appid";
	public static final String APPSECRET = "wechat.appsecret";
	public static final String TOKEN = "wechat.token";
	
	public static final String CREATE_MENU_URL = "wechat.create_menu_url";
	public static final String ACCESS_TOKEN_URL = "wechat.access_token_url";
	
	@Bean
	public String load(String key){
		return env.getProperty(key);
	}
}
