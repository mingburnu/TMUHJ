package com.asiaworld.tmuhj.core.entity;

import java.io.File;
import java.io.InputStream;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class FileIoProperties implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 998220513379335266L;

	@Transient
	private File[] file;

	@Transient
	private String[] fileFileName;

	@Transient
	private String[] fileContentType;

	@Transient
	private String dataStatus;

	@Transient
	private Integer[] importItem;

	/** export file name */
	@Transient
	private String reportFile;

	@Transient
	private InputStream inputStream;

	/**
	 * @return the file
	 */
	public File[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File[] file) {
		this.file = file;
	}

	/**
	 * @return the fileFileName
	 */
	public String[] getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String[] getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the dataStatus
	 */
	public String getDataStatus() {
		return dataStatus;
	}

	/**
	 * @param dataStatus
	 *            the dataStatus to set
	 */
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	/**
	 * @return the importItem
	 */
	public Integer[] getImportItem() {
		return importItem;
	}

	/**
	 * @param importItem
	 *            the importItem to set
	 */
	public void setImportItem(Integer[] importItem) {
		this.importItem = importItem;
	}

	/**
	 * @return the reportFile
	 */
	public String getReportFile() {
		return reportFile;
	}

	/**
	 * @param reportFile
	 *            the reportFile to set
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/** set struts action dispatcher location */
	@Transient
	private String location;

	public String getLocation() {
		StringBuilder locationBuilder = new StringBuilder("/WEB-INF/jsp/");

		String className = getClass().getCanonicalName();
		String packageName = className.substring(0, className.lastIndexOf("."));

		location = locationBuilder
				.append(packageName.substring(0, packageName.lastIndexOf(".")))
				.append("/").toString();
		return location;
	}
}
