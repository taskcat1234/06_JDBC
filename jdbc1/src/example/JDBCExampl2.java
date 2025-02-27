package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExampl2 {
	public static void main(String[] args) {
		// EMPLOYEE 테이블에서
		// 모든 사원의 사번,이름, 급여를
		// 급여 오른차순으로 정렬
		/* 1. JDBC 객체 참조 변수 선언 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			/* 2.DriverManager 객체를 이용해 connection 객체 생성하기 */
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
			sb.append("SELECT EMP_ID, EMP_NAME, SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("ORDER BY SALARY ASC ");
			
			String sql = sb.toString();
			
			/* 4. sql을 전달하고 결과를 받아올 Statement 객체 생성 */
			stmt = conn.createStatement();
			
			/* 5. Statement 객체를 이용해서 SQL을 DB로 전달 후 수행
	    1) SELECT문 : executeQuery() -> ResultSet으로 반환

	    2) DML문    : executeUpdate() -> 결과 행의 개수(int) 반환

			(5번 SQL이 SELECT 인 경우만 밑에 6번 실행)*/
			
			rs = stmt.executeQuery(sql);
			
			/* 6.조회 결과가 저장된 resultSet을
			/* 1행 씩 접근하여 각 행에 기록된 컬럼 값 얻어오기 */
			while(rs.next()) {
				// rs.next() : ResulSet의 Cursor를 다음 행으로 이동
				// 다음 행이 있으면 true, 없으면 false
				String empId   = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary     = rs.getInt("SALARY");
				System.out.printf(" : %s / :  %s / : %d \n"
						,empId ,empName, salary
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
