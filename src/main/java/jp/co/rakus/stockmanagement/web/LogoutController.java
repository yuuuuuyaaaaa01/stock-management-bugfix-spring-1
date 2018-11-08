package jp.co.rakus.stockmanagement.web;

import jp.co.rakus.stockmanagement.domain.Member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * ログアウト関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping(value = "/logout")
@SessionAttributes("member")
public class LogoutController {

	/**
	 * セッション情報に含まれるMemberオブジェクトをクリアします.
	 * @param member Sessionに入っているメンバー情報
	 * @param sessionStatus　セッションステータス
	 * @return　ログイン画面
	 */
	@RequestMapping(value = "sessionInvalidate")
	public String sessionInvalidate(Member member
			, SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
