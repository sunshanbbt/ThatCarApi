package com.zhengshun.touch.api.user.bean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhengshun.touch.api.common.util.DateUtil;
import com.zhengshun.touch.api.common.util.JsonUtil;
import com.zhengshun.touch.api.common.util.MapUtil;
import com.zhengshun.touch.api.common.util.SqlUtil;
import com.zhengshun.touch.api.user.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;


/**
 * Created by lsk on 2016/9/21.
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AppDbSession {
	@Resource
	protected DBService dbService;
	private int liveMin = 60 * 24 * 7;

	private Logger logger = LoggerFactory.getLogger(AppDbSession.class);

	@Resource
	private EhCacheCacheManager cacheManager;

	private String cacheName = "appSession";

	public boolean remove(String token) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.evict(token);

		Map rec = dbService.queryRec(
				"select * from tb_app_session where token=?", token);
		if (rec == null) {
			return false;
		}

		dbService
				.update("delete from tb_app_session where id=?", rec.get("id"));
		return true;
	}

	private Map toMap(String data) {
		return com.alibaba.fastjson.JSONObject.parseObject(data, LinkedHashMap.class);
	}

	public Object access(String token) {
		Cache cache = getCache();
		Map rec = (Map) cache.get(token, Map.class);

		if (rec == null) {
			logger.info("query db session");
			rec = dbService.queryRec("select * from tb_app_session where token=?", token);
			cache.put(token, rec);
		}

		if (rec == null)
			return new Object[][] { { "code", 413 }, { "msg", "token不存在" } };

		if (!(Boolean) rec.get("status")) {
			dbService.update("delete from tb_app_session where token=?", token);
			return toMap((String) rec.get("err_data"));
		}

		Date now = new Date();

		if (now.getTime() > ((Date) rec.get("expire_time")).getTime()) {
			dbService.update("delete from tb_app_session where token=?", token);
			return new Object[][] { { "code", 411 }, { "msg", "token已过期" } };
		}

		rec.put("expire_time", DateUtil.dateAddMins(now, liveMin));
		rec.put("last_access_time", now);

		cache.put(token, rec);

		dbService
				.update(SqlUtil.buildUpdateSql(
						"tb_app_session",
						MapUtil.array2Map(new Object[][] {
								{ "id", rec.get("id") },
								{ "expire_time", rec.get("expire_time") },
								{ "last_access_time",
										rec.get("last_access_time") } })));

		return new AppSessionBean(toMap((String) rec.get("session")));

	}



	private Cache getCache() {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			try {
				cacheManager.getCacheManager().addCache(cacheName);
			} catch (Exception e) {
				logger.warn("添加cache失败", e);
			}
			cache = cacheManager.getCache(cacheName);
		}
		return cache;
	}

	public AppSessionBean create(HttpServletRequest request, String identifier) {

		Map<String, Object> user = dbService.queryRec("select id as user_id, openId, uuid from tb_user where openId=?",
				identifier);
		long userId = Long.parseLong(user.get("user_id").toString());


		String token = UUID.randomUUID().toString().replaceAll("-", "");
		String refreshToken = (String) user.get("uuid");

		Map session = MapUtil.array2Map(new Object[][] {
				{
						"front",
						new Object[][] { { "userId", userId },
								{ "token", token } } },
				{ "userData", user } });

		Map oSession = dbService
				.queryRec("select * from tb_app_session where user_id="
						+ userId + " order by id desc limit 1");
		if (oSession != null) {
			getCache().evict(oSession.get("token"));
			dbService
					.update(SqlUtil.buildUpdateSql(
							"tb_app_session",
							MapUtil.array2Map(new Object[][] {
									{ "id", oSession.get("id") },
									{ "status", 0 },
									{
											"err_data",
											JsonUtil.toString(MapUtil
													.array2Map(new Object[][] {
															{ "code", 410 },
															{ "msg",
																	"您的账号已在其他设备登录" } })) } })));
		}

		Date now = new Date();
		dbService.insert(SqlUtil.buildInsertSqlMap(
				"tb_app_session",
				new Object[][] { { "token", token },
						{ "refresh_token", refreshToken },
						{ "user_id", userId },
						{ "expire_time", DateUtil.dateAddMins(now, liveMin) },
						{ "last_access_time", now }, { "status", 1 },
						{ "session", JsonUtil.toString(session) } }));

		return new AppSessionBean(session);

	}

	public Map<String, Object> getUserData(String token) {
		Map rec = dbService
				.queryRec("select session from tb_app_session where token='"
						+ token + "'");
		return toMap((String) rec.get("session"));
	}

	public Object autoLogin(HttpServletRequest request, String refreshToken) {

		Map rec = dbService.queryRec(
				"select openId from tb_user where uuid=?", refreshToken);
		if (rec == null) {
			return new Object[][] { { "success", false },
					{ "msg", "refreshToken不存在" } };
		}
		String openId = (String) rec.get("openId");

		AppSessionBean sessionBean = create(request, openId);

		return sessionBean;

	}
}
