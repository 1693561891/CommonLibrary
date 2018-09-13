package com.hubertyoung.baseplatform.platforms.send;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.hubertyoung.baseplatform.sdk.OnCallback;
import com.hubertyoung.baseplatform.sdk.OtherPlatform;
import com.hubertyoung.baseplatform.sdk.ResultCode;
import com.hubertyoung.baseplatform.share.IShareable;
import com.hubertyoung.baseplatform.share.ShareData;
import com.hubertyoung.baseplatform.share.ShareTo;
import com.hubertyoung.baseplatform.share.image.resource.BitmapResource;
import com.hubertyoung.baseplatform.share.image.resource.FileResource;
import com.hubertyoung.baseplatform.share.image.resource.ImageResource;
import com.hubertyoung.baseplatform.share.media.IMediaObject;
import com.hubertyoung.baseplatform.share.media.MoImage;
import com.hubertyoung.baseplatformlibrary.R;

import java.util.List;

public class SendShare implements IShareable {
	public static final String TAG = SendShare.class.getSimpleName();

	static final String PACKAGE_QQ = "com.tencent.mobileqq";
	static final String PACKAGE_WX = "com.tencent.mm";

	Activity mActivity;
	OtherPlatform mPlatform;


	SendShare( Activity activity, OtherPlatform platform ) {
		mActivity = activity;
		mPlatform = platform;
	}

	@Override
	public void share( @NonNull final ShareData data, @NonNull final OnCallback< String > callback ) {
		if ( mPlatform.getName()
					  .equals( ShareTo.ToQQ ) ) {
			if ( !isApplicationInstalled( mActivity, PACKAGE_QQ ) ) {
				callback.onError( mActivity, ResultCode.RESULT_FAILED, mActivity.getString( R.string.sdk_platform_qq_uninstall ));
				return;
			}
		} else {
			if ( !isApplicationInstalled( mActivity, PACKAGE_WX ) ) {
				callback.onError( mActivity, ResultCode.RESULT_FAILED, mActivity.getString( R.string.sdk_platform_wechat_uninstall ));
				return;
			}
		}
		Intent intent = new Intent( Intent.ACTION_SEND );

		if ( mPlatform.getName()
					  .equals( ShareTo.ToQQ ) ) {
			intent.setClassName( PACKAGE_QQ, "com.tencent.mobileqq.activity.JumpActivity" );
		} else if ( mPlatform.getName()
							 .equals( ShareTo.ToWXSession ) ) {
			intent.setClassName( PACKAGE_WX, "com.tencent.mm.ui.tools.ShareImgUI" );
		} else {
			intent.setClassName( PACKAGE_WX, "com.tencent.mm.ui.tools.ShareToTimeLineUI" );
		}

		switch ( data.type() ) {
			case IMediaObject.TYPE_IMAGE:
				intent.setType( "image/*" );
				intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION );

				ImageResource resource = ( ( MoImage ) data.media ).resource;
				if ( resource instanceof BitmapResource ) {
					final Uri uri = Uri.parse( MediaStore.Images.Media.insertImage( mActivity.getContentResolver(), resource.toBitmap(), null, null ) );
					intent.putExtra( Intent.EXTRA_STREAM, uri );
				} else if ( resource instanceof FileResource ) {
					intent.putExtra( Intent.EXTRA_STREAM, Uri.fromFile( ( ( FileResource ) resource ).file ) );
				} else {
					intent.putExtra( Intent.EXTRA_STREAM, Uri.parse( resource.toUri() ) );
				}
				break;
			case IMediaObject.TYPE_TEXT:
				intent.setType( "text/plain" );
				intent.putExtra( "Kdescription", data.text );
				intent.putExtra( Intent.EXTRA_TEXT, data.text );
				intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				break;
			default:
				callback.onError( mActivity, ResultCode.RESULT_FAILED, mActivity.getString( R.string.sdk_platform_unsupported_sharing_types ) );
				return;
		}
		if ( isIntentAvailable( mActivity, intent ) ) {
			mActivity.startActivity( intent );
		} else {
			callback.onError( mActivity, ResultCode.RESULT_FAILED, mActivity.getString( R.string.sdk_platform_share_error ) );
			return;
		}
	}

	boolean isIntentAvailable( Context context, Intent intent ) {
		PackageManager pm = context.getPackageManager();
		List list = pm.queryIntentActivities( intent, PackageManager.MATCH_DEFAULT_ONLY );
		return list.size() > 0;
	}

	boolean isApplicationInstalled( Context context, String packageName ) {
		PackageManager packageManager = context.getPackageManager();
		List< PackageInfo > list = packageManager.getInstalledPackages( 0 );
		for (int i = 0; i < list.size(); i++) {
			if ( list.get( i ).packageName.equalsIgnoreCase( packageName ) ) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void onResult( int requestCode, int resultCode, Intent data ) {
	}
}
