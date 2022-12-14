package com.itheima.pinda.authority.entity.core;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.pinda.base.entity.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 组织
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pd_core_org")
@ApiModel(value = "Org", description = "组织")
public class Org extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    @Length(max = 255, message = "简称长度不能超过255")
    @TableField(value = "abbreviation", condition = LIKE)
    private String abbreviation;

    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID")
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父级名称¬
     */
    @ApiModelProperty(value = "父级名称")
    @TableField(exist = false)
    private String parentName;

    /**
     * 部门类型 1为分公司，2为一级转运中心 3为二级转运中心 4为网点
     */
    @ApiModelProperty(value = "部门类型 1为分公司，2为一级转运中心 3为二级转运中心 4为网点")
    @TableField("org_type")
    private Integer orgType;

    /**
     * 所属省份id
     */
    @ApiModelProperty(value = "所属省份id")
    @TableField("province_id")
    private Long provinceId;

    /**
     * 所属省份名称
     */
    @ApiModelProperty(value = "所属省份名称")
    @TableField(exist = false)
    private String provinceName;

    /**
     * 所属城市id
     */
    @ApiModelProperty(value = "所属城市id")
    @TableField("city_id")
    private Long cityId;

    /**
     * 所属城市名称
     */
    @ApiModelProperty(value = "所属城市名称")
    @TableField(exist = false)
    private String cityName;

    /**
     * 所属区县id
     */
    @ApiModelProperty(value = "所属区县id")
    @TableField("county_id")
    private Long countyId;

    /**
     * 所属区县名称
     */
    @ApiModelProperty(value = "所属区县名称")
    @TableField(exist = false)
    private String countyName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Length(max = 255, message = "详细地址长度不能超过255")
    @TableField(value = "address", condition = LIKE)
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @Length(max = 255, message = "经度长度不能超过255")
    @TableField(value = "longitude", condition = LIKE)
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @Length(max = 255, message = "纬度长度不能超过255")
    @TableField(value = "latitude", condition = LIKE)
    private String latitude;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 255, message = "联系电话长度不能超过255")
    @TableField(value = "contract_number", condition = LIKE)
    private String contractNumber;

    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人id")
    @TableField("manager_id")
    private Long managerId;

    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    @TableField(exist = false)
    private String manager;


    @ApiModelProperty(value = "营业时间")
    @Length(max = 255, message = "营业时间长度不能超过255")
    @TableField(value = "business_hours", condition = LIKE)
    private String businessHours;

    /**
     * 树结构
     */
    @ApiModelProperty(value = "树结构")
    @Length(max = 255, message = "树结构长度不能超过255")
    @TableField(value = "tree_path", condition = LIKE)
    private String treePath;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField("sort_value")
    private Integer sortValue;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Boolean status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "describe_", condition = LIKE)
    private String describe;


    @Builder
    public Org(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
               String name, String abbreviation, Long parentId, String treePath, Integer sortValue,
               Boolean status, String describe, Integer orgType, Long provinceId, Long cityId, Long countyId,
               String address, String longitude, String latitude, String contractNumber) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.name = name;
        this.abbreviation = abbreviation;
        this.parentId = parentId;
        this.treePath = treePath;
        this.sortValue = sortValue;
        this.status = status;
        this.describe = describe;
        this.orgType = orgType;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contractNumber = contractNumber;
    }

}
