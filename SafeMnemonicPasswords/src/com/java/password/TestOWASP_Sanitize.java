package com.java.password;

import org.owasp.html.Sanitizers;
import org.owasp.html.PolicyFactory;
import org.owasp.encoder.*;

//https://github.com/owasp/java-html-sanitizer 

public class TestOWASP_Sanitize {

	public static void main(String[] args) {
		String xss_script1="";
		
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		String safeHTML = policy.sanitize("<script>alert(2)</script>");
		  System.out.println(safeHTML);
		  /*
		  example
		  https://github.com/OWASP/owasp-java-encoder/
		    PrintWriter out = ....;
		    out.println("<textarea>"+Encode.forHtml(userData)+"</textarea>");
		   */
	}
}
