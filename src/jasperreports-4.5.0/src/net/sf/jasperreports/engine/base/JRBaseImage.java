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
package net.sf.jasperreports.engine.base;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.HyperlinkTargetEnum;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRCloneUtils;
import net.sf.jasperreports.engine.util.JRStyleResolver;


/**
 * The actual implementation of a graphic element representing an image.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseImage.java 4595 2011-09-08 15:55:10Z teodord $
 */
public class JRBaseImage extends JRBaseGraphicElement implements JRImage
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/*
	 * Image properties
	 */

	public static final String PROPERTY_LAZY = "lazy";
	
	public static final String PROPERTY_ON_ERROR_TYPE = "onErrorType";
	
	public static final String PROPERTY_USING_CACHE = "usingCache";
	
	
	/**
	 *
	 */
	protected ScaleImageEnum scaleImageValue;
	protected HorizontalAlignEnum horizontalAlignmentValue;
	protected VerticalAlignEnum verticalAlignmentValue;
	protected Boolean isUsingCache;
	protected boolean isLazy;
	protected OnErrorTypeEnum onErrorTypeValue = OnErrorTypeEnum.ERROR;
	protected EvaluationTimeEnum evaluationTimeValue = EvaluationTimeEnum.NOW;
	protected String linkType;
	protected String linkTarget;
	private JRHyperlinkParameter[] hyperlinkParameters;

	/**
	 *
	 */
	protected JRLineBox lineBox;

	/**
	 *
	 */
	protected JRGroup evaluationGroup;
	protected JRExpression expression;
	protected JRExpression anchorNameExpression;
	protected JRExpression hyperlinkReferenceExpression;
	protected JRExpression hyperlinkAnchorExpression;
	protected JRExpression hyperlinkPageExpression;
	private JRExpression hyperlinkTooltipExpression;

	/**
	 * The bookmark level for the anchor associated with this image.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	/**
	 *
	 *
	protected JRBaseImage()
	{
		super();
	}
		

	/**
	 * Initializes properties that are specific to images. Common properties are initialized by its
	 * parent constructors.
	 * @param image an element whose properties are copied to this element. Usually it is a
	 * {@link net.sf.jasperreports.engine.design.JRDesignImage} that must be transformed into an
	 * <tt>JRBaseImage</tt> at compile time.
	 * @param factory a factory used in the compile process
	 */
	protected JRBaseImage(JRImage image, JRBaseObjectFactory factory)
	{
		super(image, factory);
		
		scaleImageValue = image.getOwnScaleImageValue();
		horizontalAlignmentValue = image.getOwnHorizontalAlignmentValue();
		verticalAlignmentValue = image.getOwnVerticalAlignmentValue();
		isUsingCache = image.getUsingCache();
		isLazy = image.isLazy();
		onErrorTypeValue = image.getOnErrorTypeValue();
		evaluationTimeValue = image.getEvaluationTimeValue();
		linkType = image.getLinkType();
		linkTarget = image.getLinkTarget();
		hyperlinkParameters = JRBaseHyperlink.copyHyperlinkParameters(image, factory);

		lineBox = image.getLineBox().clone(this);

		evaluationGroup = factory.getGroup(image.getEvaluationGroup());
		expression = factory.getExpression(image.getExpression());
		anchorNameExpression = factory.getExpression(image.getAnchorNameExpression());
		hyperlinkReferenceExpression = factory.getExpression(image.getHyperlinkReferenceExpression());
		hyperlinkAnchorExpression = factory.getExpression(image.getHyperlinkAnchorExpression());
		hyperlinkPageExpression = factory.getExpression(image.getHyperlinkPageExpression());
		hyperlinkTooltipExpression = factory.getExpression(image.getHyperlinkTooltipExpression());
		bookmarkLevel = image.getBookmarkLevel();
	}


	/**
	 *
	 */
	public ModeEnum getModeValue()
	{
		return JRStyleResolver.getMode(this, ModeEnum.TRANSPARENT);
	}

	/**
	 * 
	 */
	public ScaleImageEnum getScaleImageValue()
	{
		return JRStyleResolver.getScaleImageValue(this);
	}

	/**
	 * 
	 */
	public ScaleImageEnum getOwnScaleImageValue()
	{
		return this.scaleImageValue;
	}

	/**
	 * 
	 */
	public void setScaleImage(ScaleImageEnum scaleImageValue)
	{
		Object old = this.scaleImageValue;
		this.scaleImageValue = scaleImageValue;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_SCALE_IMAGE, old, this.scaleImageValue);
	}


	/**
	 *
	 */
	public HorizontalAlignEnum getHorizontalAlignmentValue()
	{
		return JRStyleResolver.getHorizontalAlignmentValue(this);
	}

	public HorizontalAlignEnum getOwnHorizontalAlignmentValue()
	{
		return horizontalAlignmentValue;
	}

	/**
	 *
	 */
	public void setHorizontalAlignment(HorizontalAlignEnum horizontalAlignmentValue)
	{
		Object old = this.horizontalAlignmentValue;
		this.horizontalAlignmentValue = horizontalAlignmentValue;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT, old, this.horizontalAlignmentValue);
	}
		
	/**
	 *
	 */
	public VerticalAlignEnum getVerticalAlignmentValue()
	{
		return JRStyleResolver.getVerticalAlignmentValue(this);
	}

	public VerticalAlignEnum getOwnVerticalAlignmentValue()
	{
		return verticalAlignmentValue;
	}

	/**
	 *
	 */
	public void setVerticalAlignment(VerticalAlignEnum verticalAlignmentValue)
	{
		Object old = this.verticalAlignmentValue;
		this.verticalAlignmentValue = verticalAlignmentValue;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT, old, this.verticalAlignmentValue);
	}
		
	/**
	 * @deprecated Replaced by {@link #getUsingCache()}.
	 */
	public boolean isUsingCache()
	{
		if (isUsingCache == null)
		{
			if (getExpression() != null)
			{
				return String.class.getName().equals(getExpression().getValueClassName());
			}
			return true;
		}
		return isUsingCache.booleanValue();
	}

	/**
	 * @deprecated Replaced by {@link #getUsingCache()}.
	 */
	public Boolean isOwnUsingCache()
	{
		return isUsingCache;
	}

	/**
	 *
	 */
	public Boolean getUsingCache()
	{
		return isUsingCache;
	}

	/**
	 *
	 */
	public void setUsingCache(boolean isUsingCache)
	{
		setUsingCache(isUsingCache ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 *
	 */
	public void setUsingCache(Boolean isUsingCache)
	{
		Object old = this.isUsingCache;
		this.isUsingCache = isUsingCache;
		getEventSupport().firePropertyChange(PROPERTY_USING_CACHE, old, this.isUsingCache);
	}

	/**
	 *
	 */
	public boolean isLazy()
	{
		return isLazy;
	}

	/**
	 *
	 */
	public void setLazy(boolean isLazy)
	{
		boolean old = this.isLazy;
		this.isLazy = isLazy;
		getEventSupport().firePropertyChange(PROPERTY_LAZY, old, this.isLazy);
	}

	/**
	 * 
	 */
	public OnErrorTypeEnum getOnErrorTypeValue()
	{
		return this.onErrorTypeValue;
	}

	/**
	 * 
	 */
	public void setOnErrorType(OnErrorTypeEnum onErrorTypeValue)
	{
		OnErrorTypeEnum old = this.onErrorTypeValue;
		this.onErrorTypeValue = onErrorTypeValue;
		getEventSupport().firePropertyChange(PROPERTY_ON_ERROR_TYPE, old, this.onErrorTypeValue);
	}

	/**
	 *
	 */
	public EvaluationTimeEnum getEvaluationTimeValue()
	{
		return evaluationTimeValue;
	}
		
	/**
	 *
	 */
	public JRLineBox getLineBox()
	{
		return lineBox;
	}

	/**
	 * @deprecated Replaced by {@link #getHyperlinkTypeValue()}.
	 */
	public byte getHyperlinkType()
	{
		return getHyperlinkTypeValue().getValue();
	}
		
	/**
	 *
	 */
	public HyperlinkTypeEnum getHyperlinkTypeValue()
	{
		return JRHyperlinkHelper.getHyperlinkTypeValue(this);
	}
		
	/**
	 *
	 */
	public byte getHyperlinkTarget()
	{
		return JRHyperlinkHelper.getHyperlinkTarget(this);
	}
		
	/**
	 *
	 */
	public JRGroup getEvaluationGroup()
	{
		return evaluationGroup;
	}
		
	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return expression;
	}

	/**
	 *
	 */
	public JRExpression getAnchorNameExpression()
	{
		return anchorNameExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkReferenceExpression()
	{
		return hyperlinkReferenceExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkAnchorExpression()
	{
		return hyperlinkAnchorExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkPageExpression()
	{
		return hyperlinkPageExpression;
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
	public void visit(JRVisitor visitor)
	{
		visitor.visitImage(this);
	}

	
	public int getBookmarkLevel()
	{
		return bookmarkLevel;
	}

	
	/**
	 * 
	 */
	public Float getDefaultLineWidth() 
	{
		return JRPen.LINE_WIDTH_0;
	}

	
	public String getLinkType()
	{
		return linkType;
	}

	public String getLinkTarget()
	{
		return linkTarget;
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		return hyperlinkParameters;
	}
	
	
	public JRExpression getHyperlinkTooltipExpression()
	{
		return hyperlinkTooltipExpression;
	}

	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseImage clone = (JRBaseImage)super.clone();
		clone.lineBox = lineBox.clone(clone);
		clone.hyperlinkParameters = JRCloneUtils.cloneArray(hyperlinkParameters);
		clone.expression = JRCloneUtils.nullSafeClone(expression);
		clone.anchorNameExpression = JRCloneUtils.nullSafeClone(anchorNameExpression);
		clone.hyperlinkReferenceExpression = JRCloneUtils.nullSafeClone(hyperlinkReferenceExpression);
		clone.hyperlinkAnchorExpression = JRCloneUtils.nullSafeClone(hyperlinkAnchorExpression);
		clone.hyperlinkPageExpression = JRCloneUtils.nullSafeClone(hyperlinkPageExpression);
		clone.hyperlinkTooltipExpression = JRCloneUtils.nullSafeClone(hyperlinkTooltipExpression);
		return clone;
	}


	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private Byte horizontalAlignment;
	/**
	 * @deprecated
	 */
	private Byte verticalAlignment;
	/**
	 * @deprecated
	 */
	private Byte border;
	/**
	 * @deprecated
	 */
	private Byte topBorder;
	/**
	 * @deprecated
	 */
	private Byte leftBorder;
	/**
	 * @deprecated
	 */
	private Byte bottomBorder;
	/**
	 * @deprecated
	 */
	private Byte rightBorder;
	/**
	 * @deprecated
	 */
	private Color borderColor;
	/**
	 * @deprecated
	 */
	private Color topBorderColor;
	/**
	 * @deprecated
	 */
	private Color leftBorderColor;
	/**
	 * @deprecated
	 */
	private Color bottomBorderColor;
	/**
	 * @deprecated
	 */
	private Color rightBorderColor;
	/**
	 * @deprecated
	 */
	private Integer padding;
	/**
	 * @deprecated
	 */
	private Integer topPadding;
	/**
	 * @deprecated
	 */
	private Integer leftPadding;
	/**
	 * @deprecated
	 */
	private Integer bottomPadding;
	/**
	 * @deprecated
	 */
	private Integer rightPadding;
	/**
	 * @deprecated
	 */
	private byte hyperlinkType;
	/**
	 * @deprecated
	 */
	private byte hyperlinkTarget;
	/**
	 * @deprecated
	 */
	private Byte scaleImage;
	/**
	 * @deprecated
	 */
	private byte onErrorType;
	/**
	 * @deprecated
	 */
	private byte evaluationTime;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			horizontalAlignmentValue = HorizontalAlignEnum.getByValue(horizontalAlignment);
			verticalAlignmentValue = VerticalAlignEnum.getByValue(verticalAlignment);
			scaleImageValue = ScaleImageEnum.getByValue(scaleImage);
			onErrorTypeValue = OnErrorTypeEnum.getByValue(onErrorType);
			evaluationTimeValue = EvaluationTimeEnum.getByValue(evaluationTime);

			horizontalAlignment = null;
			verticalAlignment = null;
			scaleImage = null;
		}
		
		if (lineBox == null)
		{
			lineBox = new JRBaseLineBox(this);
			JRBoxUtil.setToBox(
				border,
				topBorder,
				leftBorder,
				bottomBorder,
				rightBorder,
				borderColor,
				topBorderColor,
				leftBorderColor,
				bottomBorderColor,
				rightBorderColor,
				padding,
				topPadding,
				leftPadding,
				bottomPadding,
				rightPadding,
				lineBox
				);
			border = null;
			topBorder = null;
			leftBorder = null;
			bottomBorder = null;
			rightBorder = null;
			borderColor = null;
			topBorderColor = null;
			leftBorderColor = null;
			bottomBorderColor = null;
			rightBorderColor = null;
			padding = null;
			topPadding = null;
			leftPadding = null;
			bottomPadding = null;
			rightPadding = null;
		}

		if (linkType == null)
		{
			 linkType = JRHyperlinkHelper.getLinkType(HyperlinkTypeEnum.getByValue(hyperlinkType));
		}

		if (linkTarget == null)
		{
			 linkTarget = JRHyperlinkHelper.getLinkTarget(HyperlinkTargetEnum.getByValue(hyperlinkTarget));
		}
	}
}
