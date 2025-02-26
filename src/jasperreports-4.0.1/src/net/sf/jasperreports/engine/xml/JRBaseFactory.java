/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
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
package net.sf.jasperreports.engine.xml;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseFactory.java 3939 2010-08-20 09:52:00Z teodord $
 */
public abstract class JRBaseFactory implements ObjectCreationFactory
{


	/**
	 *
	 */
	protected transient Digester digester;


	/**
	 *
	 */
	public Digester getDigester() 
	{
		return this.digester;
	}
	

	/**
	 *
	 */
	public void setDigester(Digester digester) 
	{
		this.digester = digester;
	}
		

}
