package training.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName roilkaEmployeeInfo
 * @Description  SAP系统途虎员工对应实体
 * @Author zhanghui1
 * @Date 2020/1/3 10:00
 **/
@Data
public class EmployeeInfo implements Serializable {

    /**
     *  hr 员工工号
     */
    private Integer id;
    /**
     *  员工邮箱（通用登录账号）
     */
    private String email;
    /**
     *  员工手机号
     */
    private String mobile;
    /**
     *  员工名称
     */
    private String name;
    /**
     *  一级部门
     */
    private String businessUnit;
    /**
     *  一级部门名称
     */
    private String businessUnitName;
    /**
     *  二级部门
     */
    private String division;
    /**
     *  二级部门名称
     */
    private String divisionName;
    /**
     *  三级部门
     */
    private String department;
    /**
     *  三级部门名称
     */
    private String departmentName;
    /**
     *  四级部门
     */
    private String fourDepartment;
    /**
     *  四级部门名称
     */
    private String fourDepartmentName;
    /**
     *  五级部门
     */
    private String fiveDepartment;
    /**
     *  五级部门名称
     */
    private String fiveDepartmentName;
    /**
     *  员工入职时间
     */
    private String startDate;
    /**
     *  员工关联
     */
    private Integer accountId;




}
