// package com.example.demo;

// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import javax.sql.DataSource;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;

// @RestController
// public class ExampleController {

//     private final DataSource dataSource;

//     public ExampleController(DataSource dataSource) {
//         this.dataSource = dataSource;
//     }

//     @GetMapping("/")
//     public String getInfo() {

//         try (Connection conn = dataSource.getConnection()) {

//             if (conn.isValid(5)) {
//                 return "Connected Successfully!";
//             }

//             return "Connection is not valid";

//         } catch (Exception e) {
//             return "Connection failed: " + e.getMessage();
//         }
//     }

//     @GetMapping("/add")
//     public String addStudent(
//             @RequestParam int id,
//             @RequestParam String name,
//             @RequestParam int age) {

//         String sql = "INSERT INTO studentInfo(id,name, age) VALUES (?, ? , ?)";

//         try (
//                 Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setInt(1, id);
//             ps.setString(2, name);
//             ps.setInt(3, age);

//             int rows = ps.executeUpdate();

//             return rows + " row inserted successfully";

//         } catch (Exception e) {
//             return "Insert failed: " + e.getMessage();
//         }
//     }

//     @GetMapping(value = "/students", produces = MediaType.TEXT_HTML_VALUE)
//     public String getStudents() throws Exception {

//         StringBuilder sb = new StringBuilder();
//         sb.append("<html><body>");
//         sb.append("<h2>Student Records</h2>");
//         sb.append("<table border='1' cellpadding='8' cellspacing='0'>");
//         sb.append("<tr><th>ID</th><th>Name</th><th>Age</th></tr>");

//         try (
//                 Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM studentInfo");
//                 ResultSet rs = ps.executeQuery()) {

//             while (rs.next()) {
//                 sb.append("<tr>")
//                         .append("<td>").append(rs.getInt("id")).append("</td>")
//                         .append("<td>").append(rs.getString("name")).append("</td>")
//                         .append("<td>").append(rs.getInt("age")).append("</td>")
//                         .append("</tr>");
//             }
//         }

//         sb.append("</table>");
//         sb.append("</body></html>");

//         return sb.toString();
//     }

//     @PostMapping("/students")
//     public String createStudent(@RequestParam int id,
//             @RequestParam String name,
//             @RequestParam int age) {
//         String sql = "INSERT INTO studentInfo(id, name, age) VALUES (?, ?, ?)";
//         try (Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, id);
//             ps.setString(2, name);
//             ps.setInt(3, age);
//             int rows = ps.executeUpdate();
//             return rows + " row inserted";
//         } catch (Exception e) {
//             return "Insert failed: " + e.getMessage();
//         }
//     }

//     @GetMapping("/students/{id}")
//     public String getStudentById(@PathVariable int id) {
//         String sql = "SELECT * FROM studentInfo WHERE id = ?";
//         try (Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, id);
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return "ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age");
//             }
//             return "No student found";
//         } catch (Exception e) {
//             return "Read failed: " + e.getMessage();
//         }
//     }

//     @PutMapping("/students/{id}")
//     public String updateStudent(@PathVariable int id,
//             @RequestParam String name,
//             @RequestParam int age) {
//         String sql = "UPDATE studentInfo SET name = ?, age = ? WHERE id = ?";
//         try (Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setString(1, name);
//             ps.setInt(2, age);
//             ps.setInt(3, id);
//             int rows = ps.executeUpdate();
//             return rows + " row updated";
//         } catch (Exception e) {
//             return "Update failed: " + e.getMessage();
//         }
//     }

//     @DeleteMapping("/students/{id}")
//     public String deleteStudent(@PathVariable int id) {
//         String sql = "DELETE FROM studentInfo WHERE id = ?";
//         try (Connection conn = dataSource.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, id);
//             int rows = ps.executeUpdate();
//             return rows + " row deleted";
//         } catch (Exception e) {
//             return "Delete failed: " + e.getMessage();
//         }
//     }
// }