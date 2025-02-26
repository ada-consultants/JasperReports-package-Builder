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
package net.sf.jasperreports.charts.base;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.charts.JRTimeSeriesPlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseTimeSeriesPlot.java 3938 2010-08-19 14:59:36Z teodord $
 */
public class JRBaseTimeSeriesPlot extends JRBaseChartPlot implements JRTimeSeriesPlot 
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SHOW_LINES = "showLines";
	
	public static final String PROPERTY_SHOW_SHAPES = "showShapes";
	
	protected JRExpression timeAxisLabelExpression;
	protected JRFont timeAxisLabelFont;
	protected Color timeAxisLabelColor;
	protected JRFont timeAxisTickLabelFont;
	protected Color timeAxisTickLabelColor;
	protected String timeAxisTickLabelMask;
	protected Boolean timeAxisVerticalTickLabels;
	protected Color timeAxisLineColor;

	protected JRExpression valueAxisLabelExpression;
	protected JRExpression rangeAxisMinValueExpression;
	protected JRExpression rangeAxisMaxValueExpression;
	protected JRExpression domainAxisMinValueExpression;
	protected JRExpression domainAxisMaxValueExpression;
	protected JRFont valueAxisLabelFont;
	protected Color valueAxisLabelColor;
	protected JRFont valueAxisTickLabelFont;
	protected Color valueAxisTickLabelColor;
	protected String valueAxisTickLabelMask;
	protected Boolean valueAxisVerticalTickLabels;
	protected Color valueAxisLineColor;
	
	Boolean showShapes;
	Boolean showLines;
	
	/**
	 * 
	 */
	protected JRBaseTimeSeriesPlot(JRChartPlot plot, JRChart chart)
	{
		super(plot, chart);
		
		JRTimeSeriesPlot timeSeriesPlot = plot instanceof JRTimeSeriesPlot ? (JRTimeSeriesPlot)plot : null;
		if (timeSeriesPlot == null)
		{
			timeAxisLabelFont = new JRBaseFont(chart, null);
			timeAxisTickLabelFont = new JRBaseFont(chart, null);
			valueAxisLabelFont = new JRBaseFont(chart, null);
			valueAxisTickLabelFont = new JRBaseFont(chart, null);
		}
		else
		{//FIXMETHEME you could copy more things
			timeAxisLabelFont = new JRBaseFont(chart, timeSeriesPlot.getTimeAxisLabelFont());
			timeAxisTickLabelFont = new JRBaseFont(chart, timeSeriesPlot.getTimeAxisTickLabelFont());
			valueAxisLabelFont = new JRBaseFont(chart, timeSeriesPlot.getValueAxisLabelFont());
			valueAxisTickLabelFont = new JRBaseFont(chart, timeSeriesPlot.getValueAxisTickLabelFont());
		}
	}
	
	/**
	 * 
	 */
	public JRBaseTimeSeriesPlot(JRTimeSeriesPlot plot, JRBaseObjectFactory factory)
	{
		super(plot, factory);
		
		showLines = plot.getShowLines();
		showShapes = plot.getShowShapes();
		
		timeAxisLabelExpression = factory.getExpression( plot.getTimeAxisLabelExpression() );
		timeAxisLabelFont = new JRBaseFont(plot.getChart(), plot.getTimeAxisLabelFont());//FIXMETHEME check this plot.getChart(); don't we get the design chart?
		timeAxisLabelColor = plot.getOwnTimeAxisLabelColor();
		timeAxisTickLabelFont = new JRBaseFont(plot.getChart(), plot.getTimeAxisTickLabelFont());
		timeAxisTickLabelColor = plot.getOwnTimeAxisTickLabelColor();
		timeAxisTickLabelMask = plot.getTimeAxisTickLabelMask();
		timeAxisVerticalTickLabels = plot.getTimeAxisVerticalTickLabels();
		timeAxisLineColor = plot.getOwnTimeAxisLineColor();
		
		valueAxisLabelExpression = factory.getExpression( plot.getValueAxisLabelExpression() );
		domainAxisMinValueExpression = factory.getExpression( plot.getDomainAxisMinValueExpression() );
		domainAxisMaxValueExpression = factory.getExpression( plot.getDomainAxisMaxValueExpression() );
		rangeAxisMinValueExpression = factory.getExpression( plot.getRangeAxisMinValueExpression() );
		rangeAxisMaxValueExpression = factory.getExpression( plot.getRangeAxisMaxValueExpression() );
		valueAxisLabelFont = new JRBaseFont(plot.getChart(), plot.getValueAxisLabelFont());
		valueAxisLabelColor = plot.getOwnValueAxisLabelColor();
		valueAxisTickLabelFont = new JRBaseFont(plot.getChart(), plot.getValueAxisTickLabelFont());
		valueAxisTickLabelColor = plot.getOwnValueAxisTickLabelColor();
		valueAxisTickLabelMask = plot.getValueAxisTickLabelMask();
		valueAxisVerticalTickLabels = plot.getValueAxisVerticalTickLabels();
		valueAxisLineColor = plot.getOwnValueAxisTickLabelColor();
	}
	
	/**
	 * 
	 */
	public JRExpression getTimeAxisLabelExpression(){
		return timeAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getTimeAxisLabelFont()
	{
		return timeAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getTimeAxisLabelColor()
	{
		return JRStyleResolver.getTimeAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnTimeAxisLabelColor()
	{
		return timeAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getTimeAxisTickLabelFont()
	{
		return timeAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getTimeAxisTickLabelColor()
	{
		return JRStyleResolver.getTimeAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnTimeAxisTickLabelColor()
	{
		return timeAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getTimeAxisTickLabelMask()
	{
		return timeAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Boolean getTimeAxisVerticalTickLabels()
	{
		return timeAxisVerticalTickLabels;
	}

	/**
	 * 
	 */
	public Color getTimeAxisLineColor()
	{
		return JRStyleResolver.getTimeAxisLineColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnTimeAxisLineColor()
	{
		return timeAxisLineColor;
	}

	/**
	 * 
	 */
	public JRExpression getValueAxisLabelExpression(){
		return valueAxisLabelExpression;
	}

	/**
	 * 
	 */
	public JRExpression getDomainAxisMinValueExpression(){
		return domainAxisMinValueExpression;
	}

	/**
	 * 
	 */
	public JRExpression getDomainAxisMaxValueExpression(){
		return domainAxisMaxValueExpression;
	}

	/**
	 * 
	 */
	public JRExpression getRangeAxisMinValueExpression(){
		return rangeAxisMinValueExpression;
	}

	/**
	 * 
	 */
	public JRExpression getRangeAxisMaxValueExpression(){
		return rangeAxisMaxValueExpression;
	}

	/**
	 * 
	 */
	public JRFont getValueAxisLabelFont()
	{
		return valueAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getValueAxisLabelColor()
	{
		return JRStyleResolver.getValueAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnValueAxisLabelColor()
	{
		return valueAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getValueAxisTickLabelFont()
	{
		return valueAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getValueAxisTickLabelColor()
	{
		return JRStyleResolver.getValueAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnValueAxisTickLabelColor()
	{
		return valueAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getValueAxisTickLabelMask()
	{
		return valueAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Boolean getValueAxisVerticalTickLabels()
	{
		return valueAxisVerticalTickLabels;
	}

	/**
	 * 
	 */
	public Color getValueAxisLineColor()
	{
		return JRStyleResolver.getValueAxisLineColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnValueAxisLineColor()
	{
		return valueAxisLineColor;
	}
	
	/**
	 * @deprecated Replaced by {@link #getShowLines()}
	 */
	public boolean isShowLines(){
		return showLines == null ? true : showLines.booleanValue();
	}
	
	/**
	 * @deprecated Replaced by {@link #getShowShapes()}
	 */
	public boolean isShowShapes(){
		return showShapes == null ? true : showShapes.booleanValue();
	}
	
	/**
	 * @deprecated Replaced by {@link #setShowLines(Boolean)}
	 */
	public void setShowLines( boolean val ){
		setShowLines(Boolean.valueOf(val));
	}
	
	/**
	 * @deprecated Replaced by {@link #setShowShapes(Boolean)}
	 */
	public void setShowShapes( boolean val ){
		setShowShapes(Boolean.valueOf(val));
	}

	/**
	 * 
	 */
	public Boolean getShowLines(){
		return showLines;
	}
	
	/**
	 * 
	 */
	public Boolean getShowShapes(){
		return showShapes;
	}
	
	/**
	 * 
	 */
	public void setShowLines( Boolean val ){
		Boolean old = this.showLines;
		this.showLines = val;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LINES, old, this.showLines);
	}
	
	/**
	 * 
	 */
	public void setShowShapes( Boolean val ){
		Boolean old = this.showShapes;
		this.showShapes = val;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_SHAPES, old, this.showShapes);
	}

	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	/**
	 *
	 */
	public Object clone(JRChart parentChart) 
	{
		JRBaseTimeSeriesPlot clone = (JRBaseTimeSeriesPlot)super.clone(parentChart);
		if (timeAxisLabelExpression != null)
		{
			clone.timeAxisLabelExpression = (JRExpression)timeAxisLabelExpression.clone();
		}
		if (valueAxisLabelExpression != null)
		{
			clone.valueAxisLabelExpression = (JRExpression)valueAxisLabelExpression.clone();
		}
		if (domainAxisMinValueExpression != null)
		{
			clone.domainAxisMinValueExpression = (JRExpression)domainAxisMinValueExpression.clone();
		}
		if (domainAxisMaxValueExpression != null)
		{
			clone.domainAxisMaxValueExpression = (JRExpression)domainAxisMaxValueExpression.clone();
		}
		if (rangeAxisMinValueExpression != null)
		{
			clone.rangeAxisMinValueExpression = (JRExpression)rangeAxisMinValueExpression.clone();
		}
		if (rangeAxisMaxValueExpression != null)
		{
			clone.rangeAxisMaxValueExpression = (JRExpression)rangeAxisMaxValueExpression.clone();
		}
		return clone;
	}
	
	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private boolean isShowShapes = true;
	/**
	 * @deprecated
	 */
	private boolean isShowLines = true;

	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_1_3)
		{
			showShapes = Boolean.valueOf(isShowShapes);
			showLines = Boolean.valueOf(isShowLines);
		}
	}
	
}
