package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createMember(MemberRequestDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        memberRepository.save(member);
    }

    public int validationMember(MemberRequestDto dto) {
        Member loginMember = memberRepository.findByEmail(dto.getEmail());

        // 해당 이메일의 유저가 존재하지 않습니다.
        if(loginMember==null) {
            HttpStatus.NOT_FOUND.value();
        }
        // 비밀번호가 일치하지 않습니다.
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), loginMember.getPassword())) {
            return HttpStatus.CONFLICT.value();
        }

        return HttpStatus.OK.value();
    }

    // 현재 참여중인 택시 팟 아이디 변경
    public void setParticipatingPotId(Long memberId, long taxiPotId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );
        member.setParticipatingPotId(taxiPotId);
        memberRepository.saveAndFlush(member);
    }

    public long getParticipatingPotId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );
        return member.getParticipatingPotId();
    }

    public boolean checkNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if (member == null) return false;
        else return true;
    }

    public boolean checkEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) return false;
        else  return true;
    }

}