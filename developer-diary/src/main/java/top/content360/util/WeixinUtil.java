package top.content360.util;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import top.content360.conf.Constants;
import top.content360.menu.Button;
import top.content360.menu.ClickButton;
import top.content360.menu.Menu;
import top.content360.menu.ViewButton;
import top.content360.po.AccessToken;
public class WeixinUtil {

	/**
	 *获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = Constants.ACCESS_TOKEN_URL.replace("APPID", Constants.APPID).replace("APPSECRET", Constants.APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		
		return token;
	}
	
	public static JSONObject doGetStr(String url){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public static JSONObject dopostStr(String url, String outStr){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 初始化微信菜单
	 * @return
	 */
	public static Menu initMenu(){
		Log.log("begin init Menu");
		
		Menu menu = new Menu();
		
		ClickButton button1 = new ClickButton();
		button1.setName("翻日记");
		button1.setType("click");
		button1.setKey("11111");
		
		ViewButton button2 = new ViewButton();
		button2.setName("写日记");
		button2.setType("view");
		button2.setUrl("http://www.baidu.com");
		
		Button button3 = new Button();
		button3.setName("关于");
		button1.setKey("3333");
		
		ClickButton button3_1 = new ClickButton();
		button3_1.setName("联系我们");
		button3_1.setKey("3111");
		button3_1.setType("scancode_push");
		
		ClickButton button3_2 = new ClickButton();
		button3_2.setName("关于我们");
		button3_2.setKey("3111");
		button3_2.setType("scancode_push");
		
		button3.setSub_button(new Button[]{button3_1, button3_2});
		
		menu.setButton(new Button[]{button1, button2, button3});
		return menu;
	}
	
	public static int createMenu(String token, String menu){
		int result = 1;
		String url = Constants.CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = dopostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
