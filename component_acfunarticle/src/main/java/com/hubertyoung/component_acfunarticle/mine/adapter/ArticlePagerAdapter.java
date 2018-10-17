package com.hubertyoung.component_acfunarticle.mine.adapter;

import com.billy.cc.core.component.CC;
import com.hubertyoung.component_acfunarticle.R;
import com.hubertyoung.component_acfunarticle.entity.ServerChannel;
import com.hubertyoung.component_acfunarticle.mine.fragment.ArticleGeneralSecondFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:HubertYoung
 * @date:2018/10/16 18:54
 * @since:V$VERSION
 * @desc:com.hubertyoung.component_acfunarticle.mine.adapter
 */
public class ArticlePagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity mActivity;
	private ArrayList< ServerChannel > data = new ArrayList<>();
	private List< String > mTitles;
	private List< String > dataID;
	private List< Fragment > mFragments;

	public ArticlePagerAdapter( FragmentActivity activity, FragmentManager fm ) {
		super( fm );
		this.mActivity = activity;
	}

	@Override
	public Fragment getItem( int position ) {
		return mFragments.get( position );
	}

	@Override
	public int getCount() {
		return mFragments == null ? 0 : mFragments.size();
	}

	public void clear() {
		this.mFragments.clear();
		this.mTitles.clear();
		notifyDataSetChanged();
	}

	@Nullable
	@Override
	public CharSequence getPageTitle( int position ) {
		return mTitles.get( position );
	}

	public void setInfo( List< ServerChannel > article ) {
		data.clear();
		data.addAll( article );

		if ( data == null ) {
			return;
		}
		mTitles = new ArrayList();
		dataID = new ArrayList();

		mTitles.add( mActivity.getString( R.string.home_tab_recommend ) );
		dataID.add( "0" );
		for (int i = 0; i < data.size(); i++) {
			dataID.add( String.valueOf( ( data.get( i ) ).id ) );
			mTitles.add( ( data.get( i ) ).name );
		}
		mFragments = new ArrayList();
//		mFragments.add( ArticleRecommendFragment.newInstance( "63", "" ) );
		mFragments.add( CC.obtainBuilder( "ComponentAcFunIndex" )//
				.setActionName( "getNewRecommendFragment" )//
				.addParam( "channelId", "63" )//
				.build()//
				.call()//
				.getDataItem( "fragment" ) );
		for (int i = 1; i < dataID.size(); i++) {
			mFragments.add( ArticleGeneralSecondFragment.newInstance( dataID.get( i ), "" ) );
		}
		notifyDataSetChanged();
	}
}
