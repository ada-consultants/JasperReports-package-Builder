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
package net.sf.jasperreports.j2ee.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;


/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 * @version $Id: PptxServlet.java 4081 2010-12-10 13:30:23Z narcism $
 */
public class PptxServlet extends BaseHttpServlet
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	/**
	 *
	 */
	public void service(
		HttpServletRequest request,
		HttpServletResponse response
		) throws IOException, ServletException
	{
		List jasperPrintList = BaseHttpServlet.getJasperPrintList(request);

		if (jasperPrintList == null)
		{
			throw new ServletException("No JasperPrint documents found on the HTTP session.");
		}
		
		Boolean isBuffered = Boolean.valueOf(request.getParameter(BaseHttpServlet.BUFFERED_OUTPUT_REQUEST_PARAMETER));
		if (isBuffered.booleanValue())
		{
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPptxExporter exporter = new JRPptxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);
			
			try 
			{
				exporter.exportReport();
				fbos.close();

				if (fbos.size() > 0)
				{
					response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
					response.setHeader("Content-Disposition", "inline; filename=\"file.pptx\"");
					response.setContentLength(fbos.size());
					ServletOutputStream ouputStream = response.getOutputStream();
	
					try
					{
						fbos.writeData(ouputStream);
						fbos.dispose();
						ouputStream.flush();
					}
					finally
					{
						if (ouputStream != null)
						{
							try
							{
								ouputStream.close();
							}
							catch (IOException ex)
							{
							}
						}
					}
				}
			} 
			catch (JRException e) 
			{
				throw new ServletException(e);
			}
			finally
			{
				fbos.close();
				fbos.dispose();
			}
		}
		else
		{
			response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
			response.setHeader("Content-Disposition", "inline; filename=\"file.pptx\"");
			
			JRPptxExporter exporter = new JRPptxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			
			OutputStream ouputStream = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);

			try 
			{
				exporter.exportReport();
			} 
			catch (JRException e) 
			{
				throw new ServletException(e);
			}
			finally
			{
				if (ouputStream != null)
				{
					try
					{
						ouputStream.close();
					}
					catch (IOException ex)
					{
					}
				}
			}
		}
	}


}
