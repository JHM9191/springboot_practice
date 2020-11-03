package com.rest.springboot_practice.controller.v1;

import com.rest.springboot_practice.entity.User;
import com.rest.springboot_practice.model.response.CommonResult;
import com.rest.springboot_practice.model.response.ListResult;
import com.rest.springboot_practice.model.response.SingleResult;
import com.rest.springboot_practice.repo.UserJpaRepo;
import com.rest.springboot_practice.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController                     // 결과값을 JSON으로 출력
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 Service

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과 데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단일 조회", notes = "userId로 회원을 조회한다.")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        // 결과 데이터가 단일건인 경우 getBasicResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElse(null));
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/user")
    public SingleResult<User> save(
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
        @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
        @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
        @ApiParam(value = "회원이름", required = true) @RequestParam String name) {

        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다.")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        userJpaRepo.deleteById(msrl);
        return responseService.getSuccessResult();
    }
}

