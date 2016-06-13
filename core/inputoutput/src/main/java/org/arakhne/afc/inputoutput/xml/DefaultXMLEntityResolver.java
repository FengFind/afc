/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.inputoutput.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.arakhne.afc.vmutil.FileSystem;
import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.URISchemeType;

/** A class that permits to load an external XML resource.
 *
 * <p>It is based on the systemId and tries to find the file
 * described in the systemId. To do this it tries the following
 * cases in turn and replies the corresponding resource:
 * <ol>
 * <li>if {@code systemId} is a local file ie, an {@code file://} URL
 * with or without the URL scheme, if the filename is absolute,
 * and if the file exists then the file's content is replied.<br>
 * Example: {@code file:///tmp/mydtd-1.0.dtd}</li>
 * <li>if {@code systemId} is a local file ie, an {@code file://} URL
 * with or without the URL scheme, if the filename is relative,
 * and if a file located relatively to the current directory of
 * the XML file exists then the file's content is replied.<br>
 * Example: {@code mydirectory/mydtd-1.0.dtd}</li>
 * <li>if {@code systemId} is a local file ie, an {@code file://} URL
 * with or without the URL scheme, and if the filename is absolute then
 * the {@link Class#getResource(String)} is invoked with the
 * {@code systemId} as parameter.<br>
 * Example: {@code /fr/utbm/set/package/mydtd-1.0.dtd}</li>
 * <li>if a {@code searchPath} was specified to the constructor and if
 * a file located relatively to this {@code searchPath} exists, then
 * the file's content is replied.</li>
 * </ol>
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 14.0
 */
public class DefaultXMLEntityResolver implements EntityResolver {

	private final URL searchPath;

	private boolean emptyExternalResource;

	/** Constructor.
	 */
	public DefaultXMLEntityResolver() {
		this.searchPath = null;
		this.emptyExternalResource = false;
	}

	/**
	 * @param searchPath is the directory which corresponds to a search path.
	 * @throws MalformedURLException if the file cannot be converted to an URL.
	 */
	public DefaultXMLEntityResolver(File searchPath) throws MalformedURLException {
		this(searchPath.toURI().toURL());
	}

	/**
	 * @param searchPath is the directory which corresponds to a search path.
	 */
	public DefaultXMLEntityResolver(URL searchPath) {
		this.searchPath = searchPath;
		this.emptyExternalResource = false;
	}

	/**
	 * @param assumeEmptyExternalResource if <code>true</code> this entity resolver
	 *     replies an empty input source when it can't find an external resource. If <code>false</code>
	 *     this resolver will ask to the default XML resolver to find the external resource.
	 */
	public DefaultXMLEntityResolver(boolean assumeEmptyExternalResource) {
		this.searchPath = null;
		this.emptyExternalResource = assumeEmptyExternalResource;
	}

	/**
	 * @param searchPath is the directory which corresponds to a search path.
	 * @param assumeEmptyExternalResource if <code>true</code> this entity resolver
	 *     replies an empty input source when it can't find an external resource. If <code>false</code>
	 *     this resolver will ask to the default XML resolver to find the external resource.
	 * @throws MalformedURLException if the file cannot be converted to an URL.
	 */
	public DefaultXMLEntityResolver(File searchPath, boolean assumeEmptyExternalResource) throws MalformedURLException {
		this(searchPath.toURI().toURL(), assumeEmptyExternalResource);
	}

	/**
	 * @param searchPath is the directory which corresponds to a search path.
	 * @param assumeEmptyExternalResource if <code>true</code> this entity resolver
	 *     replies an empty input source when it can't find an external resource. If <code>false</code>
	 *     this resolver will ask to the default XML resolver to find the external resource.
	 */
	public DefaultXMLEntityResolver(URL searchPath, boolean assumeEmptyExternalResource) {
		this.searchPath = searchPath;
		this.emptyExternalResource = assumeEmptyExternalResource;
	}

	@SuppressWarnings("resource")
	private static InputSource search(String systemId, URL path) {
		InputStream systemIdStream = null;
		URL systemUrl = null;
		try {
			systemUrl = new URL(systemId);
			if (path != null) {
				systemUrl = FileSystem.makeAbsolute(path, systemUrl);
			}
			systemIdStream = systemUrl.openStream();
			if (systemIdStream != null) {
				return new InputSource(systemIdStream);
			}
		} catch (Exception e) {
			//
		}

		if (systemUrl != null && URISchemeType.getSchemeType(systemUrl).isFileBasedScheme()) {
			final String file = systemUrl.getPath();
			systemIdStream = Resources.getResourceAsStream(file);
			if (systemIdStream != null) {
				return new InputSource(systemIdStream);
			}
		} else {
			String id = systemId;
			if (path != null) {
				id = FileSystem.join(path, systemId).getPath();
			}
			systemIdStream = Resources.getResourceAsStream(id);
			if (systemIdStream != null) {
				return new InputSource(systemIdStream);
			}
		}

		return null;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if (systemId != null && !"".equals(systemId)) { //$NON-NLS-1$
			InputSource is = search(systemId, null);
			if (is != null) {
				return is;
			}

			if (this.searchPath != null) {
				is = search(systemId, this.searchPath);
				if (is != null) {
					return is;
				}
			}
		}

		if (this.emptyExternalResource) {
			return new InputSource(new ByteArrayInputStream(new byte[0]));
		}

		return null;
	}

}
