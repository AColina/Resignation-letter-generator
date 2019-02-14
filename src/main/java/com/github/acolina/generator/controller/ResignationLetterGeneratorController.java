package com.github.acolina.generator.controller;

import com.github.acolina.generator.model.constant.OutputFormat;
import com.github.acolina.generator.model.dto.ResignationLetterDTO;
import com.github.acolina.generator.model.request.ResignationLetter;
import com.github.acolina.generator.service.ResignationLetterGeneratorService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generator")
public class ResignationLetterGeneratorController {

    private final ResignationLetterGeneratorService generatorService;

    @Autowired
    public ResignationLetterGeneratorController(ResignationLetterGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void generate(HttpServletResponse response,
                         @RequestBody ResignationLetter resignationLetter) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        ResignationLetterDTO dto = generatorService.generateResignationLetterDTO(resignationLetter);
        parameters.put("dto", dto);
        InputStream jasperStream = getJasper("/jasper", "resignation_letter");
        JasperPrint jasperPrint;
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(1));
        } catch (JRException e) {
            e.printStackTrace();
            throw e;
        }
        outputFormat(response, jasperPrint, resignationLetter.getFormat(), "resignation_letter");
    }

    private void outputFormat(HttpServletResponse response, JasperPrint jasperPrint, OutputFormat format, String name) {
        switch (format) {
            case PDF:
                responsePDF(response, jasperPrint, name);
                break;
//            case "EXCEL":
//                responseXLS(response, jasperPrint, "inventarioArchivo");
//            break;
            case WORD:
                responseDocx(response, jasperPrint, name);
                break;
        }
    }

    private void responseDocx(HttpServletResponse response, JasperPrint jasperPrint, String name) {
        try {
            final OutputStream outStream = response.getOutputStream();
            response.setHeader("Access-Control-Expose-Headers", "X-FileName");
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=" + name + ".docx");
            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(name + ".docx"));
            docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            docxExporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void responsePDF(HttpServletResponse response, JasperPrint jasperPrint, String name) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Access-Control-Expose-Headers", "X-FileName");
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void responseXLS(HttpServletResponse response, JasperPrint jasperPrint, String name) {
        try {
            final OutputStream outStream = response.getOutputStream();
            response.setHeader("Access-Control-Expose-Headers", "X-FileName");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + name + ".xlsx");
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(name + ".xlsx"));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            xlsxExporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream getJasper(String path, String name) {
        String url = String.format("%s/%s.jasper", path, name);
        return this.getClass().getResourceAsStream(url);
    }
}
