package jp.co.rakus.stockmanagement.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	/**
	 * 書籍の新規登録.
	 * 
	 * @param form 新規登録する書籍の情報が送られてくる.
	 * @param result エラーの結果が複数入る.
	 * 
	 * @return　書籍在庫一覧へリターン.
	 * @throws ParseException 画像が存在しないなどのプログラマが予測できるエラーが発生する可能性があるので、例外処理をとらえる. 
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/save")
	public String save(@Validated InsertForm form,BindingResult result) throws ParseException, IOException {
		
		 //** string を　date　へ変換する処理 **//
		 //送られてくる日付データをstring へ変換
		 String date = form.getSaledate();
		 //日付のフォーマットを作成
		 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		 //フォーマットを基に、string を date型へ変換
		 Date formatDate = sdf2.parse(date);
		
		 Book book = new Book();
		 //formの情報をbookへコピーして、足りないものは下の処理で、自分でセットする
		 BeanUtils.copyProperties(form, book); 
		
		 //自動採番ではないので、最新のidを取得して１プラスしてセットする
		 Book maxBook = bookService.findByMaxId();//最新のidを取得
		 Integer maxId = (maxBook.getId()+1);
		
		 //新規で登録される画像の処理を記載
		 //image に画像の情報が詰まっている。（例： 名前、画像情報、サイズなど）
		 
		 //ファイルクラスはファイルに関する様々な操作を行う。(引数にパスやファイル名をすることで、そのオブジェクトを作成)
		 //File file  = new File(form.getImage().getOriginalFilename());
		
		 //String fileName = form.getImage().getOriginalFilename();
		 //System.out.println("fileName="+fileName);
		
		   //ファイルを作成
	       File file = new File("C:/env/springworkspace/sample/stock-management-bugfix-spring-1/src/main/webapp/img/"+form.getImage().getOriginalFilename());
	        
	        //createNewFileメソッドを使用する。空のファイルが作成されているか確認する。
	        if (file.createNewFile()){
	            System.out.println("ファイル作成成功");
	            System.out.println(file);
	        }else{
	            System.out.println("ファイル作成失敗");
	        }
	        
	        try {
	            /* バイナリデータを書き込む */
	        	//バイナリデータをファイルに書き込む先のパスを指定。
	        	BufferedOutputStream bf = new BufferedOutputStream
	                    (new FileOutputStream("C:/env/springworkspace/sample/stock-management-bugfix-spring-1/src/main/webapp/img/"+form.getImage().getOriginalFilename() ));
	        	        
	              //ASCIIコードを書き込む
	              byte[] b1 = form.getImage().getBytes();
	              
	              //ファイルに書き込む内容をバッファに格納
	              bf.write(b1);
	              
	              //バッファにためた内容を指定したファイルに書き込む。
	              bf.flush();
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
		 
		 
		 /*
		 	try {
		 	//コピーするファイルを指定
			FileInputStream fileIn = new FileInputStream("/src/main/img/"+form.getImage().getOriginalFilename());
			
			//FileOutputStreamのオブジェクトを生成する
			FileOutputStream fileOut = new FileOutputStream("/src/main/img/"+form.getImage().getOriginalFilename());
			
			// byte型の配列を宣言
			byte[] buf = new byte[256];
			int len;
			
			// ファイルの終わりまで読み込む
			while((len = fileIn.read(buf)) != -1){
				fileOut.write(buf);
			}
			
			//ファイルに内容を書き込む
			fileOut.flush();
			
			//ファイルの終了処理
			fileOut.close();
			fileIn.close();
		
		 	}catch(Exception ex){
		 		ex.printStackTrace();
		 	}
			*/
		
		book.setSaledate(formatDate);//formはString,domainはdateなので、自分でセット。
		book.setId(maxId);
		book.setImage(form.getImage().getOriginalFilename());
		
		bookService.save(book);
		
		return "book/list";
	}
	
	

	
	
	

}
