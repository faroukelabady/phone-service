package com.jumia.phoneService.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.sqlite.Function;
import org.sqlite.SQLiteConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class DaoUtilities {

	private final DataSource dataSource;

	public void addRegexFunction() {
		Connection connect = DataSourceUtils.getConnection(dataSource);
		try {
			Function.create(connect.unwrap(SQLiteConnection.class), "REGEXP", new Function() {
				@Override
				protected void xFunc() throws SQLException {
					String expression = value_text(0);
					String value = value_text(1);
					if (value == null)
						value = "";

					Pattern pattern = Pattern.compile(expression);
					result(pattern.matcher(value).find() ? 1 : 0);
				}
			});
		} catch (SQLException e) {
			log.error("couldn't add the regex function", e);
		}
	}
}
