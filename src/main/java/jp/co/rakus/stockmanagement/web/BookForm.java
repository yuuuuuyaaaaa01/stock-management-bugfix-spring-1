package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 書籍関連のリクエストパラメータが入るフォーム.
 * @author igamasayuki
 *
 */
@Data
public class BookForm {
	/** id  */
    @NotNull
	private Integer id;
	/** 在庫  */
    @NotNull(message = "値を入力してください")
	private Integer stock;
}
