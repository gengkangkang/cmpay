package com.cmpay.boss.entity.vo;

/**
 * 
 * 
 * @author xiaoxiang
 * @version $Id: RoleVo.java, v 0.1 2016年9月7日 上午9:42:43 Administrator Exp $
 */
public class RoleVo {

    private long   id;
    private String name;
    private char   status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
