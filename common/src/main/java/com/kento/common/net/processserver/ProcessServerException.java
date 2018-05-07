package com.kento.common.net.processserver;

import android.content.Intent;
import android.text.TextUtils;

import com.kento.common.CommonApplication;
import com.kento.common.constant.AppConfig;
import com.kento.common.net.config.NetStatus;


/**
 * @author:Yang
 * @date:2017/12/6 14:53
 * @since:v1.0
 * @desc:com.kento.common.net.processserver
 * @param:
 */
public class ProcessServerException {
	private String status;

	public ProcessServerException( String status ) {
		this.status = status;
	}

	public void send() {
		if ( TextUtils.equals( NetStatus.User_Not_Login.getIndex(), status ) || TextUtils.equals( NetStatus.Account_Login_Other_Device.getIndex(), status ) ) {
			//TODO 发送登录的广播
			Intent intent = new Intent( AppConfig.ACTION_LOGIN_ACTIVITY );
			CommonApplication.getAppContext()
						   .sendBroadcast( intent );
		}
	}
}