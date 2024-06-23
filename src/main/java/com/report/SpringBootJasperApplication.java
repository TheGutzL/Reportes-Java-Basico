package com.report;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@SpringBootApplication
public class SpringBootJasperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJasperApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			String destionationPath = "src" + File.separator
					+ "main" + File.separator
					+ "resources" + File.separator
					+ "static" + File.separator
					+ "ReportGenerator.pdf";

			String filePath = "src" + File.separator
					+ "main" + File.separator
					+ "resources" + File.separator
					+ "templates" + File.separator
					+ "report" + File.separator + "Report.jrxml";

			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("voucher_id", "000001");
			parameters.put("CurrentDate", formatter.format(localDateTime));
			parameters.put("AmountPaid", new BigDecimal(30000));
			parameters.put("payment_method", "Cash");
			parameters.put("parent_name", "Sergio Peña");
			parameters.put("child_name", "Santiago Perez");
			parameters.put("imageDir", "classpath:/static/images/");

			JasperReport report = JasperCompileManager.compileReport(filePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(print, destionationPath);
			System.out.println("Report Created Succesfully");
		};
	}

}
