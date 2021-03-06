/*******************************************************************************
* Copyright (c) 2019 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* Contributors:
*     Red Hat Inc. - initial API and implementation
*******************************************************************************/
package org.eclipse.lsp4xml.extensions.xsd.participants;

import java.util.List;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.ReferenceContext;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;
import org.eclipse.lsp4xml.dom.DOMAttr;
import org.eclipse.lsp4xml.dom.DOMDocument;
import org.eclipse.lsp4xml.dom.DOMNode;
import org.eclipse.lsp4xml.extensions.xsd.utils.XSDUtils;
import org.eclipse.lsp4xml.services.extensions.AbstractReferenceParticipant;
import org.eclipse.lsp4xml.utils.DOMUtils;
import org.eclipse.lsp4xml.utils.XMLPositionUtility;

/**
 * XSD reference
 * 
 * @author Angelo ZERR
 *
 */
public class XSDReferenceParticipant extends AbstractReferenceParticipant {

	@Override
	protected boolean match(DOMDocument document) {
		return DOMUtils.isXSD(document);
	}

	@Override
	protected void findReferences(DOMNode node, Position position, int offset, ReferenceContext context,
			List<Location> locations, CancelChecker cancelChecker) {
		DOMAttr attr = node.findAttrAt(offset);
		if (attr != null) {
			node = attr;
		}
		XSDUtils.searchXSOriginAttributes(node,
				(origin, target) -> locations.add(XMLPositionUtility.createLocation(origin.getNodeAttrValue())),
				cancelChecker);
	}

}
