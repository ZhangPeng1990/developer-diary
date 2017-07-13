package top.content360.conf;

public class Constants {

	/**
	 * 敏感数据
	 */
	public static final String APPID = "";
	public static final String APPSECRET = "";
	public static final String TOKEN = "";
	
	/**
	 * 非敏感数据
	 */
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
}
