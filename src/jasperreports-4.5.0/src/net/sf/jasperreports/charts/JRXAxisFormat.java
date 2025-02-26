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
package net.sf.jasperreports.charts;

import java.awt.Color;

import net.sf.jasperreports.engine.JRFont;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRXAxisFormat.java 4595 2011-09-08 15:55:10Z teodord $
 */
public interface JRXAxisFormat
{
	
	/**
	 * 
	 */
	public JRFont getXAxisLabelFont();
	
	/**
	 * 
	 */
	public Color getXAxisLabelColor();
	
	/**
	 * 
	 */
	public Color getOwnXAxisLabelColor();
	
	/**
	 * 
	 */
	public JRFont getXAxisTickLabelFont();
	
	/**
	 * 
	 */
	public Color getXAxisTickLabelColor();

	/**
	 * 
	 */
	public Color getOwnXAxisTickLabelColor();

	/**
	 * 
	 */
	public String getXAxisTickLabelMask();

	/**
	 * 
	 */
	public Boolean getXAxisVerticalTickLabels();
	
	/**
	 * 
	 */
	public Color getXAxisLineColor();

	/**
	 * 
	 */
	public Color getOwnXAxisLineColor();

}
