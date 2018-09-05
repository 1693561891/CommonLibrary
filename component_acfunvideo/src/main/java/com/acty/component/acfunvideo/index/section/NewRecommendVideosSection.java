package com.acty.component.acfunvideo.index.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acty.component.acfunvideo.entity.RegionBodyContent;
import com.acty.component.acfunvideo.entity.Regions;
import com.acty.component.acfunvideo.index.section.viewholder.BindHeaderTitleViewHolder;
import com.acty.component.acfunvideo.index.section.viewholder.HeadTitleViewHolder;
import com.acty.component_acfunvideo.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hubertyoung.common.base.BaseActivity;
import com.hubertyoung.common.image.fresco.ImageLoaderUtil;
import com.hubertyoung.common.utils.DisplayUtil;
import com.hubertyoung.common.utils.StringUtil;
import com.hubertyoung.common.widget.sectioned.Section;
import com.hubertyoung.common.widget.sectioned.SectionParameters;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:HubertYoung
 * @date:2018/9/4 16:16
 * @since:V$VERSION
 * @desc:com.acty.component.acfunvideo.index.section
 */
public class NewRecommendVideosSection extends Section {
	private BaseActivity mActivity;
	private Regions mRegions;

	public NewRecommendVideosSection( BaseActivity activity ) {
		super( new SectionParameters.Builder( R.layout.item_home_video ).headerResourceId( R.layout.widget_region_header_text ).build() );
		this.mActivity = activity;
	}

	@Override
	public RecyclerView.ViewHolder getHeaderViewHolder( View view ) {
		return new HeadTitleViewHolder( view );
	}

	@Override
	public void onBindHeaderViewHolder( RecyclerView.ViewHolder holder ) {
		BindHeaderTitleViewHolder viewHolder = new BindHeaderTitleViewHolder(mActivity, ( HeadTitleViewHolder ) holder );
		viewHolder.viewBindData(getContentItemsTotal(),mRegions);
	}

	@Override
	public int getContentItemsTotal() {
		if ( mRegions != null ) {
			return mRegions.bodyContents == null ? 0 : Math.min( mRegions.bodyContents.size(), mRegions.show + 1);
		} else {
			return 0;
		}

	}

	@Override
	public int getSpanSizeLookup( int position ) {
		if ( mRegions.topContent == null ) {
			return 3;
		} else {
			if ( position == 0 ) {
				return 6;
			} else {
				return 3;
			}
		}
	}

	@Override
	public RecyclerView.ViewHolder getItemViewHolder( View view, int itemType ) {
		return new NewRecommendVideosViewHolder( view );
	}

	@Override
	public void onBindItemViewHolder( RecyclerView.ViewHolder holder, int position ) {
		NewRecommendVideosViewHolder viewHolder = ( NewRecommendVideosViewHolder ) holder;
		RegionBodyContent regionBodyContent = mRegions.bodyContents.get( position );

		if ( regionBodyContent == null ) {
			viewHolder.itemView.setVisibility( View.GONE );
			viewHolder.playsLayout.setVisibility( View.GONE );
			return;
		} else {
			viewHolder.itemView.setVisibility( View.VISIBLE );
		}
		if ( regionBodyContent.images == null || regionBodyContent.images.size() <= 0 ) {
			ImageLoaderUtil.loadResourceImage( R.color.transparent, viewHolder.mImg );
		} else {
			ImageLoaderUtil.loadNetImage( regionBodyContent.images.get( 0 ), viewHolder.mImg );
		}

		if ( mRegions.topContent == null ) {
			viewHolder.root.setPadding( position % 2 == 0 ? DisplayUtil.dip2px( 10 ) : DisplayUtil.dip2px( 5 ),//
					0,//
					position % 2 == 1 ? DisplayUtil.dip2px( 10 ) : DisplayUtil.dip2px( 5 ),//
					DisplayUtil.dip2px( 10 ) );
		} else {
			if ( position > 0 ) {
				viewHolder.root.setPadding( position % 2 == 1 ? DisplayUtil.dip2px( 10 ) : DisplayUtil.dip2px( 5 ),//
						0,//
						position % 2 == 0 ? DisplayUtil.dip2px( 10 ) : DisplayUtil.dip2px( 5 ),//
						DisplayUtil.dip2px( 10 ) );
			} else {
				viewHolder.root.setPadding( DisplayUtil.dip2px( 10 ),//
						0,//
						DisplayUtil.dip2px( 10 ),//
						DisplayUtil.dip2px( 10 ) );
			}
		}

		viewHolder.mTitle.setText( regionBodyContent.title );
		if ( regionBodyContent.actionId == 14 ) {
			viewHolder.playsLayout.setVisibility( View.VISIBLE );
			viewHolder.mDanmus.setVisibility( View.GONE );
			viewHolder.mPlays.setText( R.string.common_special );
			viewHolder.mPlays.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
			viewHolder.mPlays.setVisibility( View.VISIBLE );
		} else if ( regionBodyContent.actionId == 10 ) {
			if ( regionBodyContent.visit != null ) {
				viewHolder.playsLayout.setVisibility( View.VISIBLE );
				viewHolder.mPlays.setText( StringUtil.formatChineseNum( mActivity, regionBodyContent.visit.views ) );
				viewHolder.mPlays.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_home_item_video_views, 0, 0, 0 );
				viewHolder.mPlays.setVisibility( View.VISIBLE );
				viewHolder.mDanmus.setText( StringUtil.formatChineseNum( mActivity, regionBodyContent.visit.comments ) );
				viewHolder.mDanmus.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_home_item_video_comments, 0, 0, 0 );
				viewHolder.mDanmus.setVisibility( View.VISIBLE );
			} else {
				viewHolder.playsLayout.setVisibility( View.GONE );
			}
			viewHolder.mMark.setText( R.string.common_article );
			viewHolder.mMark.setBackgroundResource( R.drawable.shape_home_item_mark_green );
			viewHolder.mMark.setVisibility( View.VISIBLE );
		} else if ( regionBodyContent.actionId == 17 ) {
			viewHolder.playsLayout.setVisibility( View.GONE );
			viewHolder.mMark.setText( R.string.common_game );
			viewHolder.mMark.setBackgroundResource( R.drawable.shape_home_item_mark_red );
			viewHolder.mMark.setVisibility( View.VISIBLE );
		} else {
			if ( regionBodyContent.visit != null ) {
				viewHolder.mPlays.setVisibility( View.VISIBLE );
				viewHolder.mDanmus.setVisibility( View.VISIBLE );
				viewHolder.playsLayout.setVisibility( View.VISIBLE );
				viewHolder.mPlays.setText( StringUtil.formatChineseNum( mActivity, regionBodyContent.visit.views ) );
				viewHolder.mPlays.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.icon_video_plays, 0, 0, 0 );
				viewHolder.mDanmus.setText( StringUtil.formatChineseNum( mActivity, regionBodyContent.visit.danmakuSize ) );
			} else {
				viewHolder.playsLayout.setVisibility( View.GONE );
			}
			viewHolder.mMark.setVisibility( View.GONE );
		}
		viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

			}
		} );
	}

	public void setRegions( Regions mRegions ) {
		this.mRegions = mRegions;
	}

	public void addRegions( Regions regions ) {
		this.mRegions.bodyContents.addAll( regions.bodyContents );
	}

	public interface OnItemClickListener {
		void onClickRecommendBangumiItem( View v, Regions bodyContentsBean );
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
		mOnItemClickListener = onItemClickListener;
	}

	static class NewRecommendVideosViewHolder extends RecyclerView.ViewHolder {
		public TextView mDanmus;
		public SimpleDraweeView mImg;
		public TextView mMark;
		public TextView mPlays;
		public TextView mTitle;
		public View playsLayout;
		public RelativeLayout root;

		NewRecommendVideosViewHolder( View view ) {
			super( view );
			mImg = ( SimpleDraweeView ) view.findViewById( R.id.recommend_video_item_view_img );
			mPlays = ( TextView ) view.findViewById( R.id.recommend_video_item_view_plays );
			mDanmus = ( TextView ) view.findViewById( R.id.recommend_video_item_view_danmaku );
			playsLayout = ( LinearLayout ) view.findViewById( R.id.recommend_video_item_view_plays_layout );
			mMark = ( TextView ) view.findViewById( R.id.home_item_mark_right_top );
			mTitle = ( TextView ) view.findViewById( R.id.recommend_video_item_view_title );
			root = ( RelativeLayout ) view.findViewById( R.id.home_item_root );
		}
	}
}
