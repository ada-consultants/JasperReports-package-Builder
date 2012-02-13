/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.repo;

import java.io.InputStream;




/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: RepositoryService.java 4603 2011-09-13 12:35:32Z lucianc $
 */
public interface RepositoryService
{
	/**
	 * 
	 *
	public <T extends RepositoryContext> T createContext();

	/**
	 * 
	 */
	public void setContext(RepositoryContext context);

	/**
	 * 
	 */
	public void revertContext();

	/**
	 * 
	 */
	public InputStream getInputStream(String uri);
	
	/**
	 * 
	 */
	public Resource getResource(String uri);
	
	/**
	 * 
	 */
	public void saveResource(String uri, Resource resource);
	
	/**
	 * 
	 */
	public <K extends Resource> K getResource(String uri, Class<K> resourceType);
}
