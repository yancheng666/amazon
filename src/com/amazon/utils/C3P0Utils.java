package com.amazon.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class C3P0Utils {
	// 创建C3P0连接池对象，默认就会去读取配置文件
		private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

		public static DataSource getDataSource(){
			return dataSource;
		}
		
		public static Connection getConnection() {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		// 释放两个资源
		public static void release(Statement stmt, Connection conn) {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}

		// 释放三个资源
		public static void release(ResultSet rs, Statement stmt, Connection conn) {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}
