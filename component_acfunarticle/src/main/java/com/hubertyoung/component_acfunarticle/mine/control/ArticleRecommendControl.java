package com.hubertyoung.component_acfunarticle.mine.control;


import com.hubertyoung.common.base.BaseModel;
import com.hubertyoung.common.base.BasePresenter;
import com.hubertyoung.common.base.BaseView;
import com.hubertyoung.common.basebean.MyRequestMap;
import com.hubertyoung.component_acfunarticle.entity.ArticleRecommendEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:HubertYoung
 * @date:2018/10/16 17:17
 * @since:V1.0.0
 * @desc:com.hubertyoung.component_acfunarticle.mine.control
 */
public interface ArticleRecommendControl {

	interface Model extends BaseModel {
		Observable< List< ArticleRecommendEntity > > requestArticleRecommend( MyRequestMap map );
	}

	interface View extends BaseView {
		void setArticleRecommend( List<ArticleRecommendEntity> articleRecommendEntityList );

	}

	abstract class Presenter extends BasePresenter< View, Model > {
		public abstract void requestArticleRecommend( MyRequestMap map );
	}
}
