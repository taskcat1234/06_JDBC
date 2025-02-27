package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample3 {
	public static void main(String[] args) {
		// EMPLOYEE 테이블에서
		// 급여를 300만 이상 500만 이하로 받는 사원의
		// 사번, 이름, 부서코드, 급여를
		// 급여 내림차순으로 출력
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			// DB 연결 정보
			String type = "jdbc:oracle:thin:@";
			String host = "112.221.156.34";
			String port = ":12345";
			String dvName = ":XE";
			String userName = "KH16_LSJ";
			String password = "KH1234";
			
			// Connection 객체를 생성해서 얻어오기
			conn = DriverManager.getConnection(
					type + host + port + dvName, 
					userName, // 사용자 계정명
					password  // 비밀번호
			);
			/* 3. SQL 작성 */
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("WHERE SALARY >= 3000000 AND SALARY <= 5000000 ");
			sb.append("ORDER BY SALARY DESC ");
			String sql = sb.toString();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				// rs.next() : ResulSet의 Cursor를 다음 행으로 이동
				// 다음 행이 있으면 true, 없으면 false
				String empId   = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_CODE");
				int salary     = rs.getInt("SALARY");
				System.out.printf("사번 : %s 이름 : %s 부서코드 : %s 급여 : %d \n"
						,empId ,empName, deptCode, salary
					);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			/* 7. 사용 완료된 JDBC 객체 자원 반환 */
			// JDBC 객체는 외부 자원 (DB)와 연결된 상태라서
			// Java 프로그램 종료후에도 연결이 유지되고 있다!
			// -> 마지막에 꼭 닫아줘서 메모리 반환 해야됨
			try {
				if(rs   != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
