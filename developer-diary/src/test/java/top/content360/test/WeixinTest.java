package top.content360.test;

import net.sf.json.JSONObject;
import top.content360.po.AccessToken;
import top.content360.util.Log;
import top.content360.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args) {
		AccessToken token = WeixinUtil.getAccessToken();
		
		token.getExpires_in();
		
		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getToken(), menu);
		
		if(result == 0){
			Log.log("创建菜单成功");
		}
	}
}
