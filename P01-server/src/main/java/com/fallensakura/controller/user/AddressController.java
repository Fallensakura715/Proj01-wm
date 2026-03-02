package com.fallensakura.controller.user;

import com.fallensakura.entity.Address;
import com.fallensakura.result.Result;
import com.fallensakura.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@RestController
@RequestMapping("/user/addressBook")
@RequiredArgsConstructor
@Tag(name = "C端-地址接口")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("")
    @Operation(summary = "新增地址")
    public Result<?> addAddress(@RequestBody Address address) {
        addressService.addAddress(address);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的所有地址信息")
    public Result<List<Address>> getAllAddresses() {
        return Result.success(addressService.getAllAddresses());
    }

    @GetMapping("/default")
    @Operation(summary = "查询默认地址")
    public Result<Address> getDefaultAddress() {
        return Result.success(addressService.getDefaultAddress());
    }

    @PutMapping("")
    @Operation(summary = "根据id修改地址")
    public Result<?> updateById(@RequestBody Address address) {
        addressService.updateAddressById(address);
        return Result.success();
    }

    @DeleteMapping("")
    @Operation(summary = "根据id删除地址")
    public Result<?> deleteById(@RequestParam Long id) {
        addressService.deleteAddressById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址")
    public Result<Address> selectById(@PathVariable Long id) {
        return Result.success(addressService.selectAddressById(id));
    }

    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    public Result setDefaultAddress(@RequestBody Address address) {
        addressService.setDefaultAddress(address.getId());
        return Result.success();
    }
}
