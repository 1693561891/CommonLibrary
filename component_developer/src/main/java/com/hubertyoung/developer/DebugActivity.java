package com.hubertyoung.developer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.billy.cc.core.component.CC;
import com.hubertyoung.common.base.AbsLifecycleActivity;
import com.hubertyoung.common.base.BaseActivityNew;
import com.hubertyoung.common.widget.preference.BasePreferenceFragment;
import com.hubertyoung.component_developer.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;


public class DebugActivity extends AbsLifecycleActivity implements FragmentManager.OnBackStackChangedListener {

	private LinearLayout mActivityRoot;
	private FrameLayout mContentLayout;
	private Toolbar mToolbar;
	public static DebugActivity mActivity;

	@Override
	public int getLayoutId() {
		return R.layout.developer_activity_main;
	}

	@Override
	public void initView( Bundle savedInstanceState ) {

	}

	@Override
	protected void stopLoading() {

	}

	@Override
	protected void showLoading( String title ) {

	}

	@Override
	protected void loadData() {

	}

	@Override
	public void initToolBar() {
		mActivity = this;
		mToolbar = findViewById( R.id.view_toolbar );
		mActivityRoot = findViewById( R.id.activity_root );
		mContentLayout = findViewById( R.id.content_layout );

		setSupportActionBar( mToolbar );
		ActionBar actionBar = getSupportActionBar();
		if ( actionBar != null ) {
			actionBar.setDisplayHomeAsUpEnabled( true );
		}
		mToolbar.setTitle( "开发设置" );

		initAction();
		getFragment( "", BiliPreferencesFragment.class.getName(), null, false );
	}

	private void initAction() {
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DebugActivity.this.onBackPressed();
			}
		});
		getSupportFragmentManager().addOnBackStackChangedListener( this );
	}

	private Fragment getFragment( CharSequence charSequence, String str, Bundle bundle, boolean isBackStack ) {
//		if (a(str)) {
		setTitle( charSequence );
		Fragment instantiate = Fragment.instantiate( this, str, bundle );
		FragmentTransaction beginTransaction = this.getSupportFragmentManager().beginTransaction();
//			if (!TextUtils.equals(str, BiliPreferencesFragment.class.getName())) {
//				beginTransaction.setCustomAnimations(this.j, 0, 0, 0);
//			}
		beginTransaction.replace( R.id.content_layout, instantiate, str );
		if ( isBackStack ) {
			beginTransaction.addToBackStack( "a" );
		}
		if ( charSequence != null ) {
			beginTransaction.setBreadCrumbTitle( charSequence );
		}
		beginTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
		beginTransaction.commitAllowingStateLoss();
		return instantiate;
//		}
//		throw new IllegalArgumentException("error");
	}

	@Override
	public void onBackStackChanged() {
		int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
		if ( backStackEntryCount == 0 ) {
			setTitle( "设置" );
		} else {
			setTitle( getSupportFragmentManager().getBackStackEntryAt( backStackEntryCount - 1 ).getBreadCrumbTitle() );
		}
	}


	public static class BiliPreferencesFragment extends BasePreferenceFragment {

		@Override
		public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {
			addPreferencesFromResource( R.xml.main_preferences );
			findPreference( getString( R.string.pref_key_switch_environment ) ).setOnPreferenceClickListener( new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick( Preference preference ) {
					CC.obtainBuilder( "ComponentDebug" ).setContext( mActivity ).setActionName( "toEnvironmentSwitchActivity" ).build().call();
					return false;
				}
			} );
//
//			Preference findPreference = findPreference(getString(R.string.pref_key_wifi_auto_update));
//			if (findPreference != null) {
////				findPreference.a(new b() {
////					public boolean a(Preference preference, Object obj) {
////						if (obj.equals(Boolean.valueOf(false))) {
////							hdp.a(jsx.a(new byte[]{(byte) 107, (byte) 96, (byte) 114, (byte) 100, (byte) 117, (byte) 117, (byte) 90, (byte) 114, (byte) 108, (byte) 99, (byte) 108, (byte) 97,
/// (byte) 106, (byte) 114, (byte) 107, (byte) 105, (byte) 106, (byte) 100, (byte) 97, (byte) 100, (byte) 117, (byte) 117, (byte) 90, (byte) 118, (byte) 96, (byte) 113, (byte) 113, (byte) 108,
/// (byte) 107, (byte) 98, (byte) 118, (byte) 90, (byte) 113, (byte) 112, (byte) 119, (byte) 107, (byte) 106, (byte) 99, (byte) 99}), new String[0]);
////						}
////						return true;
////					}
////				});
//			}
//			findPreference(getString(R.string.pref_key_logout)).a(new Preference.c() {
//				public boolean a(Preference preference) {
//					BiliPreferencesFragment.this.d();
//					nvb.a(BiliPreferencesFragment.this.getContext(), preference.B());
//					return true;
//				}
//			});
		}
	}

	@Override
	protected void onDestroy() {
		getSupportFragmentManager().removeOnBackStackChangedListener( this );
		mActivity = null;
		super.onDestroy();
	}

	public static void launch( Context context ) {
		if ( context instanceof BaseActivityNew ) {
			( ( BaseActivityNew ) context ).startActivity( DebugActivity.class );
		} else {
			Intent intent = new Intent( context, DebugActivity.class );
			if ( !( context instanceof Activity ) ) {
				//调用方没有设置context或app间组件跳转，context为application
				intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
			}
			context.startActivity( intent );
		}
	}

}
