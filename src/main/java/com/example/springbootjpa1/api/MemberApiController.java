package com.example.springbootjpa1.api;

import com.example.springbootjpa1.domain.entity.Member;
import com.example.springbootjpa1.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest createMemberRequest) {

        Member member = new Member();
        member.setName(createMemberRequest.getName());

        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest updateMemberRequest) {
        memberService.update(id, updateMemberRequest.getName());

        Member one = memberService.findOne(id);
        return new UpdateMemberResponse(one.getId(), one.getName());
    }

    @GetMapping("api/v1/members")
    public List<Member> getMemberV1() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("api/v2/members")
    public Result getMemberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getId()))
                .collect(Collectors.toList());

        return new Result<>(collect.size(),collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T result;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private Long id;
    }




    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }


    @Data

    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
