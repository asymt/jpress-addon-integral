package com.asymt.addon.resourcesite.service;

import com.asymt.addon.resourcesite.model.IntegralAvailables;
import com.jfinal.plugin.activerecord.Page;
import com.asymt.addon.resourcesite.model.IntegralDetails;
import io.jboot.aop.annotation.Transactional;
import io.jboot.db.model.Columns;

import java.util.List;

public interface IntegralDetailsService  {

    /**
     * 根据主键查找Model
     *
     * @param id
     * @return
     */
    public IntegralDetails findById(Object id);


    /**
     * 根据 Columns 查找单条数据
     *
     * @param columns
     * @return
     */
    public IntegralDetails findFirstByColumns(Columns columns);

    /**
     * 根据 Columns 查找单条数据
     *
     * @param columns
     * @param orderBy
     * @return
     */
    public IntegralDetails findFirstByColumns(Columns columns, String orderBy);


    /**
     * 查找全部数据
     *
     * @return
     */
    public List<IntegralDetails> findAll();


    /**
     * 根据 Columns 查找数据
     *
     * @param columns
     * @return
     */
    public List<IntegralDetails> findListByColumns(Columns columns);


    /**
     * 根据 Columns 查找数据
     *
     * @param columns
     * @param orderBy
     * @return
     */
    public List<IntegralDetails> findListByColumns(Columns columns, String orderBy);

    /**
     * 根据 Columns 查找数据
     *
     * @param columns
     * @param count
     * @return
     */
    public List<IntegralDetails> findListByColumns(Columns columns, Integer count);

    /**
     * 根据 Columns 查找数据
     *
     * @param columns
     * @param orderBy
     * @param count
     * @return
     */
    public List<IntegralDetails> findListByColumns(Columns columns, String orderBy, Integer count);


    /**
     * 根据提交查询数据量
     *
     * @param columns
     * @return
     */
    public long findCountByColumns(Columns columns);


    /**
     * 根据ID 删除model
     *
     * @param id
     * @return
     */
    public boolean deleteById(Object id);


    /**
     * 删除
     *
     * @param model
     * @return
     */
    public boolean delete(IntegralDetails model);


    /**
     * 根据 多个 id 批量删除
     *
     * @param ids
     * @return
     */
    public boolean batchDeleteByIds(Object... ids);


    /**
     * 保存到数据库
     *
     * @param model
     * @return id if success
     */
    public Object save(IntegralDetails model);


    /**
     * 保存或更新
     *
     * @param model
     * @return id if success
     */
    public Object saveOrUpdate(IntegralDetails model);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    public boolean update(IntegralDetails model);


    /**
     * 分页
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<IntegralDetails> paginate(int page, int pageSize);


    /**
     * 分页
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<IntegralDetails> paginateByColumns(int page, int pageSize, Columns columns);


    /**
     * 分页
     *
     * @param page
     * @param pageSize
     * @param columns
     * @param orderBy
     * @return
     */
    public Page<IntegralDetails> paginateByColumns(int page, int pageSize, Columns columns, String orderBy);


    /**
     * 添加积分
     * @param integralDetails
     * @return
     */
    Object add(IntegralDetails integralDetails);

    Object add(Integer userId, Integer integral, String remark);

    boolean addExpireIntegralDetails(List<IntegralAvailables> expireIntegrals);

    @Transactional
    void consumeIntegral(Integer userId, Integer integral, String remark);
}
