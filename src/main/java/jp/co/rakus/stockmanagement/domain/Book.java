package jp.co.rakus.stockmanagement.domain;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 書籍情報を保持するエンティティ.
 * @author rakus
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	/** id */
	private Integer id;
	/** 書籍名 */
	private String name;
	/** 著者 */
	private String author;
	/** 出版社 */
	private String publisher;
	/** 価格 */
	private int price;
	/** ISBNコード */
	private String isbncode;
	/** 発売日 */
	private Date saledate;
	/** 説明 */
	private String explanation;
	/** 画像 */
	private String image;
	/** 在庫数 */
	private Integer stock;
}
