package jp.co.rakus.stockmanagement.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
	 * @param <BCryptPasswordEncoder>
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return ログイン画面
	 */
	@RequestMapping(value = "create")
	@Transactional
	public <BCryptPasswordEncoder> String create(@Validated MemberForm form,BindingResult result, 
			Model model) {
		
		
		Member memberanswer = memberService.findByEmail(form.getMailAddress());
		if(memberanswer != null) {
			String message = "既に登録されています";
			result.rejectValue("mailAddress", null, message);
		}
		
//		System.out.println(form.getPassword() + "new" + form.getConfirm_password());
		
		if( !(form.getPassword().equals(form.getConfirm_password())) ) {
			String confirm_password = "パスワードが違います";
			result.rejectValue("confirm_password", null,confirm_password);
		}
		
		
		if(result.hasErrors()) {
			return "/member/form";
		}
		
		
	    final String salt = BCrypt.gensalt();
	    System.out.println("生成したソルト = " + salt);
	 
	    final String hashedPasswordWithSalt = BCrypt.hashpw(form.getPassword(), salt);
	    System.out.println("ハッシュ化したパスワード = " + hashedPasswordWithSalt);
	 
	    // hashedPasswordWithSaltをDBに格納
	    // ここでは省略
	    // something(hashedPasswordWithSalt);
	    
	    // DBからhashedPasswordWithSaltを取り出す
	    // ここでは省略。取り出したことにして代入
	    final String passwordInDB = hashedPasswordWithSalt;
	 
	    // パスワードの検証
	    final boolean checkResult = BCrypt.checkpw("hogePassword1234", passwordInDB);
	    System.out.println("hogePassword1234の検証結果 = " + checkResult);
	 
	    // 誤ったパスワードの場合
	    final boolean checkResult2 = BCrypt.checkpw("hogePassword12345678", passwordInDB);
	    System.out.println("hogePassword12345678の検証結果 = " + checkResult2);
	 
		
		Member member = new Member();
		member.setPassword(hashedPasswordWithSalt);
		member.setName(form.getName());
		member.setMailAddress(form.getMailAddress());
		
		
		//BeanUtils.copyProperties(form, member);
		memberService.save(member);
		
		
		System.out.println("pass="+member.getPassword());
		
	
		return "redirect:/";
	}
	
}




