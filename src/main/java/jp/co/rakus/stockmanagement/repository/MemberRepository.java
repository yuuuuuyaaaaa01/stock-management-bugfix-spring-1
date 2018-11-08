package jp.co.rakus.stockmanagement.repository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.awt.print.Book;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.stockmanagement.domain.Member;

/**
 * membersテーブル操作用のリポジトリクラス.
 * @author igamasayuki
 */
@Repository
public class MemberRepository {
	
	@Autowired
	public HttpSession session;
	
	/**
	 * ResultSetオブジェクトからMemberオブジェクトに変換するためのクラス実装&インスタンス化
	 */
	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String mailAddress = rs.getString("mail_address");
		String password = rs.getString("password");
		return new Member(id, name, mailAddress, password);
	};

	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	/**
	 * メールアドレスとパスワードからメンバーを取得.
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return メンバー情報.メンバーが存在しない場合はnull.
	 */
	public Member findByMailAddressAndPassword(String mailAddress, String password) {
	
		Member member = null;
		try{
			SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress",mailAddress).addValue("password", password);
			member = jdbcTemplate.queryForObject(
					"SELECT id,name,mail_address,password FROM members WHERE mail_address= :mailAddress and password=:password",
					param, 
					MEMBER_ROW_MAPPER);
			
			return member;
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Member findByPassword(String password) {
		
		Member member = null;
		try{
			SqlParameterSource param = new MapSqlParameterSource().addValue("password", password);
			member = jdbcTemplate.queryForObject(
					"SELECT id,name,mail_address,password FROM members WHERE mail_address= :mailAddress and password=:password",
					param, 
					MEMBER_ROW_MAPPER);
			
			return member;
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	/**
	 * メンバー情報を保存　または　更新する.
	 * @param member　保存または更新するメンバー情報
	 * @return　保存または更新されたメンバー情報
	 */
	public Member save(Member member) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		
		//String mail = findByEmail(member);//既に登録されていれば値を返す
		//System.out.println("mail="+mail);
		
		if (member.getId() == null) {
			
			jdbcTemplate.update(
					"INSERT INTO members(name,mail_address,password) values(:name,:mailAddress,:password)", 
					param);
				
				return member;
			
		} else {
			jdbcTemplate.update(
					"UPDATE members SET name=:name,mail_address=:mailAddress,password=:password WHERE id=:id", 
					param);
		}
		return member;
	}

	
	/**
	 * email が既に登録されているか確認.saveメソッドを実行.
	 * 
	 * @param member
	 * @return 存在している：メンバー情報　　存在していない場合：null
	 */
	
	public Member findByEmail(String mail_address) {
		String sql = "select id,name,mail_address,password from members where mail_address=:mailAddress;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress",mail_address);
		
		List<Member> members = jdbcTemplate.query(sql,param,MEMBER_ROW_MAPPER); 
		
		if(members.isEmpty()) {
			return null;
		}else {
			Member member = members.get(0);
			return member;
		}
		
		
	}

	
	
	
}
