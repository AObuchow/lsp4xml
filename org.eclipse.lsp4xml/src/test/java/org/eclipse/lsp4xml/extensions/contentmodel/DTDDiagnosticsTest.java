/**
 *  Copyright (c) 2018 Angelo ZERR
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.lsp4xml.extensions.contentmodel;

import static org.eclipse.lsp4xml.XMLAssert.d;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4xml.XMLAssert;
import org.eclipse.lsp4xml.extensions.contentmodel.participants.DTDErrorCode;
import org.junit.Test;

/**
 * XML diagnostics services tests
 *
 */
public class DTDDiagnosticsTest {

	@Test
	public void MSG_ELEMENT_NOT_DECLARED() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \r\n" + //
				"<!DOCTYPE web-app\r\n" + //
				"   PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\"\r\n" + //
				"   \"http://java.sun.com/dtd/web-app_2_3.dtd\">\r\n" + //
				"\r\n" + //
				"<web-app>\r\n" + //
				"	<XXX></XXX>\r\n" + //
				"</web-app>";
		testDiagnosticsFor(xml, d(6, 2, 5, DTDErrorCode.MSG_ELEMENT_NOT_DECLARED),
				d(7, 10, 10, DTDErrorCode.MSG_CONTENT_INVALID));
	}

	@Test
	public void testDoctypeDiagnosticsRefresh() throws Exception {
		//@formatter:off
		String xml = "<?xml version=\"1.0\"?>\n" + 
					"<!DOCTYPE student [\n" + 
					"  <!ELEMENT student (surname,id)>\n" + 
					"  <!ELEMENT surname (#PCDATA)>\n" + 
					"]>\n" + 
					"<student>\n" + 
					"  <surname>Smith</surname>\n" + 
					"  <id>567896</id>\n" + 
					"</student>";
		//@formatter:on
		testDiagnosticsFor(xml, d(7, 3, 5, DTDErrorCode.MSG_ELEMENT_NOT_DECLARED));

		//@formatter:off
		xml = "<?xml version=\"1.0\"?>\n" + 
			"<!DOCTYPE student [\n" + 
			"  <!ELEMENT student (surname,id)>\n" + 
			"  <!ELEMENT surname (#PCDATA)>\n" + 
			"  <!ELEMENT id (#PCDATA)>\n" + 
			"]>\n" + 
			"<student>\n" + 
			"  <surname>Smith</surname>\n" + 
			"  <id>567896</id>\n" + 
			"</student>";
		//@formatter:on
		testDiagnosticsFor(xml, new Diagnostic[0]);

	}

	private static void testDiagnosticsFor(String xml, Diagnostic... expected) {
		XMLAssert.testDiagnosticsFor(xml, "src/test/resources/catalogs/catalog.xml", expected);
	}

}
