package com.hubertyoung.pay.platforms.weixin;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.hubertyoung.pay.sdk.IResult;
import com.hubertyoung.pay.sdk.OnCallback;
import com.hubertyoung.pay.sdk.OtherPlatform;
import com.hubertyoung.pay.sdk.ResultCode;
import com.hubertyoung.pay.tools.PayLogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.WeakHashMap;



/**
 * Created by ezy on 17/3/18.
 */

abstract class WXBase implements IResult, IWXAPIEventHandler {
	public static final String TAG = WXBase.class.getSimpleName();

	public static int REQUEST_CODE = 1999;

	static WeakHashMap< IResult, Boolean > services = new WeakHashMap<>();

	final protected Activity mActivity;
	final protected OtherPlatform mPlatform;

	protected OnCallback mCallback;

	IWXAPI mApi;

	protected WXBase( Activity activity, OtherPlatform platform ) {
		mActivity = activity;
		mPlatform = platform;
		if ( !TextUtils.isEmpty( platform.getAppId() ) ) {
			mApi = WXAPIFactory.createWXAPI( activity.getApplicationContext(), platform.getAppId(), true );
			mApi.registerApp( platform.getAppId() );
		}
		services.put( this, true );
	}


	@Override
	public void onResult( int requestCode, int resultCode, Intent data ) {
		if ( mApi != null && requestCode == REQUEST_CODE ) {
			mApi.handleIntent( data, this );
		}
	}

	@Override
	public void onReq( BaseReq req ) {
		PayLogUtil.loge( TAG, "transaction = " + req.transaction + ", type = " + req.getType() + ", openId = " + req.openId );
	}

	@Override
	public void onResp( BaseResp resp ) {
		PayLogUtil.loge( TAG, "transaction = " + resp.transaction + ", type = " + resp.getType() + ", errCode = " + resp.errCode + ", err = " + resp.errStr );

		if ( resp.errCode == BaseResp.ErrCode.ERR_OK ) {
			switch ( resp.getType() ) {
				case ConstantsAPI.COMMAND_SENDAUTH: //授权返回
					onResultOk( ( SendAuth.Resp ) resp );
					break;
				case ConstantsAPI.COMMAND_PAY_BY_WX://支付返回
					onResultOk( ( PayResp ) resp );
					break;
				case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX://分享返回
					onResultOk( ( SendMessageToWX.Resp ) resp );
					break;
			}
		} else if ( resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL ) {
			mCallback.onError( mActivity, ResultCode.RESULT_CANCELLED, "ddpsdk_cancel_auth" );
		} else {
			mCallback.onError( mActivity, ResultCode.RESULT_FAILED, toMessage( resp ) );
		}
		mCallback.onCompleted( mActivity );
	}

	String toMessage( BaseResp resp ) {
		return "[" + resp.errCode + "]" + ( resp.errStr == null ? "" : resp.errStr );
	}

	protected void onResultOk( PayResp resp ) {

	}

	protected void onResultOk( SendAuth.Resp resp ) {
	}

	protected void onResultOk( SendMessageToWX.Resp resp ) {
	}
}
