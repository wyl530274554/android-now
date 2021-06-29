package com.melon.commonlib.util;

import android.content.Context;
import android.content.res.Resources;

import com.melon.commonlib.R;

/**
 * 广告过滤
 *
 * @author melon.wang
 * @date 2019/1/11 16:20
 * Email 530274554@qq.com
 */
public class AdFilterTool {
    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {
            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js;
    }

    public static String getClearAdDivJsByClass(Context context) {
        String js = "javascript:function hideAd() {";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDivClass);
        for (int i = 0; i < adDivs.length; i++) {
            //通过div的id属性删除div元素
            //js += "var adDiv"+i+"= document.getElementById('"+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
            //通过div的class属性隐藏div元素
            js += "var adDiv" + i + "= document.getElementsByClassName('" + adDivs[i] + "');if(adDiv" + i + " != null)" +
                    "{var x; for (x = 0; x < adDiv" + i + ".length; x++) {adDiv" + i + "[x].style.display='none';}}";
        }
        js += "}";
        return js;
    }
}
