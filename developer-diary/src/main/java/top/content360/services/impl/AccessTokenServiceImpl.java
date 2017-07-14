package top.content360.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.content360.conf.Confs;
import top.content360.entities.AccessTokenPojo;
import top.content360.po.AccessToken;
import top.content360.repositorys.AccessTokenPojoRepository;
import top.content360.services.AccessTokenService;
import top.content360.util.Log;
import top.content360.util.WeixinUtil;

@Service(AccessTokenService.SERVICE_NAME)
public class AccessTokenServiceImpl implements AccessTokenService{

	private static Confs confs = new Confs();
	
	@Autowired
	AccessTokenPojoRepository tokenRepository;

	@Override
	public AccessToken getToken(HttpServletRequest request) {
		
		String requestHost = request.getLocalAddr();
		String queryString = confs.load(Confs.APPID) + confs.load(Confs.APPSECRET);
		
		List<AccessTokenPojo> tokens = tokenRepository.findByRequestHostAndrequestQuery(requestHost, queryString);
		if(tokens != null && tokens.size() > 0){
			for(AccessTokenPojo pojo : tokens){
				if(pojo.getExpiresTime().after(new Date())){
					Log.log("get access_token from local success");
					return BeanUtils.toAccessToken(pojo);
				}
				tokenRepository.delete(pojo);
			}
		}
		
		Log.log("geting access_token from weixin server");
		AccessToken token = WeixinUtil.getAccessToken();
		Log.log("get access_token from weixin server success");
		
		AccessTokenPojo pojo = BeanUtils.toAccessTokenPojo(token);
		pojo.setRequestHost(requestHost);
		pojo.setRequestQuery(queryString);
		
		Date nowDate = new Date();
		long expires = nowDate.getTime() + token.getExpires_in() * 1000;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(expires);
		pojo.setExpiresTime(calendar.getTime());
		tokenRepository.save(pojo);

		return token;
	}

}
