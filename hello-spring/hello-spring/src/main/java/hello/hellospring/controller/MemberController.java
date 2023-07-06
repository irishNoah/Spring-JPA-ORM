package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // 회원 컨트롤러가 회원서비스와 회원 리포지토리를 사용할 수 있게 의존관계를 준비하자.
    private final MemberService memberService;

    /*
    생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 이렇게
    객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 한다.
     */
    @Autowired
    public MemberController(MemberService memberService){
        // 여기서는 MemberService에 DI를 해주었다.
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // 회원 가입
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 회원 가입
    public String create(MemberForm form){
        // 입력창 통해서 name 입력 받은 것을 getName() 통해서 받아오고 setName() 통해서 세트한다.
        Member member = new Member();
        member.setName(form.getName());

        // 회원가입 진행
        memberService.join(member);

        // System.out.println("member = " + member.getName());

        // 회원가입 끝나면 home화면으로 돌려보내기
        return "redirect:/";
    }

    @GetMapping("/members") // 회원 목록
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
