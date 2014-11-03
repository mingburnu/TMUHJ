package j.index.test;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XlsImporter {
	@SuppressWarnings("unused")
	void imports() throws IOException {
		FileInputStream fileInputStream = new FileInputStream("C:\\journal.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook.getSheet("sheet1");

		for (int i = 0; i < 0; i++) {
			HSSFRow row = worksheet.getRow(i);
			HSSFCell Dbchttitle = row.getCell(0);
			String dbChtTitle;
			if (Dbchttitle == null) {
				dbChtTitle = "";
			} else {
				dbChtTitle = Dbchttitle.getStringCellValue();
			}

			HSSFCell Dbengtitle = row.getCell(1);
			String dbEngTitle;
			if (Dbengtitle == null) {
				dbEngTitle = "";
			} else {
				dbEngTitle = Dbengtitle.getStringCellValue();
			}

			HSSFCell publishname = row.getCell(2);
			String publishName;
			if (publishname == null) {
				publishName = "";
			} else {
				publishName = publishname.getStringCellValue();
			}

			HSSFCell languages = row.getCell(3);
			String language;
			if (languages == null) {
				language = "";
			} else {
				language = languages.getStringCellValue();
			}

			HSSFCell IncludedSpecies = row.getCell(4);
			String includedSpecies;
			if (IncludedSpecies == null) {
				includedSpecies = "";
			} else {
				includedSpecies = IncludedSpecies.getStringCellValue();
			}

			HSSFCell yearpublication = row.getCell(5);
			String publishYear;
			if (yearpublication == null) {
				publishYear = "";
			} else {
				publishYear = yearpublication.getStringCellValue();
			}

			HSSFCell publication = row.getCell(7);
			String publicationStr;
			if (publication == null) {
				publicationStr = "";
			} else {
				publicationStr = publication.getStringCellValue();
			}

			HSSFCell congressclassification = row.getCell(8);
			String congressClassification;
			if (congressclassification == null) {
				congressClassification = "";
			} else {
				congressClassification = congressclassification
						.getStringCellValue();
			}
		}

	}

	public static void main(String[] args) {

	}
}
