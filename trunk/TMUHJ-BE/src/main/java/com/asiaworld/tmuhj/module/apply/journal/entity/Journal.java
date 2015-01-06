package com.asiaworld.tmuhj.module.apply.journal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;

@Entity
@Table(name = "journal")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Journal extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8359789887877536098L;

	// 中文刊名
	@Column(name = "chinesetitle")
	private String chineseTitle;

	// 英文刊名
	@Column(name = "englishtitle")
	private String englishTitle;

	// 英文縮寫刊名
	@Column(name = "abbreviationtitle")
	private String abbreviationTitle;

	// 刊名演變
	@Column(name = "titleevolution")
	private String titleEvolution;

	// ISSN
	@Column(name = "ISSN")
	private String issn;

	// 語系
	@Column(name = "languages")
	private String languages;

	// 出版項
	@Column(name = "publishname")
	private String publishName;

	// 出版年
	@Column(name = "publishyear")
	private String publishYear;

	// 標題
	@Column(name = "caption")
	private String caption;

	// URL
	@Column(name = "URL")
	private String url;

	// 編號
	@Column(name = "numB")
	private String numB;

	// 刊別
	@Column(name = "Publication")
	private String publication;

	// 國會分類號
	@Column(name = "congressclassification")
	private String congressClassification;

	// 版本
	@Column(name = "version")
	private int version;

	@Transient
	private ResourcesBuyers resourcesBuyers;

	@Transient
	private List<Customer> customers;
	
	@Transient
	private String existStatus;

	/**
	 * @return the chineseTitle
	 */
	public String getChineseTitle() {
		return chineseTitle;
	}

	/**
	 * @param chineseTitle
	 *            the chineseTitle to set
	 */
	public void setChineseTitle(String chineseTitle) {
		this.chineseTitle = chineseTitle;
	}

	/**
	 * @return the englishTitle
	 */
	public String getEnglishTitle() {
		return englishTitle;
	}

	/**
	 * @param englishTitle
	 *            the englishTitle to set
	 */
	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	/**
	 * @return the abbreviationTitle
	 */
	public String getAbbreviationTitle() {
		return abbreviationTitle;
	}

	/**
	 * @param abbreviationTitle
	 *            the abbreviationTitle to set
	 */
	public void setAbbreviationTitle(String abbreviationTitle) {
		this.abbreviationTitle = abbreviationTitle;
	}

	/**
	 * @return the titleEvolution
	 */
	public String getTitleEvolution() {
		return titleEvolution;
	}

	/**
	 * @param titleEvolution
	 *            the titleEvolution to set
	 */
	public void setTitleEvolution(String titleEvolution) {
		this.titleEvolution = titleEvolution;
	}

	/**
	 * @return the issn
	 */
	public String getIssn() {
		return issn;
	}

	/**
	 * @param issn
	 *            the issn to set
	 */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/**
	 * @return the languages
	 */
	public String getLanguages() {
		return languages;
	}

	/**
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	/**
	 * @return the publishName
	 */
	public String getPublishName() {
		return publishName;
	}

	/**
	 * @param publishName
	 *            the publishName to set
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	/**
	 * @return the publishYear
	 */
	public String getPublishYear() {
		return publishYear;
	}

	/**
	 * @param publishYear
	 *            the publishYear to set
	 */
	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the numB
	 */
	public String getNumB() {
		return numB;
	}

	/**
	 * @param numB
	 *            the numB to set
	 */
	public void setNumB(String numB) {
		this.numB = numB;
	}

	/**
	 * @return the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @param publication
	 *            the publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}

	/**
	 * @return the congressClassification
	 */
	public String getCongressClassification() {
		return congressClassification;
	}

	/**
	 * @param congressClassification
	 *            the congressClassification to set
	 */
	public void setCongressClassification(String congressClassification) {
		this.congressClassification = congressClassification;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the resourcesBuyers
	 */
	public ResourcesBuyers getResourcesBuyers() {
		return resourcesBuyers;
	}

	/**
	 * @param resourcesBuyers
	 *            the resourcesBuyers to set
	 */
	public void setResourcesBuyers(ResourcesBuyers resourcesBuyers) {
		this.resourcesBuyers = resourcesBuyers;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @return the existStatus
	 */
	public String getExistStatus() {
		return existStatus;
	}

	/**
	 * @param existStatus the existStatus to set
	 */
	public void setExistStatus(String existStatus) {
		this.existStatus = existStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Journal [chineseTitle=" + chineseTitle + ", englishTitle="
				+ englishTitle + ", abbreviationTitle=" + abbreviationTitle
				+ ", titleEvolution=" + titleEvolution + ", issn=" + issn
				+ ", languages=" + languages + ", publishName=" + publishName
				+ ", publishYear=" + publishYear + ", caption=" + caption
				+ ", url=" + url + ", numB=" + numB + ", publication="
				+ publication + ", congressClassification="
				+ congressClassification + ", version=" + version
				+ ", resourcesBuyers=" + resourcesBuyers + ", customers="
				+ customers + ", existStatus=" + existStatus + "]";
	}

	public Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Journal(String chineseTitle, String englishTitle,
			String abbreviationTitle, String titleEvolution, String issn,
			String languages, String publishName, String publishYear,
			String caption, String url, String numB, String publication,
			String congressClassification, int version,
			ResourcesBuyers resourcesBuyers, List<Customer> customers,
			String existStatus) {
		super();
		this.chineseTitle = chineseTitle;
		this.englishTitle = englishTitle;
		this.abbreviationTitle = abbreviationTitle;
		this.titleEvolution = titleEvolution;
		this.issn = issn;
		this.languages = languages;
		this.publishName = publishName;
		this.publishYear = publishYear;
		this.caption = caption;
		this.url = url;
		this.numB = numB;
		this.publication = publication;
		this.congressClassification = congressClassification;
		this.version = version;
		this.resourcesBuyers = resourcesBuyers;
		this.customers = customers;
		this.existStatus = existStatus;
	}

}
