package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RestController
public class ExampleController {

    private final DataSource dataSource;

    public ExampleController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    public String getInfo() {

        try (Connection conn = dataSource.getConnection()) {

            if (conn.isValid(5)) {
                return "Connected Successfully!";
            }

            return "Connection is not valid";

        } catch (Exception e) {
            return "Connection failed: " + e.getMessage();
        }
    }

    @GetMapping("/add")
    public String addStudent(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam int age) {

        String sql = "INSERT INTO studentInfo(id,name, age) VALUES (?, ? , ?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);

            int rows = ps.executeUpdate();

            return rows + " row inserted successfully";

        } catch (Exception e) {
            return "Insert failed: " + e.getMessage();
        }
    }
    
    @GetMapping("/students")
public String getStudents() throws Exception {

    StringBuilder sb = new StringBuilder();

    try (
            Connection conn = dataSource.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement("SELECT * FROM studentInfo");
            java.sql.ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {
            sb.append(rs.getInt("id"))
              .append(" ")
              .append(rs.getString("name"))
              .append(" ")
              .append(rs.getInt("age"))
              .append("<br>");
        }
    }

    return sb.toString();
}
}