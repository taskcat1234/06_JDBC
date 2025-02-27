package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {
		// 부서명을 입력 받아
		// 해당 부서의 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 내림 차순으로 조회
		
		Scanner sc = new Scanner(System.in);
		
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
			System.out.println("=== 부서명 입력! ===");
			System.out.print("부서명 입력 : ");
			String job = sc.next();
			
			/* 3. SQL 작성 */
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_CODE ");
			sb.append("FROM EMPLOYEE ");
			sb.append("JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID) ");
			sb.append("WHERE DEPT_TITLE = ");
			sb.append("'" + job + "'");
			sb.append(" ORDER BY JOB_CODE DESC ");
			String sql = sb.toString();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				// rs.next() : ResulSet의 Cursor를 다음 행으로 이동
				// 다음 행이 있으면 true, 없으면 false
				String empId   = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_TITLE");
				String jobCode = rs.getString("JOB_CODE");
				
				System.out.printf("사번 : %s 이름 : %s 부서명 : %s 직급명 : %s \n"
						,empId ,empName, deptCode, jobCode
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
