package com.ssfw.common.framework.response;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author a
 * @date 2022/7/7
 * @since 2.7.1
 */
@Getter
public abstract class AbstractResponse extends HashMap<String,Object> {

    /**
     * 状态码,-1 失败
     */
    protected int status = 1;

    /**
     * 返回码 0 成功 -1 失败
     */
    protected int code = 0;

    /**
     * 返回数据
     */
    protected Object data;

    /**
     * 返回的数据的总行数
     */
    protected long total;

    /**
     * 错误信息
     */
    protected String errMsg;
    /**
     * 错误详细描述
     */
    protected String errDetail;

    /**
     * 提示信息
     */
    protected String tips;

    @SuppressWarnings("unchecked")
    public <T> T getData() {
        return (T) data;
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {

        super.putAll(map);

        if (map.containsKey("status")){
            this.tips = (map.get("status").toString());
        }
        if (map.containsKey("code")){
            this.code = (Integer.parseInt(map.get("code").toString()));
        }
        if (map.containsKey("total")){
            this.total = (Long.parseLong(map.get("total").toString()));
        }
        if (map.containsKey("data")){
            this.data = (map.get("data"));
        }
        if (map.containsKey("errMsg")){
            this.errMsg = (map.get("errMsg").toString());
        }
        if (map.containsKey("errDetail")){
            this.errDetail = (map.get("errDetail").toString());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void  ifPresent(Consumer<? super T> action) {
        if (null!=data) {
            action.accept((T) data);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (data != null) {
            action.accept((T) data);
        } else {
            emptyAction.run();
        }
    }
}
