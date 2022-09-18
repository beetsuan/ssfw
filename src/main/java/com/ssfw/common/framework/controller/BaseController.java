package com.ssfw.common.framework.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.common.framework.request.Pagination;
import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.framework.service.BaseService;
import com.ssfw.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author a
 * @Description: 模块公共controller<br>
 */
@Slf4j
public class BaseController<T> {

    private IService<T> service;
    private BaseService<T> baseService;

    public static final String PRODUCE_UTF8_JSON ="application/json;charset=UTF-8";

    protected static final String PAGE_REDIRECT="redirect:";

    protected final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    protected final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    @Autowired
    protected HttpServletRequest request;

    public BaseController(IService<T> service) {
        this.service = service;
    }

    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }


    protected HttpSession getSession() {
        return request.getSession();
    }

    protected void setSessionAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    protected Object getSessionAttribute(@NotNull String name) {

        return getSession().getAttribute(name);
    }

    protected <T> T getSessionAttribute(@NotNull Class<T> clazz, @NotNull String name) {

        final Object o = getSession().getAttribute(name);
        if (null != o) {
            return (T) o;
        }
        return null;
    }


    protected Page<T> page(LambdaQueryWrapper<T> wrapper){
        Pagination<T> pagination = new Pagination<>(request);
        Page<T> page = pagination.getPage();
        if (null!= baseService){
            return baseService.page(page, wrapper);
        }
        return service.page(page, wrapper);
    }

    protected Page<T> page(QueryWrapper<T> wrapper){
        Pagination<T> pagination = new Pagination<>(request);
        Page<T> page = pagination.getPage();
        if (null!= baseService){
            return baseService.page(page, wrapper);
        }
        return service.page(page, wrapper);
    }

    /**
     * 分页查询
     * @param wrapper LambdaQueryWrapper
     * @param mapper AssemblerFacade
     * @return ResponseVo
     */
    protected ResponseVo pageQuery(LambdaQueryWrapper<T> wrapper, AssemblerFacade mapper){

        Page<T> page = page(wrapper);

        return ResponseVo.success(mapper.entityToVO(page.getRecords())).setTotal(page.getTotal());
    }

    /**
     * 分页查询
     * @param wrapper QueryWrapper
     * @param mapper AssemblerFacade
     * @return ResponseVo
     */
    protected ResponseVo pageQuery(QueryWrapper<T> wrapper, AssemblerFacade mapper){

        Page<T> page = page(wrapper);
        return ResponseVo.success(mapper.entityToVO(page.getRecords())).setTotal(page.getTotal());
    }

    /**
     * 生产下载或打开文件的 ResponseEntity
     *
     * @param isDownload  是否下载文件
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @param fileBytes   文件内容
     * @download
     * @return ResponseEntity<Object>
     */
    @NotNull
    protected ResponseEntity<Object> toResponseEntity(HttpServletRequest request,boolean isDownload, String fileName, String contentType, byte[] fileBytes) {

        //文件内容类型
        String contentDisposition = getContentDisposition(request, isDownload, fileName);
        if (isDownload){
            contentType = "application/octet-stream";
        }
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_LENGTH, fileBytes.length + "")
                .header("Connection", "close");
        return bodyBuilder.body(fileBytes);
    }

    /**
     * 生产下载或打开文件的 ResponseEntity
     * @apiNote 将以流的方式传输至前端，可用于大文件下载、视频播放等
     * @param isDownload 是否下载文件
     * @param fileName 文件名称
     * @param contentType 内容类型
     * @param inputStream 文件内容
     * @download
     * @return ResponseEntity<InputStreamResource>
     */
    @NotNull
    protected ResponseEntity<InputStreamResource> toResponseEntity(HttpServletRequest request, boolean isDownload, String fileName, String contentType, long contentLength, InputStream inputStream) {

        //文件内容类型
        String contentDisposition = getContentDisposition(request, isDownload, fileName);
        if (isDownload){
            contentType = "application/octet-stream";
        }

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_LENGTH, contentLength + "")
                .header("Connection", "close");


        return bodyBuilder.body(new InputStreamResource(inputStream));
    }

    private String getContentDisposition(HttpServletRequest request, boolean isDownload, String fileName){

        try {
            //获得浏览器信息并转换为大写
            String userAgent = request.getHeader("User-Agent");
            if (StringUtil.isNotNull(userAgent)){
                String agent = userAgent.toUpperCase();
                //IE浏览器和Edge浏览器，名称乱码
                if (agent.indexOf("MSIE") > 0 || (agent.indexOf("GECKO")>0 && agent.indexOf("RV:11")>0)) {
                    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
                } else {  //其他浏览器
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                }
            }
        }catch (UnsupportedEncodingException e){
            log.error("文件名转码异常，文件名：{}",fileName);
        }
        //文件内容类型
        String contentDisposition;
        if (isDownload){
            contentDisposition = "attachment; fileName=" +  fileName;
        }else {
            contentDisposition = "fileName=" +  fileName;
        }
        return contentDisposition;
    }
}
