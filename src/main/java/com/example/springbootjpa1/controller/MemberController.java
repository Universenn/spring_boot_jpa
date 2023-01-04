package com.example.springbootjpa1.controller;

import com.example.springbootjpa1.domain.entity.Address;
import com.example.springbootjpa1.domain.entity.Member;
import com.example.springbootjpa1.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); //
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // 오류가 담겨서 실행 된다

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setUserName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // home 으로 다시 넘기기
    }

}
