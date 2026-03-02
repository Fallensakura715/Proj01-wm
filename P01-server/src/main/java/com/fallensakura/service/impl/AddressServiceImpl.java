package com.fallensakura.service.impl;

import com.fallensakura.entity.Address;
import com.fallensakura.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void addAddress(Address address) {

    }

    @Override
    public List<Address> getAllAddresses() {
        return null;
    }

    @Override
    public Address getDefaultAddress() {
        return null;
    }

    @Override
    public void updateAddressById(Address address) {

    }

    @Override
    public void deleteAddressById(Long id) {

    }

    @Override
    public Address selectAddressById(Long id) {
        return null;
    }

    @Override
    public void setDefaultAddress(Long id) {

    }
}
