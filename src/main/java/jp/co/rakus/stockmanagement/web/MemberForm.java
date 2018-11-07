package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * メンバー関連のリクエストパラメータが入るフォーム.
 * @author igamasayuki
 *
 */
public class MemberForm {
	/** 名前 */
	@NotBlank(message = "名前は必須項目です")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "アドレスは必須項目です")
	@Email(message="メールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "パスワードは必須項目です")
	@Size(min=1,max=50,message="パスワードは1文字以上、50文字以内で記載してください")
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
