// CafeDAO.java
package main.com.web.enjoy.dao;

import static main.com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import main.com.web.enjoy.dto.Cafe;

public class CafeDao {
	 private Properties sql = new Properties();
	    {
	        String path = CafeDao.class.getResource("/sql/cafe_sql.properties").getPath();
	        try (FileReader fr = new FileReader(path)) {
	            sql.load(fr);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Cafe> selectAllCafes(Connection conn, int cPage, int numPerpage) {
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        List<Cafe> cafes = new ArrayList<>();
	        
	        try {
	            pstmt = conn.prepareStatement(sql.getProperty("selectAllCafes"));
	            int start = (cPage - 1) * numPerpage + 1; // 시작 행 번호
	            int end = cPage * numPerpage; // 끝 행 번호
	            pstmt.setInt(1, start);
	            pstmt.setInt(2, end);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	                cafes.add(getCafe(rs));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs);
	            close(pstmt);
	        }
	        return cafes;
	    }

	    public int selectCafeAllCount(Connection conn) {
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        int result = 0;
	        try {
	            pstmt = conn.prepareStatement(sql.getProperty("selectCafeAllCount"));
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                result = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs);
	            close(pstmt);
	        }
	        return result;
	    }

	    private Cafe getCafe(ResultSet rs) throws SQLException {
	        return Cafe.builder()
	                .cafeNo(rs.getInt("cafeNo"))
	                .cafeName(rs.getString("cafeName"))
	                .cafeAddress(rs.getString("cafeAddress"))
	                .cafePhone(rs.getString("cafePhone"))
	                .cafeTime(rs.getString("cafeTime"))
	                .cafeImg(rs.getString("cafeImg"))
	                .location(rs.getString("location"))
	                .category(rs.getString("category"))
	                .build();
	    }
	}