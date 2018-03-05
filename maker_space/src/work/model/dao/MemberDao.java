/**
 * <pre>
 * @author DONGHYUNLEE HANAJUNG YOUNGHWANGBO
 * @version ver.1.0
 * @since jdk1.8
 * </pre>
 */
package work.model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import work.model.dto.Member;

/**
 * * ȸ�����̺��� ���� DAO(Data Access Object)Ŭ����
 * 1) JDBC Driver loading
 * 2) DB ���� ����
 * 3) SQL ��� ����
 * 4) SQL ���� ���� ��û
 * 4) ��� ó��
 * 5) �ڿ� ����
 */

/**
 * ## ���
 * 1. ȸ���߰�
 * 2. ȸ������
 * 3. ȸ���������� (��й�ȣ, �޴�����ȣ)
 * 4. �α���
 * 5. �̸���ã��
 * 6. ��й�ȣã��
 * 4. ��üȸ����ȸ(������ ����)
 * 5. ��ȸ����ȸ(������ ����)
 */
public class MemberDao {
	private DataSource ds;
	/**
	 * �⺻������
	 * context ȯ�� ���� ��������.
	 */
	public MemberDao() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
			} catch(NamingException e){
				System.out.println("ERROR: " + e.getMessage());
			}	
	}
	/**
	 * ȸ���߰�
	 * @param dto
	 * @return
	 */
	public int insertMember(Member dto) {
		String email = dto.getEmail();
		String password = dto.getPassword();
		String name = dto.getName();
		String mobile = dto.getMobile();
		String company = dto.getCompany();
		String grade = dto.getGrade();
		int point = dto.getPoint();
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		try {
			//conn = DriverManager.getConnection(url, user, password);
			conn = ds.getConnection();
			
			String sql = "insert into members values(?, ?, ?, ?, ?, ?, ?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, mobile);
			pstmt.setString(5, company);
			pstmt.setString(6, grade);
			pstmt.setInt(7, point);
			pstmt.executeUpdate();
			return 1;
		} catch(SQLException e){
			System.out.println("Error: " + e.getMessage() + "// ȸ���߰� ����");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : ȸ���߰� �ڿ� ���� ����");
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * ȸ������
	 * @param email
	 * @param password
	 * @return
	 */
	public int removeMember(String email, String password) {
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "delete members where email=? and password=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			return 1;
		} catch(SQLException e){
			System.out.println("Error: " + e.getMessage() + "// ȸ�� ���� ����");
			e.printStackTrace();
		} finally {
			try {
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null) { 
						conn.close();
					}
			} catch(SQLException e) {
				System.out.println("Error : ȸ������ �ڿ����� ����");
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	/**
	 * ȸ����������
	 * @param email
	 * @param password
	 * @param mobile
	 * @return
	 */
	public int changeMemberInfo(String email, String password, String mobile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "update members set password =?, mobile=? where email=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, mobile);
			pstmt.setString(3, email);
			pstmt.executeUpdate();
			return 1;
		} catch(SQLException e){
			System.out.println("Error: " + e.getMessage() + "// ȸ������ ����");
			e.printStackTrace();
		} finally {
			try {
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null) { 
						conn.close();
					}
			} catch(SQLException e) {
				System.out.println("Error : ȸ������ �ڿ����� ����");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * �α���
	 * @param email
	 * @param password
	 * @return
	 */
	public String loginMember(String email, String password) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String sql = "select grade from members where email=? and password=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String grade= rs.getString(1);
				return grade;
			}
		} catch(SQLException e) {
				System.out.println("Error: " + e.getMessage() + "// �α��� ����");
				e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : �α��� �ڿ����� ����");
				e.printStackTrace();
			}
		}	
		return null;
	}
	
	/**
	 * �̸��� ã�� 
	 * @param name
	 * @param mobile
	 * @return
	 */
	public String findMemberEmail(String name, String mobile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String sql = "select email from members where name=? and mobile=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, mobile);
			rs = pstmt.executeQuery();		
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(SQLException e) {
			System.out.println("Error: " + e.getMessage() + "// �̸���ã�� ����");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : �̸���ã�� �ڿ����� ����");
				e.printStackTrace();
			}
		}	
		return null;
	}
	/**
	 * ��й�ȣ ã��
	 * @param email
	 * @param password
	 * @return
	 */
	public String findMemberPw(String email, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String sql = "update members set password =?where email=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(SQLException e) {
			System.out.println("Error: " + e.getMessage() + "// ��й�ȣ ã�� ����");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : ��й�ȣã�� �ڿ����� ����");
				e.printStackTrace();
			}
		}	
		return null;
	}
	
	/**
	 * ��üȸ����ȸ(�����ڿ���)
	 * @return
	 */
	public ArrayList<Member> getMembers() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from members";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<Member> members = new ArrayList<Member>();
			
			String email, password, name, mobile, company, grade;
			int point;

			while(rs.next()) {
				email = rs.getString(1);
				password = rs.getString(2);
				name = rs.getString(3);
				mobile = rs.getString(4);
				company = rs.getString(5);
				grade = rs.getString(6);
				point = rs.getInt(7);
				members.add( new Member(email, password, name, mobile, company, grade, point));
			}
			return members;
		} catch(SQLException e) {
						System.out.println("Error: " + e.getMessage() + "// ��üȸ����ȸ ����");
						e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : ȸ����ü��ȸ �ڿ����� ����");
				e.printStackTrace();
			}
		}	
		return null;
	}
	/**
	 * ��ȸ����ȸ
	 * @param email
	 * @return
	 */
	public Member getMemberOne(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from members where email=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String password = rs.getString(2);
				String name = rs.getString(3);
				String mobile = rs.getString(4);
				String company = rs.getString(5);
				String grade = rs.getString(6);
				int point = rs.getInt(7);
				return new Member(email, password, name, mobile, company, grade, point);
			}
		} catch(SQLException e) {
				System.out.println("Error: " + e.getMessage() + "// ��ȸ����ȸ ����");
				e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) { 
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Error : ��ȸ����ȸ �ڿ����� ����");
				e.printStackTrace();
			}
		}	
		return null;
	}		
}