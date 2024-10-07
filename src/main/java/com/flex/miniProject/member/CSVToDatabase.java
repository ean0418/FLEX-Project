package com.flex.miniProject.member;

import java.sql.*;
import java.io.*;

public class CSVToDatabase {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://192.168.0.119:3306/bizone?useSSL=false&allowLoadLocalInfile=true";
        String username = "test";
        String password = "a12345678!";

        String csvFilePath = "C:/Users/sdedu/Downloads/데이터모음/bizone_service.csv";

        Connection connection = null;

        try {
            // 1. MySQL 연결
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            // 2. INSERT 쿼리 준비
            String sql = "INSERT INTO bizone_service (bs_name, bs_code) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 3. CSV 파일을 읽기 위한 BufferedReader
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            // 4. 첫 번째 줄(헤더) 무시
            lineReader.readLine();

            int lineNumber = 0;  // 라인 수 확인용 변수

            // 5. CSV 파일의 각 줄을 읽어서 데이터 삽입
            while ((lineText = lineReader.readLine()) != null) {
                lineNumber++;
                String[] data = lineText.split(",");

                // 배열 길이 확인
                if (data.length < 2) {
                    System.err.println("잘못된 형식의 데이터가 발견되었습니다. Line: " + lineNumber + " => " + lineText);
                    continue;
                }

                String bs_name = data[0];
                String bs_code = data[1];

                // 값 설정 및 배치 추가
                statement.setString(1, bs_name);
                statement.setString(2, bs_code);
                statement.addBatch();
            }

            // 파일 리더 닫기
            lineReader.close();

            // 배치 실행 및 커밋
            statement.executeBatch();
            connection.commit();

            // 연결 종료
            statement.close();
            connection.close();

            System.out.println("CSV 파일 데이터를 데이터베이스에 성공적으로 삽입했습니다.");

        } catch (IOException ex) {
            System.err.println("CSV 파일을 읽는 동안 오류가 발생했습니다.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQLException 발생: " + ex.getMessage());
            ex.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // 오류 발생 시 롤백
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}