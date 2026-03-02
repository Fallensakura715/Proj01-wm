package com.fallensakura.service;

import com.fallensakura.entity.Address;
import com.fallensakura.result.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface AddressService {
    /**
     * 新增地址
     * @param address
     */
    void addAddress(Address address);

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    List<Address> getAllAddresses();

    /**
     * 查询默认地址
     * @return
     */
    Address getDefaultAddress();

    /**
     * 根据id修改地址
     * @param address
     */
    void updateAddressById(Address address);

    /**
     * 根据id删除地址
     * @param id
     */
    void deleteAddressById(Long id);

    /**
     * 根据id查询地址
     * @param id
     */
    Address selectAddressById(Long id);

    /**
     * 设置默认地址
     * @param id
     */
    void setDefaultAddress(Long id);
}
