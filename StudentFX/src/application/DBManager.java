package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DBManager 
{
	public List<Student> loadStudents()
	{
		List<Student> studentAll = new ArrayList<Student>();
		Connection myConn = this.Connector();
		try
		{
			Statement myStmt = myConn.createStatement();
			String sql = "SELECT * FROM studenttable";
			ResultSet myRs = myStmt.executeQuery(sql);
			while (myRs.next())
			{
				String name = myRs.getString("name");
				int id = myRs.getInt("id");
				String gender = myRs.getString("gender");
				LocalDate birth = null;
				if(myRs.getDate("dateofbirth") != null)
				{
					birth = myRs.getDate("dateofbirth").toLocalDate();
				}
				String photo = myRs.getString("photo");
				float mark = myRs.getFloat("mark");
				String comments = myRs.getString("comments");
				
				Student s = new Student(id, name, gender, birth, photo, mark, comments);
				studentAll.add(s);
			}
			this.close(myConn, myStmt, myRs);
			return studentAll;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateStudent(Student selectedStudent)
	{
		Connection myConn = this.Connector();
		try
		{
			Statement myStmt = myConn.createStatement();
			String sql = "UPDATE student.studenttable SET ";
			sql += " gender = '";
			sql += selectedStudent.getGender();
			
			sql += "', dateofbirth = '";
			sql += selectedStudent.getBirthDate();
			
			sql += "', name = '";
			sql += selectedStudent.getName();
				
			sql += "', mark =";
			sql += selectedStudent.getMark();

			sql += ", comments='";
			sql += selectedStudent.getComments();
			
			sql += "', photo= '";
			sql += selectedStudent.getPhoto();
			
			sql += "' WHERE id = ";
			sql += selectedStudent.getId();
			
			sql += ";";
			int myRs = myStmt.executeUpdate(sql);
			
			this.close(myConn, myStmt, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addStudent(Student selectedStudent)
	{
		Connection myConn = this.Connector();
		try
		{
			Statement myStmt = myConn.createStatement();
			String sql = "INSERT INTO student.studenttable (name, gender, dateofbirth, photo, mark, comments) VALUES ('";
			sql += selectedStudent.getName();
			
			sql += "', '";
			sql += selectedStudent.getGender();
			
			sql += "', '";
			sql += selectedStudent.getBirthDate();
			
			sql += "', '";
			sql += selectedStudent.getPhoto();
			
			sql += "', ";
			sql += selectedStudent.getMark();
			
			sql += ", '";
			sql += selectedStudent.getComments();
			
			sql += "') ;";
			int myRs = myStmt.executeUpdate(sql);
			
			this.close(myConn, myStmt, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteStudent(Student selectedStudent)
	{
		Connection myConn = this.Connector();
		try
		{
			Statement myStmt = myConn.createStatement();
			String sql = "DELETE FROM student.studenttable WHERE id = ";
			sql += selectedStudent.getId();
			
			sql += ";";
			int myRs = myStmt.executeUpdate(sql);
			
			this.close(myConn, myStmt, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Connection Connector()
	{
		try
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?serverTimezone=UTC", "root", "root");
			return connection;
		}
		catch ( Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs)
	{
		try
		{
			if(myStmt!=null)
				myStmt.close();
			if(myRs!=null)
				myRs.close();
			if(myConn!=null)
				myConn.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
