package com.wxc.happyturtle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class IpUtil {

	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		if(ip.indexOf(",")!=-1){
			String[] temp = ip.split(",");
			for(int i=0;i<temp.length;i++){
				if("unknown".equalsIgnoreCase(temp[i].trim())){
					continue;
				}

				ip = temp[i].trim();
				break;
			}
		}
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			logger.info("ip------------{}",ip);
			return "127.0.0.1";
		}
		
		return ip;
	}

}
