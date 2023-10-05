package com.spring.angular.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.angular.models.Pelicula;
import com.spring.angular.models.PeliculaReport;
import com.spring.angular.repositories.PeliculaRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.angular.exceptions.ResourceNotFoundException;

@Service
public class PeliculaService {
	
	@Autowired
	private PeliculaRepository repoPelicula;
	
	public List<Pelicula> listarPeliculas(){
		return repoPelicula.findAll();
	}
	
	/* Registra y actualiza la entidad pelicula */
	public Pelicula guardarPelicula(Pelicula pelicula) {
		return repoPelicula.save(pelicula);
	}
	
	public Pelicula obtenerPeliculaPorId(Integer id) {
		return repoPelicula.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe la pelicula con el ID : " + id));
	}
	
	public List<Pelicula> obtenerRegistrosParaExportar(int idPelicula) {
	    return repoPelicula.obtenerRegistrosParaExportar(idPelicula);
	}
	
	public void eliminarPeliculaPorId(Integer id) {
		Pelicula pelicula = repoPelicula.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe la pelicula con el ID : " + id));
		repoPelicula.delete(pelicula);
	}
	
	public Pelicula convertJsonToMovie(String stringPeli) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Pelicula pelicula = objectMapper.readValue(stringPeli, Pelicula.class);
        return pelicula;
    }
	
	public InputStream getPeliculaReport(Pelicula pelicula) throws JRException {
	    List<Pelicula> listaPeliculas = this.obtenerRegistrosParaExportar(pelicula.getIdPelicula());
	    List<PeliculaReport> listData = new ArrayList<PeliculaReport>();
	    listData.add(new PeliculaReport(listaPeliculas));

	    JRBeanCollectionDataSource dts = new JRBeanCollectionDataSource(listData);

	    try {
	        // Utiliza el nombre del informe JasperReports que contiene los campos del DataSet de tabla
	        JasperPrint jPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/jasper/ReportePeliculasPDF.jasper"), null, dts);
	        return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jPrint));
	    } catch (JRException e) {
	        throw e;
	    }
	}
}
