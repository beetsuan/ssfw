package com.ssfw.common.log.constant;

/**
 * 操作方式
 * @author a
 */

public enum ActionTypeEnum {

    /**
     * 默认方法
     */
    DEFAULT(0,"",4)
    ,ADD(1, "新增",1)
    ,CREATE(2,"新建",1)
    ,EDIT(3,"编辑",2)
    ,UPDATE(4,"修改",2)
    ,UPGRADE(5,"更新",2)
    ,DELETE(6,"删除",3)
    ,REMOVE(7,"移除",3)
    ,MOVE(8,"移动",2)
    ,PUBLISH(9,"发布",2)
    ,QUERY(10,"查询",4)
    ,EXECUTE(11,"执行",4)
    ,EXPORT(12,"导出",4)
    ,IMPORT(13,"导入",4)
    ,UPLOAD(14,"上传",1)
    ,DOWNLOAD(15,"下载",4)
    ,SYNC(16,"数据同步",4)
    ,CHECK(17,"检查",2)
    ,REVIEW(18,"审核",2)
    ,INSPECTION(19,"审查",2)
    ,CANCEL(20,"注销",2);
    private int index;
    private String text;
    private int type;

    public static int TYPE_ADD = 1;
    public static int TYPE_UPDATE = 2;
    public static int TYPE_DELETE = 3;
    public static int TYPE_QUERY = 4;

    ActionTypeEnum(int index, String text,int type) {
        this.index = index;
        this.text =text;
        this.type = type;
    }

    public int index() {
        return this.index;
    }

    public String text() {
        return this.text;
    }

    public int type(){
        return this.type;
    }

    public ActionTypeEnum get(int value) {
        for (ActionTypeEnum type : ActionTypeEnum.values()) {
            if (type.index() == value){
                return  type;
            }
        }
        return null;
    }
}
