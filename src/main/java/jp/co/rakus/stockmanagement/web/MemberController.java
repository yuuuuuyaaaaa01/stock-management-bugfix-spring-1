package jp.co.rakus.stockmanagement.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.service.MemberService;

/**
 * メンバー関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/member")
@Transactional
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private HttpSession session;
	
	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public MemberForm setUpForm() {
		return new MemberForm();
	}

	/**
	 * メンバー情報登録画面を表示します.
	 * @return メンバー情報登録画面
	 */
	@RequestMapping(value = "form")
	public String form() {
		return "/member/form";
	}
	
	/**
	 * メンバー情報を登録します.
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return ログイン画面
	 */
	@RequestMapping(value = "create")
	@Transactional
	public String create(@Validated MemberForm form,BindingResult result, 
			Model model) {
		
		if(result.hasErrors()) {
			return "/member/form";
		}
		
		Member member = new Member();
		BeanUtils.copyProperties(form, member);
		
		List<Member> memberanswer = memberService.findByEmail(member.getMailAddress());
		
		System.out.println(member.getPassword() + "new" + member.getConfirm_password());
		
		if( !(member.getPassword().equals(member.getConfirm_password())) ) {
			String confirm_password = "パスワードが違います";
			result.rejectValue("confirm_password", null,confirm_password);
			return "/member/form";
		}
		
		if(memberanswer.isEmpty() ) {
		memberService.save(member);
		}else{
			String message = "既に登録されています";
			result.rejectValue("mailAddress", null, message);
		}
		
	
		return "/member/form";
	}
	
}
