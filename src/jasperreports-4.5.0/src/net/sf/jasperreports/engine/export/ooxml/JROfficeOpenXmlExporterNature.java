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

/*
 * Contributors:
 * Greg Hilton 
 */

package net.sf.jasperreports.engine.export.ooxml;

import java.util.List;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.export.ExporterFilter;
import net.sf.jasperreports.engine.export.ExporterNature;
import net.sf.jasperreports.engine.util.JRProperties.PropertySuffix;

/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: JROfficeOpenXmlExporterNature.java 4713 2011-10-18 14:55:22Z shertage $
 */
public abstract class JROfficeOpenXmlExporterNature implements ExporterNature
{
	protected ExporterFilter filter;

	/**
	 * 
	 */
	public JROfficeOpenXmlExporterNature(ExporterFilter filter)
	{
		this.filter = filter;
	}
	
	/**
	 * 
	 */
	public boolean isToExport(JRPrintElement element)
	{
		return (filter == null || filter.isToExport(element));
	}
	
	/**
	 * 
	 */
	public boolean isSplitSharedRowSpan()
	{
		return false;
	}

	/**
	 * 
	 */
	public boolean isSpanCells()
	{
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isIgnoreLastRow()
	{
		return true;
	}

	public boolean isHorizontallyMergeEmptyCells()
	{
		return false;
	}

	/**
	 * Specifies whether empty page margins should be ignored
	 */
	public boolean isIgnorePageMargins()
	{
		return false;
	}
	
	/**
	 *
	 */
	public boolean isBreakBeforeRow(JRPrintElement element)
	{
		return false;
	}
	
	/**
	 *
	 */
	public boolean isBreakAfterRow(JRPrintElement element)
	{
		return false;
	}
	
	/**
	 *
	 */
	public Boolean getRowAutoFit(JRPrintElement element)
	{
		return null;
	}
	
	/**
	 *
	 */
	public Boolean getColumnAutoFit(JRPrintElement element)
	{
		return null;
	}

	/**
	 *
	 */
	public Integer getCustomColumnWidth(JRPrintElement element)
	{
		return null;
	}

	public Float getColumnWidthRatio(JRPrintElement element) 
	{
		return null;
	}
	
	public List<PropertySuffix> getRowLevelSuffixes(JRPrintElement element)
	{
		return null;
	}

}
