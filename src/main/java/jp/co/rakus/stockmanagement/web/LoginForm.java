package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * ログイン関連のリクエストパラメータが入るフォーム.
 * @author igamasayuki
 *
 */
@Data
public class LoginForm {
	/** メールアドレス */
	@NotNull(message = "値を入力してください")
	private String mailAddress;
	/** パスワード */
	@NotNull(message = "値を入力してください")
	private String password;
}
