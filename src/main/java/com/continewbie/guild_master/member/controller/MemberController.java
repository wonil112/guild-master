package com.continewbie.guild_master.member.controller;

import com.continewbie.guild_master.member.dto.MemberDto;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.mapper.MemberMapper;
import com.continewbie.guild_master.member.service.MemberService;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
public class MemberController {
    //    회원가입시 초기 URL
    private final static String SIGN_UP_URL = "/signup";

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = memberMapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        URI location = UriCreator.createUri(SIGN_UP_URL, createdMember.getMemberId());
        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,   @RequestBody @Valid MemberDto.Patch requestBody){
        requestBody.setMemberId(memberId);
        Member member = memberMapper.memberPatchDtoToMember(requestBody);
        Member updatedMember = memberService.updateMember(member);
        return new ResponseEntity<>(memberMapper.memberToMemberResponse(updatedMember),HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}