package jp.co.rakus.stockmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * メンバー情報を保持するエンティティ.
 * @author rakus
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	/** id(主キー) */
	private Integer id;
	/** 名前 */
	private String name;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
}
