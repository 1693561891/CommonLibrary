package com.acty.component.home;

import android.content.Context;
import android.content.Intent;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.acty.component.home.index.fragment.IndexRootFragment;

/**
 * <br>
 * function:任务组件功能实现
 * <p>
 *
 * @author:Yang
 * @date:2018/5/9 10:33 AM
 * @since:V1.0
 * @desc:com.kento.component.home
 */
public class ComponentTask implements IComponent {
	@Override
	public String getName() {
		return "ComponentIndex";
	}

	@Override
	public boolean onCall( CC cc ) {
		Context context = cc.getContext();
		Intent intent;
		switch ( cc.getActionName() ) {
			case "getIndexRootFragment":
				CC.sendCCResult( cc.getCallId(), CCResult.success( "fragment", IndexRootFragment.newInstance( "", "" ) ) );
				break;
//            case "toPatrolPlanActivity":
//                intent = new Intent(context, PatrolPlanActivity.class);
//                intent.putExtra("callId", cc.getCallId());
//                if (!(context instanceof Activity )) {
//                    //调用方没有设置context或app间组件跳转，context为application
//                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                context.startActivity(intent);
//                CC.sendCCResult(cc.getCallId(), CCResult.success());
//                break;
//            case "toIssueActivity":
//                intent = new Intent(context, IssueActivity.class);
//                intent.putExtra("callId", cc.getCallId());
//                if (!(context instanceof Activity )) {
//                    //调用方没有设置context或app间组件跳转，context为application
//                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.putExtra(ISSUE_TYPE, (int) cc.getParamItem("ISSUE_TYPE"));
//                intent.putExtra("toTeamsName", (String ) cc.getParamItem("toTeamsName"));
//                intent.putExtra("toTeamsId", (String ) cc.getParamItem("toTeamsId"));
//
//                context.startActivity(intent);
//                CC.sendCCResult(cc.getCallId(), CCResult.success());
//                break;
		}
		return false;
	}
}
