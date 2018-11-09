package com.hubertyoung.component_acfunarticle.article.model;

import com.hubertyoung.common.CommonApplication;
import com.hubertyoung.common.api.Api;
import com.hubertyoung.common.api.HostType;
import com.hubertyoung.common.net.transformer.DefaultTransformer;
import com.hubertyoung.component_acfunarticle.BuildConfig;
import com.hubertyoung.component_acfunarticle.api.ApiArticleService;
import com.hubertyoung.component_acfunarticle.article.control.ArticleControl;
import com.hubertyoung.component_acfunarticle.entity.Channel;
import com.hubertyoung.environmentswitcher.EnvironmentSwitcher;

import java.util.HashMap;

import io.reactivex.Flowable;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:HubertYoung
 * @date:2018/10/16 17:17
 * @since:V1.0.0
 * @desc:com.hubertyoung.component_acfunarticle.mine.model
 */
public class ArticleModelImp implements ArticleControl.Model {
	@Override
	public Flowable< Channel > requestAllChannel( HashMap map ) {
		return Api.getDefault( HostType.MY_RESULT )
				.getRetrofitClient()
				.setBaseUrl( EnvironmentSwitcher.getMineEnvironment( CommonApplication.getAppContext(), BuildConfig.DEBUG) )
				.builder( ApiArticleService.class )
				.requestAllChannel( map )
				.compose( new DefaultTransformer() );
	}
//	@Override
//	public Flowable< User > requestUserInfo( MyRequestMap map ) {
//		return Api.getDefault( HostType.MY_RESULT )
//				.getRetrofitClient()
//				.setBaseUrl( EnvironmentSwitcher.getMineEnvironment( CommonApplication.getAppContext(), BuildConfig.DEBUG) )
//				.builder( ApiHomeService.class )
//				.requestUserInfo( map.map )
//				.compose( new DefaultTransformer() );
//	}
}
