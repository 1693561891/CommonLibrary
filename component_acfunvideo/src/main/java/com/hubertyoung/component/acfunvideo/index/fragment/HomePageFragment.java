package com.hubertyoung.component.acfunvideo.index.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hubertyoung.common.base.AbsLifecycleFragment;
import com.hubertyoung.common.utils.bar.BarUtils;
import com.hubertyoung.common.utils.display.ToastUtil;
import com.hubertyoung.common.utils.log.CommonLog;
import com.hubertyoung.component.acfunvideo.config.VideoConstants;
import com.hubertyoung.component.acfunvideo.index.adapter.HomePagerAdapter;
import com.hubertyoung.component.acfunvideo.index.vm.HomePageViewModel;
import com.hubertyoung.component_acfunvideo.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.HashMap;

import android.support.v7.widget.Toolbar;
import android.arch.lifecycle.Observer;
import android.support.v4.view.ViewPager;

/**
 * <br>
 * function:首页
 * <p>
 *
 * @author:Yang
 * @date:2018/5/9 10:18 AM
 * @since:V1.0
 * @desc:com.hubertyoung.component.home.index.fragment
 */
public class HomePageFragment extends AbsLifecycleFragment< HomePageViewModel >{

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String mParam1;
	private String mParam2;
	private Toolbar mToolbar;
	private SmartTabLayout videoTabLayout;
	private ImageView mIvSearch;
	private ImageView mIvGame;
	private ViewPager mHomeViewPager;
	private HomePagerAdapter mHomePagerAdapter;
	private NewRecommendFragment mNewRecommendFragment;
	private ChannelFragment mChannelFragment;

	private boolean isShowGameIcon = true;
	private boolean isGameNew = false;
	private int mCurrentPostion = 0;

	public HomePageFragment() {
	}

	public static HomePageFragment newInstance( String param1, String param2 ) {
		HomePageFragment fragment = new HomePageFragment();
		Bundle args = new Bundle();
		args.putString( ARG_PARAM1, param1 );
		args.putString( ARG_PARAM2, param2 );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		if ( getArguments() != null ) {
			mParam1 = getArguments().getString( ARG_PARAM1 );
			mParam2 = getArguments().getString( ARG_PARAM2 );
		}
	}

	@Override
	protected void initToolBar() {
		BarUtils.setPaddingSmart( mToolbar );
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.fragment_home_page_view;
	}

	@Override
	protected void initView( Bundle savedInstanceState ) {
		super.initView( savedInstanceState );
		mToolbar = ( Toolbar ) findViewById( R.id.toolbar );
		videoTabLayout = ( SmartTabLayout ) findViewById( R.id.tab_layout_video );
		mIvSearch = ( ImageView ) findViewById( R.id.iv_search );
		mIvGame = ( ImageView ) findViewById( R.id.iv_game );
		mHomeViewPager = ( ViewPager ) findViewById( R.id.home_view_pager );
		initViewPager();
		initAction();
		initData();
	}

	@Override
	protected void lazyLoad() {
		loadData();
	}

	public void loadData() {
		mViewModel.requestDomainAndroidCfg( );
	}
	@Override
	protected void dataObserver() {
		registerObserver(VideoConstants.EVENT_KEY_CHANNEL_DOMAIN_ANDROIDCFG ,HashMap.class).observe( this, new Observer< HashMap >() {
			@Override
			public void onChanged( HashMap hashMap ) {
				CommonLog.loge( hashMap.toString() );
			}
		});
	}


	private void initData() {
		if ( isShowGameIcon ) {
			mIvGame.setVisibility( View.VISIBLE );
			mIvGame.setBackgroundResource( isGameNew ? R.mipmap.ic_game_center_new : R.mipmap.ic_game_center );
		} else {
			mIvGame.setVisibility( View.GONE );
		}
	}

	private void initAction() {
		mHomeViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

			}

			@Override
			public void onPageSelected( int position ) {
				mCurrentPostion = position;
			}

			@Override
			public void onPageScrollStateChanged( int state ) {

			}
		} );
		mIvSearch.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				ToastUtil.showSuccess( "mIvSearch" );
			}
		} );
		mIvGame.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				ToastUtil.showSuccess( "mIvGame" );
			}
		} );
	}

	private void initViewPager() {
//		videoTabLayout.setCustomTabView( R.layout.widget_home_page_tab_view, R.id.tab_text );
		mHomePagerAdapter = new HomePagerAdapter( getChildFragmentManager() );
		mNewRecommendFragment = NewRecommendFragment.newInstance( "0", "" );
		mChannelFragment = ChannelFragment.newInstance( "", "" );

		mHomePagerAdapter.add( mNewRecommendFragment, activity.getString( R.string.recommend_text ) );
		mHomePagerAdapter.add( mChannelFragment, activity.getString( R.string.common_channel ) );
		mHomeViewPager.setAdapter( mHomePagerAdapter );
		mHomeViewPager.setOffscreenPageLimit( 1 );
		mHomeViewPager.setCurrentItem( 0 );
		videoTabLayout.setViewPager( mHomeViewPager );
	}

	@Override
	public void showLoading( String title) {

	}
//
//	@Override
//	public void stopLoading() {
//		super.stopLoading();
//	}

	@Override
	protected void stopLoading() {

	}
	public void showErrorTip( String msg ) {

	}
}
