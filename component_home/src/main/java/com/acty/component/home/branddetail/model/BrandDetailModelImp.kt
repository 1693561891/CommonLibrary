package com.gent.youxidandan.ddsocial.ui.mytest.model

import com.acty.component.home.api.ApiHomeService
import com.acty.component.home.branddetail.control.BrandDetailControl
import com.acty.component.home.entity.BrandDetailEntity
import com.hubertyoung.common.api.Api
import com.hubertyoung.common.api.HostType
import com.hubertyoung.common.basebean.MyRequestMap
import com.hubertyoung.common.net.transformer.DefaultTransformer
import io.reactivex.Observable

/**
 * <br>
 * function:
 * <p>
 * @author:HubertYoung
 * @date:2018/5/15 2018/5/15
 * @since:V1.0
 * @desc:com.gent.youxidandan.ddsocial.ui.mytest.model
 */
class BrandDetailModelImp : BrandDetailControl.Model {
	override fun requestBrandDetail(map: MyRequestMap): Observable<BrandDetailEntity> {
			return Api.getDefault(HostType.MY_RESULT)
					.retrofitClient.builder(ApiHomeService::class.java)
					.requestBrandDetail(map.map)
					.compose(DefaultTransformer())
	}
}