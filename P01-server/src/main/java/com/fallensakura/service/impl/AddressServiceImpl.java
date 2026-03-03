package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fallensakura.context.BaseContext;
import com.fallensakura.entity.Address;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.AddressMapper;
import com.fallensakura.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;

    private Long getUserId() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }
        return userId;
    }

    @Override
    public void addAddress(Address address) {

        address.setId(null);
        address.setUserId(getUserId());

        Object target = addressMapper.selectList(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getUserId, getUserId())
        );

        if (target == null) {
            address.setIsDefault(1);
        }

        addressMapper.insert(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        Long userId = getUserId();

        return addressMapper.selectList(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, userId)
                .orderByDesc(Address::getIsDefault));
    }

    @Override
    public Address getDefaultAddress() {
        Long userId = getUserId();

        return addressMapper.selectOne(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, 1));
    }

    @Override
    public void updateAddressById(Address address) {

        if (address.getId() == null) {
            throw new BusinessException("地址ID不能为空");
        }

        Address target = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getId, address.getId())
                        .eq(Address::getUserId, address.getUserId())
        );

        if (target == null) {
            throw new BusinessException("地址不存在或没权限");
        }

        target.setUserId(getUserId());
        addressMapper.updateById(address);
    }

    @Override
    public void deleteAddressById(Long id) {
        int rows = addressMapper.delete(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getId, id)
                        .eq(Address::getUserId, getUserId())
        );
        if (rows == 0) throw new BusinessException("地址不存在或无权限");
    }

    @Override
    public Address selectAddressById(Long id) {

        Address address = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getId, id)
                        .eq(Address::getUserId, getUserId())
        );

        if (address == null) throw new BusinessException("地址不存在或无权限");

        return address;
    }

    @Override
    public void setDefaultAddress(Long id) {
        if (id == null) {
            throw new BusinessException("地址ID不能为空");
        }

        Long userId = getUserId();

        Address target = addressMapper.selectOne(
                Wrappers.<Address>lambdaQuery()
                        .eq(Address::getId, id)
                        .eq(Address::getUserId, userId)
        );

        if (target == null) {
            throw new BusinessException("地址不存在或无权限操作");
        }

        Address reset = new Address();
        reset.setIsDefault(0);
        addressMapper.update(Wrappers.<Address>lambdaUpdate()
                .eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, 1));

        Address set = new Address();
        set.setIsDefault(1);
        set.setId(id);
        addressMapper.updateById(set);

    }
}
