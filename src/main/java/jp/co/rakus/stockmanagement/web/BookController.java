package jp.co.rakus.stockmanagement.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.service.BookService;

/**
 * 書籍関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/book")
@Transactional
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public BookForm setUpForm() {
		return new BookForm();
	}
	
	@ModelAttribute
	public InsertForm setUpForm2() {
		return new InsertForm();
	}
	
	/**
	 * 書籍リスト情報を取得し書籍リスト画面を表示します.
	 * @param model モデル
	 * @return 書籍リスト表示画面
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		return "book/list";
	}
	
	/**
	 * 書籍詳細情報を取得し書籍詳細画面を表示します.
	 * @param id 書籍ID
	 * @param model　モデル
	 * @return　書籍詳細画面
	 */
	@RequestMapping(value = "show/{bookId}")
	public String show(@PathVariable("bookId") Integer id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		return "book/show";
	}
	
	/**
	 * 書籍更新を行います.
	 * @param form フォーム
	 * @param result リザルト情報
	 * @param model　モデル
	 * @return　書籍リスト画面
	 *//*
	@RequestMapping(value = "update")
	public String update(@Validated BookForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return show(form.getId(), model);
		}
		Book book = bookService.findOne(form.getId());
		book.setStock(form.getStock());
		bookService.update(book);
		return list(model);
	}
	*/
	
	@RequestMapping("/insert")
	public String insert() {		
		return "book/insert";
	}
	
	
	@RequestMapping("/save")
	public String save(@Validated InsertForm form,BindingResult result,Model model) throws ParseException {
		
		 //string を　date　へ変換する処理
		 String date = form.getSaledate();
		 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		 Date formatDate = sdf2.parse(date);// String をdate に変換 
		
		Book book = new Book();
		BeanUtils.copyProperties(form, book); 
		
		Book maxBook = bookService.findByMaxId();//最新のidを取得
		Integer maxId = (maxBook.getId()+1);
		
		java.io.File file  = new java.io.File(form.getImage().getOriginalFilename());
		
		String fileName = form.getImage().getOriginalFilename();
		System.out.println("fileName="+fileName);
		
		try {
			   form.getImage().transferTo( new File("src/main/webapp/img/"+fileName) );
			   
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
   	
		
		
		
		book.setSaledate(formatDate);//formはString,domainはdateなので、自分でセット。
		book.setId(maxId);
		book.setImage(form.getImage().getOriginalFilename());
		
		bookService.save(book);
		
		return "book/list";
	}
	
	

	
	
	

}
