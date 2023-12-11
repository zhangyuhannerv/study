package com.study.gulimall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangYuhan
 * @email zhangyuhannerv@gmail.com
 * @date 2023-12-11 16:50:26
 */
@Data
@TableName("undo_log")
public class UndoLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long branchId;
    /**
     *
     */
    private String xid;
    /**
     *
     */
    private String context;
    /**
     *
     */
//	private Longblob rollbackInfo;
    private Byte rollbackInfo;
    /**
     *
     */
    private Integer logStatus;
    /**
     *
     */
    private Date logCreated;
    /**
     *
     */
    private Date logModified;
    /**
     *
     */
    private String ext;

}
