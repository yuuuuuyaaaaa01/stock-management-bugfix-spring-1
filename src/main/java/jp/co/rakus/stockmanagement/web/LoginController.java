package jp.co.rakus.stockmanagement.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.service.MemberService;

/**
 * ログイン関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private MemberService memberService;

	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面を表示します.
	 * @return ログイン画面
	 */
	@RequestMapping
	public String index() {
		return "loginForm";
	}
	
	@Autowired
	public HttpSession session;
	
	/**
	 * ログイン処理を行います.
	 * @param form　フォーム
	 * @param result　リザルト
	 * @param model　モデル
	 * @return　ログイン成功時：書籍リスト画面
	 */
	@RequestMapping(value = "/login")
	public String login(@Validated LoginForm form,
			BindingResult result, Model model) {
	
		String mailAddress = form.getMailAddress();
		
		//ログインフォームから送られてきたメールアドレスを基に、該当する一意のメンバーを引っ張ってくる
		Member getMemberByEmail = memberService.findByEmail(form.getMailAddress());
		//getMemberByEmailを基に、DBの暗号化されたパスワードを引っ張ってくる
		String dbHashpass = getMemberByEmail.getPassword();
		//暗号化したパスワード認証は、falseにセットする
		boolean checkResult = false;
		//ログインした暗号化されていないパスワードと、DBの暗号化されたパスワードを比較。あっていればtrueを返却する
		// true の時は、メールアドレスとパスワードのチェックを行う
		if( checkResult = BCrypt.checkpw(form.getPassword(), dbHashpass)) {
		Member member = memberService.findOneByMailAddressAndPassword(mailAddress, dbHashpass);
		if (member == null) {
			ObjectError error = new ObjectError("loginerror", "メールアドレスまたはパスワードが違います。");
            result.addError(error);
			return index();
		}		
		session.setAttribute("member",member);
		}else {
				String password = "パスワードが違います";
				result.rejectValue("password",null,password);
			return "redirect:/login";
		}
		if (result.hasErrors()){
			return index();
		}
		
		return "redirect:/book/list";
	}
}
