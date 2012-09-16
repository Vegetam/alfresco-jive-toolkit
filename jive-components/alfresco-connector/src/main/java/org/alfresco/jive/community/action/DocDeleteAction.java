/*
 * Copyright 2011-2012 Alfresco Software Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is part of an unsupported extension to Alfresco.
 */

package org.alfresco.jive.community.action;

import org.alfresco.jive.cmis.manager.AlfrescoNavigationManager;
import org.alfresco.jive.cmis.manager.RemoteDocument;
import org.alfresco.jive.community.impl.ConnectorConstants;

/**
 *  Custom action to delete a document. Removes socialized aspect from document of Managed type. 
 */
@SuppressWarnings("serial")
public class DocDeleteAction extends
		com.jivesoftware.community.action.DocDeleteAction {

	private AlfrescoNavigationManager alfrescoNavigationManager;
	
	public String execute() {
				
		String code = super.execute();
		
		// remove socialized aspect from Alfresco
		if(code.equals(SUCCESS) && isManagedDocument()) {
			String objectId = getDocument().getProperties().get(ConnectorConstants.REMOTE_OBJECT_PROPERTY);
        	RemoteDocument remoteDoc = new RemoteDocument(objectId, null);
        	
			alfrescoNavigationManager.deleteDocument(remoteDoc);
		}
		
		return code;
	}
	
	public boolean isManagedDocument() {
		return getDocument().getDocumentType().getID() == ConnectorConstants.MANAGED_TYPE;
	}
	
	public void setAlfrescoNavigationManager(
			AlfrescoNavigationManager alfrescoNavigationManager) {
		this.alfrescoNavigationManager = alfrescoNavigationManager;
	}
}
