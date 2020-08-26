package com.televisivo.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.televisivo.service.JasperReportsService;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class JasperReportsServiceImpl implements JasperReportsService {

	private static final String DIR_RELATORIOS = "/relatorios/";
	private Map<String, Object> params = new HashMap<String, Object>();
		
	@Autowired
	private DataSource dataSource;
	
	@Override
	public JasperPrint imprimeRelatorioDownload(String file) {
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		try {
			InputStream arquivo = getClass().getResourceAsStream(DIR_RELATORIOS + file + ".jasper");
			JasperPrint jasperPrint = null;
			jasperPrint = JasperFillManager.fillReport(arquivo, params, conexao());
			return jasperPrint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] imprimeRelatorioNoNavegador(String file) {
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		try {
			InputStream arquivo = getClass().getResourceAsStream(DIR_RELATORIOS + file + ".jasper");
			JasperPrint jasperPrint = null;
			jasperPrint = JasperFillManager.fillReport(arquivo, params, conexao());
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Connection conexao() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}