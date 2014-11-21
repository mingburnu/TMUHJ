package com.asiaworld.tmuhj.core.tag;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * BodyTag
 * @author David Hsu
 * @version 2014/3/14
 */
public class BodyTag extends SimpleTagSupport {

	private String height;
	
	@Override
	public void doTag() throws JspException, IOException {
		final JspWriter jspWriter = getJspContext().getOut();
        final StringWriter stringWriter = new StringWriter();
		final StringBuffer bodyContent = new StringBuffer();
		
		final String menuPath = ((PageContext) getJspContext()).getServletContext().getRealPath("/WEB-INF/jsp/layout/menu.jsp");
		
		//TODO: 改寫FileUtils
		File file = new File(menuPath);
		InputStreamReader in = new FileReader(file);
        char c[] = new char[(int) file.length()];
        in.read(c);
        in.close();
        
		// Execute the tag's body into an internal writer
        getJspBody().invoke(stringWriter);
		
		bodyContent.append("<table> <tr style=\"height: ").append(height)
			.append("\"/> <td valign=\"top\">")
			.append(c)
			.append("</td>")
			.append("<td valign=\"top\">")
			.append(stringWriter.getBuffer())
			.append("</td></tr></table>");
		
		jspWriter.write(bodyContent.toString());
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
}
