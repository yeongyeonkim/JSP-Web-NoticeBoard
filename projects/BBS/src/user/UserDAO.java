//데이터베이스 접근 객체의 약자. 실질적으로 데이버베이스에서 정보를 넣거나 불러올때 사용
package user;
//control+shilf+O 를 눌러서 java.sql.connection 해서 외부 라이브러리를 추가
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() { //실제로 접속할 수 있게 해주는 부분
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int login(String userID, String userPassword)
	{
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID); // ?에 해당하는 아이디로 넣어줌
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}
				else
					return 0; // 비밀번호 불일치.
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserID());
			pstmt.setString(2,  user.getUserPassword());
			pstmt.setString(3,  user.getUserName());
			pstmt.setString(4,  user.getUserGender());
			pstmt.setString(5,  user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
