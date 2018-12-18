package com.hubertyoung.component.acfunvideo.index.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hubertyoung.common.base.BaseActivity;
import com.hubertyoung.common.entity.RegionBodyContent;
import com.hubertyoung.common.entity.Regions;
import com.hubertyoung.common.image.fresco.ImageLoaderUtil;
import com.hubertyoung.common.widget.sectioned.SectionParameters;
import com.hubertyoung.common.widget.sectioned.StatelessSection;
import com.hubertyoung.component_acfunvideo.R;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:HubertYoung
 * @date:2018/9/5 11:01
 * @since:V$VERSION
 * @desc:com.hubertyoung.component.acfunvideo.index.section
 */
public class NewRecommendBannersSection extends StatelessSection {
	private BaseActivity mActivity;
	private Regions regions;

	public NewRecommendBannersSection( BaseActivity activity ) {
		super( SectionParameters.builder().itemResourceId( R.layout.item_region_single_banner )//
//				.headerResourceId( R.layout.widget_region_header_text )//
//				.footerResourceId( R.layout.widget_region_bottom_menu )//
				.build() );
		this.mActivity = activity;
	}

	@Override
	public int getContentItemsTotal() {
		if ( regions != null ) {
			return regions.bodyContents == null ? 0 : 1;
		} else {
			return 0;
		}
	}

	@Override
	public int getSpanSizeLookup( int position ) {
		return 6;
	}

	@Override
	public RecyclerView.ViewHolder getItemViewHolder( View view, int itemType ) {
		return new BannersViewHolder( view );
	}

	@Override
	public void onBindItemViewHolder( RecyclerView.ViewHolder holder, int position ) {
		BannersViewHolder viewHolder = ( BannersViewHolder ) holder;
		if ( regions.bodyContents != null && regions.bodyContents.size() != 0 ) {
			RegionBodyContent bodyContent = regions.bodyContents.get( 0 );
			if ( bodyContent.ad == 1 ) {
				viewHolder.ad.setVisibility( View.GONE );
			} else {
				viewHolder.ad.setVisibility( View.GONE );
			}
			if ( bodyContent.images == null || bodyContent.images.size() <= 0 ) {
				ImageLoaderUtil.loadResourceImage( R.color.transparent, viewHolder.ivBanner1 );
			} else {
				ImageLoaderUtil.loadNetImage( bodyContent.images.get( 0 ), viewHolder.ivBanner1 );
			}
			if ( mOnItemClickListener != null ) {
				viewHolder.ivBanner1.setOnClickListener( new View.OnClickListener() {
					public void onClick( View view ) {
						if ( bodyContent != null ) {
							if ( bodyContent.ad == 1 ) {
//							SensorsAnalyticsUtil.a(HomeListAdapter.this.aq, regionBodyContent.title, HomeListAdapter.this.ar);
							}
//						SensorsAnalyticsUtil.a(regionBodyContent.title, regionBodyContent.actionId);
						}
//					HomeListAdapter.this.a(KanasConstants.bP, i2, regionBodyContent.ad == 1, regionBodyContent.title, regionBodyContent.actionId, regionBodyContent.contentId);
//					HomeListAdapter.this.f(regionBodyContent);
						mOnItemClickListener.onSingleBannerClick( view,bodyContent );
					}
				} );
			}
		} else {
			if ( regions.advertLists == null || regions.advertLists.size() <= 0 ) {
				// TODO: 2018/9/5 广告处理
			} else {
				viewHolder.ivBanner1.setVisibility( View.GONE );
				viewHolder.divider.setVisibility( View.GONE );
				viewHolder.ad.setVisibility( View.GONE );
				viewHolder.itemView.setVisibility( View.GONE );
			}
		}
	}

	public interface OnItemClickListener {
		void onSingleBannerClick( View v, RegionBodyContent bodyContent );
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
		mOnItemClickListener = onItemClickListener;
	}

	public void setRegions( Regions regions ) {
		this.regions = regions;
	}

	static class BannersViewHolder extends RecyclerView.ViewHolder {
		public ImageView ad;
		public View divider;
		public SimpleDraweeView ivBanner1;

		BannersViewHolder( View view ) {
			super( view );
			this.ivBanner1 = ( SimpleDraweeView ) view.findViewById( R.id.region_item_banner_1 );
			this.ad = ( ImageView ) view.findViewById( R.id.iv_ad );
			this.divider = ( View ) view.findViewById( R.id.divider );
		}
	}
}
