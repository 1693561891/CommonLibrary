package com.acty.component.home.index.section;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acty.component.home.entity.HomeIndexEntity;
import com.acty.component_home.R;
import com.hubertyoung.common.ImageLoaderUtils;
import com.hubertyoung.common.widget.sectioned.Section;
import com.hubertyoung.common.widget.sectioned.SectionParameters;

import java.util.List;


/**
 * <br>
 * <p>
 *
 * @author:Yang
 * @date:2018/2/6 下午5:08
 * @since:V$VERSION
 * @desc:com.gent.cardsharelife.ui.home.section
 */
public class DynamicSection extends Section {
	private Activity activity;
	private List< HomeIndexEntity.ChannelBean > data;

	public DynamicSection( Activity activity, List< HomeIndexEntity.ChannelBean > data ) {
		super( new SectionParameters.Builder( R.layout.home_item_dynamicsoreview_menu_body ).build() );
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getContentItemsTotal() {
		return data == null || data.isEmpty() ? 0 : data.size();
	}

	@Override
	public RecyclerView.ViewHolder getItemViewHolder( View view, int itemType ) {
		return new CardShareMenuBodyViewHolder( view );
	}

	@Override
	public void onBindItemViewHolder( RecyclerView.ViewHolder holder, int position ) {
		CardShareMenuBodyViewHolder viewHolder = ( CardShareMenuBodyViewHolder ) holder;
		HomeIndexEntity.ChannelBean bean = data.get( position );
		ImageLoaderUtils.getInstance().display( activity, viewHolder.mIvMenu, bean.iconUrl );
		viewHolder.mTvMenu.setText( bean.name );
		if ( mOnItemClickListener != null ) {
			viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick( View v ) {
					mOnItemClickListener.onitemClick( v, bean.iconUrl + "", bean.name );
				}
			} );
		}
	}

	public interface OnItemClickListener {
		void onitemClick( View v, String iconUrl, String name );
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
		mOnItemClickListener = onItemClickListener;
	}

	static class CardShareMenuBodyViewHolder extends RecyclerView.ViewHolder {
		AppCompatImageView mIvMenu;
		AppCompatTextView mTvMenu;

		CardShareMenuBodyViewHolder( View view ) {
			super( view );
			mIvMenu = view.findViewById( R.id.iv_menu );
			mTvMenu = view.findViewById( R.id.tv_menu );
		}
	}
}
