package com.ssfw.common.framework.response;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssfw.common.exception.ExceptionParser;
import com.ssfw.common.framework.dto.ResultDto;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import com.ssfw.common.util.JsonUtil;
import java.util.Map;

/**
 *
 * @author a
 */
public class ResponseVo extends AbstractResponse {


    @Override
    public String toString() {
        return "ResponseVO{}";
    }


    public ResponseVo(){
        super();
        this.setCode(0);
    }

    private void setError(ResponseVo vo){
        vo.setStatus(-1);
        if( vo.getCode() == 0 ){
            vo.setCode(-1);
        }
    }

    public static ResponseVo of(long total){
        ResponseVo vo = empty();
        vo.setTotal(total);
        return vo;
    }

    public static ResponseVo success(){

        return empty().setTips("操作成功");
    }

    public static ResponseVo success(Object data){
        ResponseVo model = empty();
        model.setData(data);
        return model;
    }

    public static ResponseVo of(ResultDto dto){
        ResponseVo vo = empty();
        if (dto.hasError()){
            vo.setErrMsg(dto.getErrMsg());
        }
        if (null != dto.getEntities()){
            vo.setData(dto.getEntities());
        }
        if (null != dto.getEntity()){
            vo.setData(dto.getEntity());
        }
        if (dto.getErrCode() != null) {
            vo.put("errCode",dto.getErrCode());
        }
        return vo;
    }

    public static ResponseVo of(ValueObjectOfEntity vo){

        return ofData(vo);
    }

    public static ResponseVo ofData(Object data){
        ResponseVo vo = empty();
        vo.setData(data);
        return vo;
    }



    public static ResponseVo of(Map<String,?> map){
        ResponseVo vo = empty();
        vo.putAll(map);
        return vo;
    }

    public static ResponseVo failure(String errMsg){
        ResponseVo vo = empty();
        vo.setErrMsg(errMsg);
        if (null != errMsg){
            empty().setError(vo);
        }
        return vo;
    }

    public static ResponseVo failureToAdd() {
        return failure("没有新增成功");
    }

    public static ResponseVo failureToUpdate() {
        return failure("没有编辑成功");
    }

    public static ResponseVo failureToDelete() {
        return failure("没有删除成功");
    }

    public static ResponseVo ofTips(String tips){
        ResponseVo model = empty();
        model.setTips(tips);
        model.setCode(0);
        return model;
    }

    public static ResponseVo failure(Exception ex){

        ResponseVo model = ExceptionParser.getInstance().parse(ex);
        if (null != model.getErrMsg()){
            model.setStatus(500);
            model.setCode(-1);
        }
        return of(model);
    }

    public static ResponseVo success(Page<?> page){
        ResponseVo vo = empty();
        vo.setData(page.getRecords());
        vo.setTotal(page.getTotal());
        return vo;
    }

    public static ResponseVo empty() {
        return new ResponseVo();
    }

    public String toJson() {
        return JsonUtil.obj2String(this);
    }

    public ResponseVo setTips(String tips) {
        this.tips = tips;
        this.put("tips",tips);
        return this;
    }

    public ResponseVo setStatus(int status) {
        this.status = status;
        this.put("status",status);
        return this;
    }

    public ResponseVo setCode(int code) {
        this.code = code;
        this.put("code",code);
        return this;
    }

    public ResponseVo setData(Object data) {
        this.data = data;
        if (null != data){
            this.put("data",data);
        }else{
            this.remove("data");
        }
        return this;
    }

    public ResponseVo setTotal(long total) {
        this.total = total;
        this.put("total",total);
        return this;
    }

    public ResponseVo setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        if (null != errMsg){
            this.put("errMsg",errMsg);
            this.setError(this);
        }else{
            this.remove("errMsg");
        }
        return this;
    }

    public ResponseVo setErrDetail(String errDetail) {
        this.errDetail = errDetail;
        if (null != errDetail){
            this.put("errDetail",errDetail);
            this.setError(this);
        }else{
            this.remove("errDetail");
        }
        return this;
    }
}
