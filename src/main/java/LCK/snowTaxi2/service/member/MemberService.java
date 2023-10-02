package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.member.LoginResponseDto;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.dto.pot.MyPotsResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    // 멤버 생성
    public void createMember(MemberRequestDto dto);

    // 로그인 가능한지 확인
    public LoginResponseDto findMember(MemberRequestDto dto);

    // 유효한 닉네임인지 확인
    public boolean isMemberNickname(String nickname);

    // 유효한 이메일인지 확인 (이미 가입했는지)
    public boolean isMemberEmail(String email);

    // 참여중인 팟 아이디 변경
    public void setParticipatingPotId(Long memberId, long taxiPotId);

    // 참여중인 팟 아이디 조회
    public long getParticipatingPotId(Long memberId);

    // 비밀번호 변경
    public void changePassword(MemberRequestDto dto);

    public List<MyPotsResponseDto> getMyPots(Long memberId);

}
