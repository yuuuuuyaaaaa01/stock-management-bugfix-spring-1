package jp.co.rakus.stockmanagement.repository;

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
		SqlParameterSource param = new MapSqlParameterSource();
		Member member = null;
		try{
			member = jdbcTemplate.queryForObject(
					"SELECT id,name,mail_address,password FROM members WHERE mail_address= '"
							+ mailAddress + "' and password='" + password + "'",
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
		
		String mail = findByEmail(member);//既に登録されていれば値を返す
		System.out.println("mail="+mail);
		
		if (member.getId() == null) {
			
			if(mail == null) { // null であれば、実行。
			jdbcTemplate.update(
					"INSERT INTO members(name,mail_address,password) values(:name,:mailAddress,:password)", 
					param);
			}else {
				String message2 = null;
				message2 = "既に登録されています";
				session.setAttribute("message2",message2);		
				
				return member;
			}
			
			
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
	 * @return
	 */
	public String findByEmail(Member member) {
		String sql = "select mail_address from members where mail_address=:mailAddress;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress",member.getMailAddress());
		String mail = jdbcTemplate.queryForObject(sql, param, String.class);
		return mail;
	}
	
	
	
	
}
